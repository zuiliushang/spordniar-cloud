package spordniar.cloud.product.amqp;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;

public class SpordniarEvent extends ApplicationEvent{

	private RabbitMqEnum.EventType eventType;
	
	private String data;
	
	private static final long serialVersionUID = 1L;

	public SpordniarEvent(Object source) {
		super(source);
		if (!(source instanceof String)) {
			throw new IllegalArgumentException("create SpordniarEvent must use String.");
		}
		String jsonStr = (String)source;
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		String et = jsonObject.getString("eventType");
		if (StringUtils.isNotBlank(et)) {
			RabbitMqEnum.EventType eventType = RabbitMqEnum.EventType.valueOf(et);
			this.eventType = eventType;
		}
		this.data = jsonObject.getString("data");
	}

	public SpordniarEvent() {
		super("");
	}

	public RabbitMqEnum.EventType getEventType() {
		return eventType;
	}

	public void setEventType(RabbitMqEnum.EventType eventType) {
		this.eventType = eventType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("eventType", eventType);
		jsonObject.put("data", data);
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	
}
