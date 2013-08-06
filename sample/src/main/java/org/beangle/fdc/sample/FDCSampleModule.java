package org.beangle.fdc.sample;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.fdc.jms.producer.ExceptionMessageProducer;
import org.beangle.fdc.sample.observer.impl.ExceptionFileObserver;
import org.beangle.fdc.sample.observer.impl.ExceptionJMSObserver;
import org.beangle.fdc.sample.observer.impl.ExceptionObservers;
import org.beangle.fdc.sample.web.action.TestAction;
import org.springframework.jms.core.JmsTemplate;

public class FDCSampleModule extends AbstractBindModule{

	@Override
	protected void doBinding() {
		bind("jmsTemplate_1",JmsTemplate.class)
			.property("connectionFactory",ref("connectionFactory"))
			.property("messageConverter",ref("fdcMessageConverter"));
		
		bind("jmsTemplate_2",JmsTemplate.class)
			.property("connectionFactory",ref("connectionFactory_1"))
			.property("messageConverter",ref("fdcMessageConverter"));
					
		bind("queueProducer", ExceptionMessageProducer.class)
			.property("destination", ref("destination"))
			.property("jmsTemplates",list(ref("jmsTemplate_1"),ref("jmsTemplate_2")));
		
		bind("exceptionJMSObserver", ExceptionJMSObserver.class)
			.property("producers",list(ref("queueProducer")));
		
		bind("exceptionFileObserver", ExceptionFileObserver.class);
		
		bind(org.beangle.fdc.observer.ExceptionObservers.class.getName(),ExceptionObservers.class)
			.property("observers", list(ref("exceptionJMSObserver"),ref("exceptionFileObserver")));
		
		bind(TestAction.class);
	}
}
