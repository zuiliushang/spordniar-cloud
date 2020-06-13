package spordniar.cloud.gateway.status;

public enum CommonStatus {
	
	ENABLE((byte)1),
	DISABLE((byte)2);
	
	private byte value;
	
	CommonStatus(byte value) {
		this.value = value;
	}
	
	public byte value() {
		return this.value;
	}
	
}
