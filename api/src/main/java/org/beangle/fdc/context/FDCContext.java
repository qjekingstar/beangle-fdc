package org.beangle.fdc.context;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public interface FDCContext extends Map<String, Object>,Serializable{
	public ExceptionHolder getExceptionHolder();
	
	public String getServerName();
	
	public String getServerAddr();
	
	public String getRequestURI();
	
	public Locale getLocale();
	
	public String getUserAgent();
	
	public Map<String,Object> getParams();
	
	public Map<String,Object> getSession();
	
	public String toJSONString();
}
