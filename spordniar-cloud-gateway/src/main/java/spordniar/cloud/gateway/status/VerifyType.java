package spordniar.cloud.gateway.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum VerifyType {
	SIGN((byte)1),TOKEN((byte)2),METHOD((byte)3);
	VerifyType(byte value){
		this.value = value;
	}
	private byte value;
	public byte value() {
		return this.value;
	}
	
	@SuppressWarnings("serial")
	private static final Map<Integer, VerifyType> map = new HashMap<Integer, VerifyType>(){{
		put(1, SIGN);
		put(2, TOKEN);
		put(3, METHOD);
	}};
	
	public static Optional<VerifyType> getByType(int type){
		return Optional.ofNullable(map.get(type));
	}
}
