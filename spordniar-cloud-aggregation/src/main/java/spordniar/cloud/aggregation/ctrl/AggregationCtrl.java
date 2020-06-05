package spordniar.cloud.aggregation.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregationCtrl {

	@GetMapping("netcheck")
	public String netcheck(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
	
}
