package spordniar.cloud.product.amqp;

public class RabbitMqEnum {

	public enum Exchange{
		
		CONTACT_FANOUT("Exchange.spordniar.fanout", "消息分发"),
		CONTACT_TOPIC("Exchange.spordniar.topic", "消息订阅"),
		CONTACT_DIRECT("Exchange.spordniar.direct", "点对点");
		
		private String code;
		private String name;
		
		Exchange(String code, String name){
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}
	
	public enum QueueName{
		QUEUE_AGGREGATION("queue.spordniar.aggregation", "聚合服务队列"),
		QUEUE_PRODUCT("queue.spordniar.product", "生产服务队列"),
		QUEUE_CONSUMER("queue.spordniar.consumer", "消费服务队列");
		private String name;
		private String code;
		QueueName(String code, String name){
			this.name = name;
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}
	}
	
	public enum QueueBinding{
		BINDING_AGGREGATION("topic.spordniar.aggr", "聚合服务bindingkey"),
		BINDING_PRODUCT("topic.spordniar.product", "生产key"),
		BINDING_CONSUMER("topic.spordniar.consumer", "消费key");
		private String code;
		private String name;
		QueueBinding(String code, String name){
			this.code = code;
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	public enum EventType{
		SPORDNIAR_EVENT_TYPE_RECEIVED,
		SPORDNIAR_EVENT_TYPE_PRODUCT_SUCCESS,
		SPORDNIAR_EVENT_TYPE_PRODUCT_FAILURE,
		SPORDNIAR_EVENT_TYPE_CONSUMER_SUCCESS,
		SPORDNIAR_EVENT_TYPE_CONSUMER_FAILURE,
		SPORDNIAR_EVENT_TYPE_AGGREGATION_BEGIN,
		SPORDNIAR_EVENT_TYPE_AGGREGATION_COMPLETE,
		SPORDNIAR_EVENT_TYPE_ROLLBACK_AGGREGATION_FAILURE
	}
	
}
