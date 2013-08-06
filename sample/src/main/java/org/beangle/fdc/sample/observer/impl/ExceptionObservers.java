package org.beangle.fdc.sample.observer.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.fdc.context.FDCContext;
import org.beangle.fdc.observer.ExceptionObserver;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class ExceptionObservers implements org.beangle.fdc.observer.ExceptionObservers{
	private List<ExceptionObserver> observers = CollectUtils.newArrayList();

	protected static final Logger LOG = LoggerFactory.getLogger(ExceptionObservers.class);

	protected Logger categoryLogger;
	protected boolean logEnabled = false;
	protected String logCategory;
	protected String logLevel;

	public void handle(FDCContext context) {
		for (ExceptionObserver observer : observers) {
			try {
				observer.handle(context);
			} catch (Exception e) {
				if (isLogEnabled()) {
					handleLogging(e);
				}
			}
		}
	}

	public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public String getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(String logCatgory) {
		this.logCategory = logCatgory;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	protected void handleLogging(Exception e) {
		if (logCategory != null) {
			if (categoryLogger == null) {
				// init category logger
				categoryLogger = LoggerFactory.getLogger(logCategory);
			}
			doLog(categoryLogger, e);
		} else {
			doLog(LOG, e);
		}
	}

	protected void doLog(Logger logger, Exception e) {
		if (logLevel == null) {
			logger.debug(e.getMessage(), e);
			return;
		}

		if ("trace".equalsIgnoreCase(logLevel)) {
			logger.trace(e.getMessage(), e);
		} else if ("debug".equalsIgnoreCase(logLevel)) {
			logger.debug(e.getMessage(), e);
		} else if ("info".equalsIgnoreCase(logLevel)) {
			logger.info(e.getMessage(), e);
		} else if ("warn".equalsIgnoreCase(logLevel)) {
			logger.warn(e.getMessage(), e);
		} else if ("error".equalsIgnoreCase(logLevel)) {
			logger.error(e.getMessage(), e);
		} else if ("fatal".equalsIgnoreCase(logLevel)) {
			logger.fatal(e.getMessage(), e);
		} else {
			throw new IllegalArgumentException("LogLevel [" + logLevel + "] is not supported");
		}
	}

	public void setObservers(List<ExceptionObserver> observers) {
		this.observers = observers;
	}

	public List<ExceptionObserver> getObservers() {
		return observers;
	}
}
