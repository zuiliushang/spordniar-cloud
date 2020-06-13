package spordniar.cloud.gateway.entity;

public class PartnerRole {
	private Integer partnerId;
	private Integer roleId;
	public PartnerRole(Integer partnerId, Integer roleId) {
		super();
		this.partnerId = partnerId;
		this.roleId = roleId;
	}
	public PartnerRole() {
		super();
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
}
