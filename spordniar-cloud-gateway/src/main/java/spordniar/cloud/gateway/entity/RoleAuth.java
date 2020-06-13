package spordniar.cloud.gateway.entity;

public class RoleAuth {

	private Integer id;
	
	private Integer roleId;
	
	private Integer authId;

	public RoleAuth() {
		super();
	}

	public RoleAuth(Integer id, Integer roleId, Integer authId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.authId = authId;
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

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}
	
}
