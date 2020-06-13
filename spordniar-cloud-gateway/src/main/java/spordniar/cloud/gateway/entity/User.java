package spordniar.cloud.gateway.entity;

public class User {

	private Integer id;
	
	private byte status;
	
	private String username;
	
	private Integer partnerId;
	
	//用户默认token时间
	private byte tokenDurHours;
	//token最多同时存在几个
	private byte tokenGenLimit;
	
	private String mobile;

	public User() {
		super();
	}

	public User(Integer id, byte status, String username, Integer partnerId, byte tokenDurHours, byte tokenGenLimit,
			String mobile) {
		super();
		this.id = id;
		this.status = status;
		this.username = username;
		this.partnerId = partnerId;
		this.tokenDurHours = tokenDurHours;
		this.tokenGenLimit = tokenGenLimit;
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public byte getTokenDurHours() {
		return tokenDurHours;
	}

	public void setTokenDurHours(byte tokenDurHours) {
		this.tokenDurHours = tokenDurHours;
	}

	public byte getTokenGenLimit() {
		return tokenGenLimit;
	}

	public void setTokenGenLimit(byte tokenGenLimit) {
		this.tokenGenLimit = tokenGenLimit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
