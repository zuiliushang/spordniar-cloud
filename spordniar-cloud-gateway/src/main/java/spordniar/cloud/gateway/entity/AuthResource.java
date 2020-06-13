package spordniar.cloud.gateway.entity;

public class AuthResource {
	
	//唯一
	private Integer id;
	//authID
	private Integer authId;
	//资源
	private String resource;
	//优先级
	private short matchOrder;
	//资源类型
	private byte resourceType;
	//认证方式
	private byte verifyType;

	public AuthResource(Integer id, Integer authId, String resource, short matchOrder, byte resourceType,
			byte verifyType) {
		super();
		this.id = id;
		this.authId = authId;
		this.resource = resource;
		this.matchOrder = matchOrder;
		this.resourceType = resourceType;
		this.verifyType = verifyType;
	}

	public AuthResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public short getMatchOrder() {
		return matchOrder;
	}

	public void setMatchOrder(short matchOrder) {
		this.matchOrder = matchOrder;
	}

	public byte getResourceType() {
		return resourceType;
	}

	public void setResourceType(byte resourceType) {
		this.resourceType = resourceType;
	}

	public byte getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(byte verifyType) {
		this.verifyType = verifyType;
	}
	
}
