package spordniar.cloud.consumer.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

	@GetMapping("/consumer")
	public String consumer() {
		int random = (int) (Math.random() * 100);
		return "consumer: " + random;
	}
	
}
