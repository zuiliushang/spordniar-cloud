package spordniar.cloud.gateway.entity;

public class Partner {
	private Integer id;
	private String partnerName;
	private short defaultDurHours;
	private Integer defaultTokenLimit;
	
	public Partner(Integer id, String partnerName, short defaultDurHours, Integer defaultTokenLimit) {
		super();
		this.id = id;
		this.partnerName = partnerName;
		this.defaultDurHours = defaultDurHours;
		this.defaultTokenLimit = defaultTokenLimit;
	}
	public Partner() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public short getDefaultDurHours() {
		return defaultDurHours;
	}
	public void setDefaultDurHours(short defaultDurHours) {
		this.defaultDurHours = defaultDurHours;
	}
	public Integer getDefaultTokenLimit() {
		return defaultTokenLimit;
	}
	public void setDefaultTokenLimit(Integer defaultTokenLimit) {
		this.defaultTokenLimit = defaultTokenLimit;
	}
}
