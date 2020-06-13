package spordniar.cloud.gateway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {

	private static Logger LOGGER = LoggerFactory.getLogger(MD5Utils.class);
	
	public static byte[] getMD5(String content) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bytes = digest.digest(content.getBytes("utf8"));
		return bytes;
	}
	
//	private static String byte2hex(byte[] bytes) {
//		StringBuilder hex = new StringBuilder();
//		for (int i = 0; i < bytes.length; i++) {
//			byte b = bytes[i];
//			
//		}
//	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Math.abs(-5555));
		System.out.println(Math.abs(-5555) | 0x80);
		System.out.println(0x80);
	}
	
}
