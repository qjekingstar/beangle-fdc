package org.beangle.fdc.observer;

import java.util.List;

import org.beangle.fdc.context.FDCContext;

public interface ExceptionObservers {
	public void handle(FDCContext context);

	public void setObservers(List<ExceptionObserver> observers);

	public List<ExceptionObserver> getObservers();
}
