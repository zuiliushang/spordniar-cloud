package spordniar.cloud.gateway.util;

import java.util.Date;

public class UserTokenDTO {

	private Integer userId;
	private String userName;
	private Integer partnerId;
	private Integer partnerUserId;
	private Integer userStatus;
	private String token;
	private Date expireTime;
	private Date createTime;
	private Integer tokenDurHours;
	private Integer tokenGenLimit;
	private Integer tokenId;
	
	public UserTokenDTO() {
		super();
	}
	public UserTokenDTO(Integer userId, String userName, Integer partnerId, Integer partnerUserId, Integer userStatus,
			String token, Date expireTime, Date createTime, Integer tokenDurHours, Integer tokenGenLimit,
			Integer tokenId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.partnerId = partnerId;
		this.partnerUserId = partnerUserId;
		this.userStatus = userStatus;
		this.token = token;
		this.expireTime = expireTime;
		this.createTime = createTime;
		this.tokenDurHours = tokenDurHours;
		this.tokenGenLimit = tokenGenLimit;
		this.tokenId = tokenId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getPartnerUserId() {
		return partnerUserId;
	}
	public void setPartnerUserId(Integer partnerUserId) {
		this.partnerUserId = partnerUserId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getTokenDurHours() {
		return tokenDurHours;
	}
	public void setTokenDurHours(Integer tokenDurHours) {
		this.tokenDurHours = tokenDurHours;
	}
	public Integer getTokenGenLimit() {
		return tokenGenLimit;
	}
	public void setTokenGenLimit(Integer tokenGenLimit) {
		this.tokenGenLimit = tokenGenLimit;
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	
}
