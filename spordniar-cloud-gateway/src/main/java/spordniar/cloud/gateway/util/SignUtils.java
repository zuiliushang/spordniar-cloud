package spordniar.cloud.gateway.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignUtils.class);
	
	private static final String[] SIGN_HEADERS = new String[] {"appid"};
	
	public static String genSign(String secret, HttpServletRequest request) {
		TreeMap<String , String> sortMap = new TreeMap<String, String>();
		Enumeration<String> paramEnum = request.getParameterNames();
		while (paramEnum.hasMoreElements()) {
			String name = paramEnum.nextElement();
			LOGGER.debug("sign param name={}", name);
			String value = request.getParameter(name);
			if (needAddSgin(name,value)) {
				sortMap.put(name, value);
			}
		}
		addSignHead(request, sortMap);
		return genSign(secret, sortMap);
	}

	private static String genSign(String secret, TreeMap<String, String> sortMap) {
		StringBuilder preStr = new StringBuilder();
		preStr.append(secret);
		for (Map.Entry<String, String> param : sortMap.entrySet()) {
			String name = param.getKey();
			String value = param.getValue();
			if (needAddSgin(name, value)) {
				preStr.append(name).append(value);
			}
		}
		LOGGER.info("gen Sign preStr = {}", preStr);
		//String mySign = 
		return null;
	}

	private static void addSignHead(HttpServletRequest request, TreeMap<String, String> sortMap) {
		for (String h : SIGN_HEADERS) {
			String hv;
			if ((hv = request.getHeader(h))!=null) {
				sortMap.put(h,hv);
			}
		}
	}

	private static boolean needAddSgin(String name, String value) {
		if (StringUtils.isNoneBlank(value) && !name.equals("file")) {
			return true;
		}
		return true;
	}
	
	
}
