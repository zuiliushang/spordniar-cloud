package spordniar.cloud.aggregation.config.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Configurable
@AutoConfigureAfter(value = {RabbitMqConfig.class})
@Component
public class RabbitMqExchangeConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqExchangeConfig.class);
	
	@Value("${rabbit.queue.persistent:true}")
	private boolean queuePersistent;
	
	@Bean
	TopicExchange contractTopicExchangeDurable(RabbitAdmin rabbitAdmin) {
		TopicExchange contractTopicExchange = new TopicExchange(RabbitMqEnum.Exchange.CONTACT_TOPIC.getCode());
		rabbitAdmin.declareExchange(contractTopicExchange);
		LOGGER.debug("\t topic exchange init");
		return contractTopicExchange;
	}
	
	@Bean
	Queue getAggrQueue(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitMqEnum.QueueName.QUEUE_AGGREGATION.getCode(), queuePersistent);
		rabbitAdmin.declareQueue(queue);
		LOGGER.debug("\t aggregation queue init success");
		return queue;
	}
	
	@Bean
	Binding bindingAggregationQueue(Queue aggrQueue, TopicExchange topicExchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(aggrQueue).to(topicExchange).with(RabbitMqEnum.QueueBinding.BINDING_AGGREGATION.getCode());
		rabbitAdmin.declareBinding(binding);
		LOGGER.debug("\t aggregation queue init success");
		return binding;
	}
	
}
