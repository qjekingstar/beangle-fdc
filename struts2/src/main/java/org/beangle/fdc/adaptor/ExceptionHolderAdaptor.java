package org.beangle.fdc.adaptor;

import org.beangle.fdc.context.ExceptionHolder;

public class ExceptionHolderAdaptor {
	public static ExceptionHolder adapte(final com.opensymphony.xwork2.interceptor.ExceptionHolder exceptionHolder){
		return new ExceptionHolder() {
			private static final long serialVersionUID = 6699235983156764018L;

			public String getExceptionStack() {
				return exceptionHolder.getExceptionStack();
			}
			
			public Exception getException() {
				return exceptionHolder.getException();
			}
		};
	}
}
