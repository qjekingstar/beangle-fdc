package org.beangle.fdc.sample.web.action;

import org.beangle.struts2.action.ActionSupport;


public class TestAction extends ActionSupport{
	private static final long serialVersionUID = 3879686836380475610L;
	
	public String index(){
		return forward();
	}
}
