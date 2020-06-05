package spordniar.cloud.aggregation.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spordniar.cloud.aggregation.proxy.WorkCombineProxy;

@RestController
public class WorkCombileProxyCtrl {

	@Autowired
	WorkCombineProxy workCombineProxy;
	
	@GetMapping("product-consumer")
	public String productConsumer() {
		return String.format("%s \n %s \n %s", workCombineProxy.combile(), 
				workCombineProxy.combileCombileException(), 
				workCombineProxy.combileCommonException());
	}
	
}
