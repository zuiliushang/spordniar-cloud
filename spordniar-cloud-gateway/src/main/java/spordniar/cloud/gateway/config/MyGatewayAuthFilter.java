package spordniar.cloud.gateway.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class MyGatewayAuthFilter extends ZuulFilter implements InitializingBean{

	private static final Logger LOGGER = LoggerFactory.getLogger(MyGatewayAuthFilter.class);
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println(request.getRequestURL());
		return request.getRequestDispatcher("www.baidu.com");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//cache
		LOGGER.debug("\t do some cache");
	}

	@Override
	public String filterType() {
		 return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER +1;
	}

}
