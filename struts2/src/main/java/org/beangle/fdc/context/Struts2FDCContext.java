package org.beangle.fdc.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.StrutsStatics;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.fdc.adaptor.ExceptionHolderAdaptor;

import com.opensymphony.xwork2.ActionContext;

public class Struts2FDCContext extends HashMap<String, Object> implements FDCContext{
	private static final long serialVersionUID = 363780510511209976L;

	private static final String QUOT = "\"";
	private final ExceptionHolder exceptionHolder;
			
	private final String serverName;
	
	private final String serverAddr;
	
	private final String requestURI;
	
	private final Locale locale;
	
	private final String userAgent;
	
	private final Map<String,Object> params = Collections.unmodifiableMap(ActionContext.getContext().getParameters());
	
	private final Map<String, Object> session = Collections.unmodifiableMap(ActionContext.getContext().getSession());
	
	public static Struts2FDCContext create(com.opensymphony.xwork2.interceptor.ExceptionHolder strutsExceptionHolder){
		ExceptionHolder exceptionHolder = ExceptionHolderAdaptor.adapte(strutsExceptionHolder);
		return new Struts2FDCContext(exceptionHolder);
	}
	
	private Struts2FDCContext(ExceptionHolder exceptionHolder){
		super();
		this.exceptionHolder = exceptionHolder;
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		requestURI = request.getRequestURI();
		locale = request.getLocale();
		userAgent = request.getHeader("User-Agent");
		serverName = request.getServerName();
		serverAddr = request.getLocalAddr();
	}

	public ExceptionHolder getExceptionHolder() {
		return exceptionHolder;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	public String toJSONString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append(QUOT).append("exceptionHolder").append(QUOT).append(":{")
			.append(QUOT).append("message").append(QUOT).append(":").append(QUOT).append(exceptionHolder.getException().getMessage()).append(QUOT).append(",")
			.append(QUOT).append("stack").append(QUOT).append(":").append(QUOT).append(exceptionHolder.getExceptionStack()).append(QUOT).append("},")
		.append(QUOT).append("serverName").append(QUOT).append(":").append(QUOT).append(serverName).append(QUOT).append(",")
		.append(QUOT).append("serverAddr").append(QUOT).append(":").append(QUOT).append(serverAddr).append(QUOT).append(",")
		
		.append(QUOT).append("requestURI").append(QUOT).append(":").append(QUOT).append(requestURI).append(QUOT).append(",")
		.append(QUOT).append("locale").append(QUOT).append(":").append(QUOT).append(locale).append(QUOT).append(",")
		.append(QUOT).append("userAgent").append(QUOT).append(":").append(QUOT).append(userAgent).append(QUOT).append(",")
		.append(QUOT).append("params").append(QUOT).append(":").append("{");
		for (Entry<String,Object> entry: params.entrySet()) {
			sb.append(QUOT).append(entry.getKey()).append(QUOT).append(":");
			Object value = entry.getValue();
			if(value.getClass().isArray()){
				Object[] objArr = (Object[]) value;
				if(objArr.length==1){
					sb.append(QUOT).append(objArr[0].toString()).append(QUOT).append(",");
				}else if(objArr.length==0){
					sb.append("[]");
				}else {
					sb.append("[");
					for (Object object : objArr) {
						sb.append(QUOT).append(object.toString()).append(QUOT).append(",");	
					}
					sb.deleteCharAt(sb.length()-1).append("],");
				}
			}else{
				sb.append(QUOT).append(value.toString()).append(QUOT).append(",");
			}
		}
		if(!params.isEmpty()){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("},").append(QUOT).append("session").append(QUOT).append(":").append("{");
		for (Entry<String,Object> entry: session.entrySet()) {
			sb.append(QUOT).append(entry.getKey()).append(QUOT).append(":");
			Object value = entry.getValue();
			if(value.getClass().isArray()){
				Object[] objArr = (Object[]) value;
				if(objArr.length==1){
					sb.append(QUOT).append(objArr[0].toString()).append(QUOT).append(",");
				}else if(objArr.length==0){
					sb.append("[]");
				}else {
					sb.append("[");
					for (Object object : objArr) {
						sb.append(QUOT).append(object.toString()).append(QUOT).append(",");	
					}
					sb.deleteCharAt(sb.length()-1).append("],");
				}
			}else{
				sb.append(QUOT).append(value.toString()).append(QUOT).append(",");
			}
		}
		if(!session.isEmpty()){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.append("}}").toString();
	}
}
