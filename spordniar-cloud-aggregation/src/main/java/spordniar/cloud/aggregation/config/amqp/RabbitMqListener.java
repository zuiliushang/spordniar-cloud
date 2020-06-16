package spordniar.cloud.aggregation.config.amqp;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Configurable
@AutoConfigureAfter(RabbitMqConfig.class)
@Component
public class RabbitMqListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListener.class);
	 //绑定监听的队列
    @Bean("testQueueContainer")
	public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMqEnum.QueueName.QUEUE_AGGREGATION.getCode());//消费的队列名
        container.setMessageListener(exampleListener());//定义消费逻辑
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式手工确认
        return container;
	}
	
	@Bean("spordniarQueueListener")
	public ChannelAwareMessageListener exampleListener() {
		return (message, channel) -> {
			LOGGER.info("receive message {}", message);
			try {
				String contentType = message.getMessageProperties().getContentType();
				if (StringUtils.isNotBlank(contentType) && contentType.equals(MessageProperties.CONTENT_TYPE_JSON)) {
					String msg = new String(message.getBody(), "utf-8");
					LOGGER.info("receive [thread: {}, data: {}]", Thread.currentThread().getName(), msg);
				}else {
					LOGGER.error("Not support ContentType {}", contentType);
				}
			} catch (Exception e) {
				LOGGER.error("onMessage error:{}", e.getMessage());
			} finally {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息成功消费
			}
		};
	}
	
	
}
