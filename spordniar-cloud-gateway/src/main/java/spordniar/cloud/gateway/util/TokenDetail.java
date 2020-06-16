package spordniar.cloud.gateway.util;

import java.util.List;

public class TokenDetail {

	private Long id;
	
	private String username;
	
	private List<String> authList;
	
	private Long exptime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getAuthList() {
		return authList;
	}

	public void setAuthList(List<String> authList) {
		this.authList = authList;
	}

	public Long getExptime() {
		return exptime;
	}

	public void setExptime(Long exptime) {
		this.exptime = exptime;
	}

	@Override
	public String toString() {
		return "TokenDetail [id=" + id + ", username=" + username + ", authList=" + authList + ", exptime=" + exptime
				+ "]";
	}
	
}
