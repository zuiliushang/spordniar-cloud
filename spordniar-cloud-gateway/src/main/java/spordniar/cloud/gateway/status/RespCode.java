package spordniar.cloud.gateway.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public enum RespCode {

	SUCCESS("1000", "success"),
	
	/** 校验  */
	ILLEGAL_IP("E5001", "非法IP"),
	TOKEN_CHECK_FAILURE("E5002", "访问令牌校验失败"),
	TOKEN_TIME_OUT("E5114", "访问令牌已过期"),
	SIGN_CHECK_FAILURE("E5116", "SIGN校验失败"),
	UNSUPPORT_CONTENT_TYPE("E5003", "错误的请求方式"),
	NOT_SUPPORT_VERIFY_FAILURE("E5117", "资源访问校验异常"),
	NOT_ALLOW_ACCESS("E5115", "权限不足"),
	UNKNOW_TOKEN_ERROR("E5118", "未知的令牌状态"),
	
	/** 请求参数 */
	ILLEGAL_PARAMS("E5004", "非法请求参数"),
	ILLEGAL_USER_ID("E5008", "非法的用户ID"),
	ILLEGAL_APP_ID("E5116", "非法的应用标记"),
	
	
	/** 内部默认错误 */
	SERVER_INTERNAL_ERROR("E5109", "服务器内部错误"),
	REQUEST_TOO_FREQUENT("E5113", "请求过于频繁"),
	
	/** 网络相关 */
	HTTP_STATUS_400("HTTP-400", "请求参数错误"),
	HTTP_STATUS_401("HTTP-401", "访问被拒绝"),
	HTTP_STATUS_403("HTTP-403", "资源不可用"),
	HTTP_STATUS_404("HTTP-404", "请求的资源不存在"),
	HTTP_STATUS_405("HTTP-405", "请求方法对指定的资源不适用"),
    HTTP_STATUS_406("HTTP-406", "指定的资源MIME类型不匹配"),
    HTTP_STATUS_407("HTTP-407", "要求进行代理身份验证"),
    HTTP_STATUS_408("HTTP-408", "请求超时"),

    HTTP_STATUS_500("HTTP-500", "服务器内部错误"),
    HTTP_STATUS_501("HTTP-501", "服务不支持"),
    HTTP_STATUS_502("HTTP-502", "应用程序出错"),
    HTTP_STATUS_503("HTTP-503", "服务不可用"),
    HTTP_STATUS_504("HTTP-504", "网关超时"),    
	
	;
	
	private String code;
	
	private String msg;
	
	RespCode(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private static Map<String, RespCode> map = new HashMap<String, RespCode>(){
		private static final long serialVersionUID = 1L;

	{
		for (RespCode code : RespCode.values()) {
			put(code.code, code);
		}
	}};
	
	public static Optional<RespCode> getByCode(String code){
		return Optional.ofNullable(map.get(code));
	}
	
	public String getJsonResponse() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"code\": \"").append(code).append("\",\"msg\": \"").append(msg).append("\"}");
		return sb.toString();
	}
	
	public static Map<String, Object> getByHttpStatus(int httpStatus, String dftErrorMessage){
		String key = "http-" + httpStatus;
		RespCode respCode = map.get(key);
		Optional<RespCode> o = Optional.ofNullable(respCode);
		o.orElse(SERVER_INTERNAL_ERROR);
		Map<String, Object> map = o.get().toMap();
		if (StringUtils.isNoneBlank(dftErrorMessage)) {
			map.put("msg", dftErrorMessage);//cover
		}
		return map;
	}
	
	public static Map<String , Object> getByHttpStatus(int httpStatus){
		return getByHttpStatus(httpStatus, null);
	}
	
	private Map<String, Object> toMap() {
		Map<String , Object> map = new HashMap<String, Object>(){
			private static final long serialVersionUID = -1694756690286330529L;
		{
			put(code, msg);
		}};
		return map;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}}
