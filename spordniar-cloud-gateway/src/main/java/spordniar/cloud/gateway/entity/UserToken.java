package spordniar.cloud.gateway.entity;

import java.util.Date;

public class UserToken {
	
	private Integer userId;
	
	private String token;
	
	private Date expireTime;
	
	public UserToken() {
		super();
	}
	
	public UserToken(Integer userId, String token, Date expireTime) {
		super();
		this.userId = userId;
		this.token = token;
		this.expireTime = expireTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
}
