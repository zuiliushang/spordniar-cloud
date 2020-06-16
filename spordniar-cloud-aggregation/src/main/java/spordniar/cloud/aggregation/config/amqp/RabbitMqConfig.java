package spordniar.cloud.aggregation.config.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class RabbitMqConfig {

	@Value("${spring.rabbitmq.addresses}")
	private String addresses;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.publisher-confirms}")
	private Boolean publisherConfirms;
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
	
	@Bean
	public ConnectionFactory getConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(addresses);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPublisherConfirms(publisherConfirms);
		connectionFactory.setVirtualHost(virtualHost);
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin getRabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(getConnectionFactory());
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate getRbRabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(getConnectionFactory());
		return template;
	}
	
}
