package spordniar.cloud.gateway.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class HttpPayloadUtils {

	public static void printHttpPayload(Logger logger, HttpServletRequest request) {
		String method = request.getMethod();
		logger.debug("\t> Request.method = {}", method);
		logger.debug("\t> URI: {}", request.getRequestURI());
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			logger.debug("\t> HEAD: {}={}", name, request.getHeader(name));
		}
		Map<String, String[]> parameterMap = request.getParameterMap();
		for ( Entry<String, String[]> parameterEntry : parameterMap.entrySet()) {
			logger.debug("\t>> PARAM: {}={}", parameterEntry.getKey(),
					StringUtils.join(parameterEntry.getValue(), ","));
		}
	}
	
}
