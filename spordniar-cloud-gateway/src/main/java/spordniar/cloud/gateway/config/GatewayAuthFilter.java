package spordniar.cloud.gateway.config;

import org.springframework.beans.factory.InitializingBean;

import com.netflix.zuul.ZuulFilter;

public class GatewayAuthFilter extends ZuulFilter implements InitializingBean{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
