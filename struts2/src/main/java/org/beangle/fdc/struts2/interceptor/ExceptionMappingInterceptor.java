package org.beangle.fdc.struts2.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.beangle.fdc.context.Struts2FDCContext;
import org.beangle.fdc.observer.ExceptionObservers;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;

public class ExceptionMappingInterceptor extends com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor {
	private static final long serialVersionUID = -7238417334855807316L;

	@Inject
	protected ObjectFactory objectFactory;
	
	@Override
    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		invocation.getStack().push(exceptionHolder);
		try {
			//FIXME objectFactory的实例不是SpringObjectFactory的情况下，ExceptionObservers的observer队列可能为空
			ExceptionObservers observers = (ExceptionObservers) objectFactory.buildBean(ExceptionObservers.class.getName(), null,true);
			observers.handle(Struts2FDCContext.create(exceptionHolder));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}