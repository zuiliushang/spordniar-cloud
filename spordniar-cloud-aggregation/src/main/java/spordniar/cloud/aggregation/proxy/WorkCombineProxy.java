package spordniar.cloud.aggregation.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class WorkCombineProxy {

	@Autowired
	RestTemplate restTemplate;
	
	private static final String PRODUCT_ADDR = "http://spordniar-cloud-product/product";
	
	private static final String CONSUMER_ADDR = "http://spordniar-cloud-consumer/consumer";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkCombineProxy.class);
	
	@HystrixCommand(fallbackMethod = "combileDefault")
	public String combile() {
		ResponseEntity<String> resp = restTemplate.exchange(PRODUCT_ADDR, HttpMethod.GET, null, String.class);
		String product;
		String consumer;
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			product = resp.getBody();
		}else {
			throw new NullPointerException("product");
		}
		resp = restTemplate.exchange(CONSUMER_ADDR, HttpMethod.GET, null, String.class);
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			consumer = resp.getBody();
		}else {
			throw new NullPointerException("consumer");
		}
		return String.format("%s : %s", product, consumer);
	}
	
	@HystrixCommand(fallbackMethod = "combileDefault")
	public String combileCommonException() {
		ResponseEntity<String> resp = restTemplate.exchange(PRODUCT_ADDR, HttpMethod.GET, null, String.class);
		String product;
		String consumer;
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			product = resp.getBody();
		}else {
			throw new NullPointerException("product");
		}
		resp = restTemplate.exchange(CONSUMER_ADDR, HttpMethod.GET, null, String.class);
		if (!resp.getStatusCode().equals(HttpStatus.OK)) {//cause
			consumer = resp.getBody();
		}else {
			throw new NullPointerException("consumer");
		}
		return String.format("%s : %s", product, consumer);
	}
	
	
	@HystrixCommand(fallbackMethod = "combileDefault")
	public String combileCombileException() {
		ResponseEntity<String> resp = restTemplate.exchange(PRODUCT_ADDR, HttpMethod.GET, null, String.class);
		String product;
		String consumer;
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			product = resp.getBody();
		}else {
			throw new NullPointerException("product");
		}
		resp = restTemplate.exchange(CONSUMER_ADDR, HttpMethod.GET, null, String.class);
		if (resp.getStatusCode().equals(HttpStatus.OK)) {
			consumer = resp.getBody();
		}else {
			throw new NullPointerException("consumer");
		}
		int j = 1/0;// exception
		return String.format("%s : %s", product, consumer);
	}
	
	
	public String combileDefault(Throwable e) {
		LOGGER.error("combile error default {}", e.getMessage());
		return "default value";
	}
	
}
