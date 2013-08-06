package org.beangle.fdc.observer;

import org.beangle.fdc.context.FDCContext;

public interface ExceptionObserver {
	public void handle(FDCContext context) throws Exception;
}
