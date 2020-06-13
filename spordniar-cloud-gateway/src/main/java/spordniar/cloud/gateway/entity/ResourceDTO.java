package spordniar.cloud.gateway.entity;

public class ResourceDTO {
	private String authName;
	private int verifyType;
	private String resource;
	private int matchOrder;
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public int getVerifyType() {
		return verifyType;
	}
	public void setVerifyType(int verifyType) {
		this.verifyType = verifyType;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getMatchOrder() {
		return matchOrder;
	}
	public void setMatchOrder(int matchOrder) {
		this.matchOrder = matchOrder;
	}
}
