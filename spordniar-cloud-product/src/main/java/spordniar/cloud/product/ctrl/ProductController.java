package spordniar.cloud.product.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@GetMapping("product")
	public String product() {
		int random = (int) (Math.random() * 100);
		return "product: " + random;
	}
	
}
