package org.beangle.fdc.sample.observer.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.fdc.context.FDCContext;
import org.beangle.fdc.jms.producer.ExceptionMessageProducer;
import org.beangle.fdc.observer.ExceptionObserver;

public class ExceptionJMSObserver implements ExceptionObserver{
	
	protected List<ExceptionMessageProducer> producers = CollectUtils.newArrayList(); 

	public void handle(FDCContext context) throws Exception {
		for (ExceptionMessageProducer producer : producers) {
			producer.sendMessage(context);
		}
	}

	public void setProducers(List<ExceptionMessageProducer> producers) {
		this.producers = producers;
	}
}
