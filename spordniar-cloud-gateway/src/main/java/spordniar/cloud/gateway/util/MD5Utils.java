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
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			int inb = (b < 0) ? Math.abs(b) | 0x80 : Math.abs(b);
			String temp = Integer.toHexString(inb);
			if (temp.length() == 1) {
				hex.append("0");
			}
			hex.append(temp.toLowerCase());
		}
		return hex.toString();
	}
	
	public static String getHexMd5(String source) {
		try {
			byte[] md5 = getMD5(source);
			String md5HexStr = byte2hex(md5);
			return md5HexStr;
		} catch (Exception e) {
			LOGGER.error("hex error source={}" , source);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Math.abs(-5555));
		System.out.println(Math.abs(-5555) | 0x80);
		System.out.println(0x80);
		System.out.println(getHexMd5("raindrops123"));
		System.out.println( Integer.toHexString(14));
		String md5 = getHexMd5("raindrops");
		System.out.println("md5 " + md5);
	}
	
}
