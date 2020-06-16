package spordniar.cloud.gateway.config;

import static spordniar.cloud.gateway.status.RespCode.ILLEGAL_APP_ID;
import static spordniar.cloud.gateway.status.RespCode.ILLEGAL_PARAMS;
import static spordniar.cloud.gateway.status.RespCode.SIGN_CHECK_FAILURE;
import static spordniar.cloud.gateway.status.RespCode.TOKEN_CHECK_FAILURE;
import static spordniar.cloud.gateway.status.RespCode.TOKEN_TIME_OUT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import spordniar.cloud.gateway.entity.AppInfo;
import spordniar.cloud.gateway.entity.Auth;
import spordniar.cloud.gateway.entity.ResourceDTO;
import spordniar.cloud.gateway.service.AuthService;
import spordniar.cloud.gateway.service.UserTokenService;
import spordniar.cloud.gateway.status.RespCode;
import spordniar.cloud.gateway.status.VerifyType;
import spordniar.cloud.gateway.util.ExecuteResult;
import spordniar.cloud.gateway.util.HttpPayloadUtils;
import spordniar.cloud.gateway.util.JwtTokenUtils;
import spordniar.cloud.gateway.util.SignUtils;
import spordniar.cloud.gateway.util.TokenDetail;
import spordniar.cloud.gateway.util.UserTokenDTO;

@Component
public class GatewayAuthFilter extends ZuulFilter implements InitializingBean{
	
	private static Logger LOG = LoggerFactory.getLogger(GatewayAuthFilter.class);
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserTokenService userTokenService;
	
	@Value("${auth.allStartMatcher}")
	private String allStartMatcher;

	@Value("${sign.content.type}")
	private String signContentType;
	
	@Value("${auth.ignoreMatcher}")
	private String ignoreMatcher;
	
	@Override
	public boolean shouldFilter() {
		return false;
//		RequestContext ctx = RequestContext.getCurrentContext();
//		HttpServletRequest request = ctx.getRequest();
//		String requestURI = request.getRequestURI();
//		Object o = ctx.get("GatewayAuthFilter.flag");
//		LOG.debug("GatewayAuthFilter = {}", o);
//		if (requestURI.contentEquals(ignoreMatcher)) {
//			return false;
//		}
//		String[] matchers = allStartMatcher.split(",");
//		Optional<String> op = Arrays.asList(matchers).parallelStream().filter(m->requestURI.startsWith(m))
//			.findAny();
//		if (op.isPresent()) {
//			return true;
//		}
//		return false;
	}
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		LOG.info("GatewayAuthFilter.run");
		HttpPayloadUtils.printHttpPayload(LOG, request);
		ctx.set("GatewayAuthFilter.flag");
		ctx.addZuulRequestHeader("zuul", "zuul-header");
		String URI = request.getRequestURI();
		Optional<ResourceDTO> resDtoOp = authService.getResourceAuth(URI);
		if (!resDtoOp.isPresent()) {
			LOG.info("{} not need any auth", URI);
		}
		Integer verifyType = resDtoOp.get().getVerifyType();
		Optional<VerifyType> vOptional = VerifyType.getByType(verifyType);
		if (!vOptional.isPresent()) {
			LOG.error("Not Support Verify Type [{}]" , verifyType);
			return null;
		}
		ExecuteResult<List<String>, RespCode> result = null;
		List<String> visitorAuthSet = null;
		switch (vOptional.get()) {
			case SIGN:
				result = verifySign(request);
				visitorAuthSet = result.getResult();
				LOG.debug("verifySign={} ,RUI={}", result.success(), URI);
				LOG.debug("zuul RequestHeader={}",ctx.getZuulRequestHeaders());
				break;
			case TOKEN:
				result = verifyToken(request);
				
				break;
			case METHOD:
				break;
			default:
				break;
			}
		return null;
	}
	private ExecuteResult<List<String>, RespCode> verifyToken(HttpServletRequest request) {
		String accessToken = request.getHeader("token");
		if (StringUtils.isEmpty(accessToken)) {
			return new ExecuteResult<List<String>, RespCode>(false, null, TOKEN_CHECK_FAILURE);
		}
		TokenDetail tokenDetail = null;
		try {
			tokenDetail = JwtTokenUtils.parse(accessToken);
		} catch (ExpiredJwtException e) {
			LOG.error("Token timeout,msg={}", e.getMessage());
			return new ExecuteResult<List<String>, RespCode>(false, null, TOKEN_TIME_OUT);
		} catch (JwtException e) {
			LOG.error("JwtParse Failed: " + e.getMessage(), e);
			return new ExecuteResult<List<String>, RespCode>(false, null, TOKEN_CHECK_FAILURE);
		}
		if (tokenDetail.getExptime() < System.currentTimeMillis()) {
			LOG.error("token expireTime={}, systemTime={}", tokenDetail.getExptime(), System.currentTimeMillis());
			return new ExecuteResult<List<String>, RespCode>(false, null, TOKEN_TIME_OUT);
		}
		// 获取token信息
		Optional<List<UserTokenDTO>> listOptional = userTokenService.loadUserAvailableTokens(tokenDetail.getUsername());
		if (!listOptional.isPresent()) {
			LOG.debug("!listOptional.isPresent()");
			return new ExecuteResult<>(false, null, TOKEN_CHECK_FAILURE);
		}
		return null;
	}
	private ExecuteResult<List<String>, RespCode> verifySign(HttpServletRequest request) {
		String accessSign = request.getHeader("sign");
		String appId = request.getHeader("appId");
		if (StringUtils.isBlank(accessSign) || StringUtils.isBlank(appId)) {
			LOG.error("verify failed : sign or appId is null");
			return new ExecuteResult<List<String>, RespCode>(false, null, ILLEGAL_PARAMS);
		}
		LOG.info("RECEIVE [ SIGN={} , APPID={} ]", accessSign, appId);
		AppInfo appInfo = authService.getAppInfoByAppId(appId);
		if (appInfo == null) {
			LOG.error("VerifySign failed: No available appId, appId={}" , appId);
			return new ExecuteResult<List<String>, RespCode>(false, null, ILLEGAL_APP_ID);
		}
		String contentType = request.getContentType();
		if (!isSupportContentType(contentType)) {
			LOG.error("VerifySign failed: Not Support ContentType, ContentType={} ", contentType);
			return new ExecuteResult<List<String>, RespCode>(false, null, SIGN_CHECK_FAILURE);
		}
		String systemSign = SignUtils.genSign(appInfo.getAppSecret(), request);
		if (!systemSign.equals(accessSign)) {
			LOG.error("VerifySign failed: Sign is different, [access={}, system={}] ", accessSign, systemSign);
			return new ExecuteResult<List<String>, RespCode>(false, null, SIGN_CHECK_FAILURE);
		}else {
			List<Auth> auths = authService.findAuthByAppId(appId);
			List<String> authNames = auths.stream().map(Auth::getAuthName).collect(Collectors.toList());
			RequestContext ctx = RequestContext.getCurrentContext();
			ctx.set("partnerId", appInfo.getPartnerId());
			return new ExecuteResult<List<String>, RespCode>(true, authNames, null);
		}
	}
	private boolean isSupportContentType(String contentType) {
		if (StringUtils.isBlank(contentType)) {
			LOG.error("contentType is null");
			return false;
		}
		String[] contTypes = signContentType.split(",");
		for (String contType : contTypes) {
			if (contentType.startsWith(contType)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}
	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}
}
