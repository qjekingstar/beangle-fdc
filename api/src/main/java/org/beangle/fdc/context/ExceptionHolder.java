package org.beangle.fdc.context;

import java.io.Serializable;

public interface ExceptionHolder extends Serializable {
	public Exception getException();

	public String getExceptionStack();
}
