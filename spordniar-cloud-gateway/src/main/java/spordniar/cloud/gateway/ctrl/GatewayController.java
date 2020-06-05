package spordniar.cloud.gateway.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
	
	@GetMapping("/common/netcheck")
	public String commonNetcheck(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String uri = request.getRequestURI();
		LOGGER.info("ip={} access {}", ip, uri);
		return ip;
	}
	
}
