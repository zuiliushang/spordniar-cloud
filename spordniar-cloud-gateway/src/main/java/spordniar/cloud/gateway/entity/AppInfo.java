package spordniar.cloud.gateway.entity;

public class AppInfo {

	private Integer id;
	private String appId;
	private String appSecret;
	private Integer partnerId;
	private String ipWhite; // ,分割
	
	public AppInfo() {
		super();
	}
	public AppInfo(Integer id, String appId, String appSecret, Integer partnerId, String ipWhite) {
		super();
		this.id = id;
		this.appId = appId;
		this.appSecret = appSecret;
		this.partnerId = partnerId;
		this.ipWhite = ipWhite;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getIpWhite() {
		return ipWhite;
	}
	public void setIpWhite(String ipWhite) {
		this.ipWhite = ipWhite;
	}
	
}
