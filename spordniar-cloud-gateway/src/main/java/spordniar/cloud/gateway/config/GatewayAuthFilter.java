package spordniar.cloud.gateway.config;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import spordniar.cloud.gateway.entity.AppInfo;
import spordniar.cloud.gateway.entity.ResourceDTO;
import spordniar.cloud.gateway.service.AuthServer;
import spordniar.cloud.gateway.status.RespCode;
import spordniar.cloud.gateway.status.VerifyType;
import spordniar.cloud.gateway.util.ExecuteResult;
import spordniar.cloud.gateway.util.HttpPayloadUtils;
import static spordniar.cloud.gateway.status.RespCode.*;

@Component
public class GatewayAuthFilter extends ZuulFilter implements InitializingBean{
	
	private static Logger LOG = LoggerFactory.getLogger(GatewayAuthFilter.class);
	
	@Autowired
	AuthServer authServer;
	
	@Value("${auth.allStartMatcher}")
	private String allStartMatcher;

	@Value("${sign.content.type}")
	private String signContentType;
	
	@Value("${auth.ignoreMatcher}")
	private String ignoreMatcher;
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURI = request.getRequestURI();
		Object o = ctx.get("GatewayAuthFilter.flag");
		LOG.debug("GatewayAuthFilter = {}", o);
		if (requestURI.contentEquals(ignoreMatcher)) {
			return false;
		}
		String[] matchers = allStartMatcher.split(",");
		Optional<String> op = Arrays.asList(matchers).parallelStream().filter(m->requestURI.startsWith(m))
			.findAny();
		if (op.isPresent()) {
			return true;
		}
		return false;
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
		Optional<ResourceDTO> resDtoOp = authServer.getResourceAuth(URI);
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
				break;
			case TOKEN:
				
				break;
			case METHOD:
				break;
			default:
				break;
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
		AppInfo appInfo = authServer.getAppInfoByAppId(appId);
		if (appInfo == null) {
			LOG.error("VerifySign failed: No available appId, appId={}" , appId);
			return new ExecuteResult<List<String>, RespCode>(false, null, ILLEGAL_APP_ID);
		}
		String contentType = request.getContentType();
		if (!isSupportContentType(contentType)) {
			LOG.error("VerifySign failed: Not Support ContentType, ContentType={} ", contentType);
			return new ExecuteResult<List<String>, RespCode>(false, null, SIGN_CHECK_FAILURE);
		}
		
		return null;
	}
	private boolean isSupportContentType(String contentType) {
		
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
