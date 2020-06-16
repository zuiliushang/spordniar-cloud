package spordniar.cloud.product.amqp;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback{

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqSender.class);
	
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public RabbitMqSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitTemplate.setConfirmCallback(this);
	}
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		LOGGER.info("confirm: " + correlationData.getId());
	}
	
	public void sendRabbitmqTopic(String routeKey, SpordniarEvent spordniarEvent) {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		LOGGER.info("send: " + correlationData.getId());
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		Message message = new Message(spordniarEvent.toString().getBytes(), messageProperties);
		this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTACT_TOPIC.getCode(), routeKey, message, correlationData);
	}
	
}
