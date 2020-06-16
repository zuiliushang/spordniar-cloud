package spordniar.cloud.aggregation.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spordniar.cloud.aggregation.config.amqp.RabbitMqEnum;
import spordniar.cloud.aggregation.config.amqp.RabbitMqSender;
import spordniar.cloud.aggregation.config.amqp.SpordniarEvent;
import spordniar.cloud.aggregation.proxy.WorkCombineProxy;

@RestController
public class WorkCombileProxyCtrl {

	@Autowired
	WorkCombineProxy workCombineProxy;
	
	@Autowired
	RabbitMqSender rabbitMqSender;
	
	@GetMapping("product-consumer")
	public String productConsumer() {
		return String.format("%s \n %s \n %s", workCombineProxy.combile(), 
				workCombineProxy.combileCombileException(), 
				workCombineProxy.combileCommonException());
	}
	
	@GetMapping("send/product")
	public void sendProduct() {
		SpordniarEvent spordniarEvent = new SpordniarEvent();
		spordniarEvent.setData("aggregation->product");
		spordniarEvent.setEventType(RabbitMqEnum.EventType.SPORDNIAR_EVENT_TYPE_PRODUCT_SUCCESS);
		rabbitMqSender.sendRabbitmqTopic(RabbitMqEnum.QueueBinding.BINDING_PRODUCT.getCode(), spordniarEvent);
	}
	
	@GetMapping("send/consumer")
	public void sendConsumer() {
		SpordniarEvent spordniarEvent = new SpordniarEvent();
		spordniarEvent.setData("aggregation->consumer");
		spordniarEvent.setEventType(RabbitMqEnum.EventType.SPORDNIAR_EVENT_TYPE_CONSUMER_SUCCESS);
		rabbitMqSender.sendRabbitmqTopic(RabbitMqEnum.QueueBinding.BINDING_CONSUMER.getCode(), spordniarEvent);
	}
	
}
