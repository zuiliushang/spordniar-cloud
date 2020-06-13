package spordniar.cloud.gateway.entity;

public class UserRole {
	private Integer id;
	private Integer roleId;
	private Integer userId;
	
	public UserRole() {
		super();
	}
	public UserRole(Integer id, Integer roleId, Integer userId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
