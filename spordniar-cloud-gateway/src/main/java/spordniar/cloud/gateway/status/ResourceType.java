package spordniar.cloud.gateway.status;

public enum ResourceType {
	URI((byte)0), METHOD((byte)1);
	
	ResourceType(byte value) {
		this.value = value;
	}
	
	private byte value;
	
	public byte value() {
		return this.value;
	}
}
