package org.beangle.fdc.jms.producer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.activemq.command.ActiveMQQueue;
import org.beangle.fdc.context.FDCContext;
import org.springframework.jms.core.JmsTemplate;

public class ExceptionMessageProducer {
	private ActiveMQQueue destination;

	private List<JmsTemplate> jmsTemplates;

	private AtomicInteger current = new AtomicInteger(0);

	private JmsTemplate getJmsTemplate() {
		int cur = current.getAndIncrement();
		int index = cur % jmsTemplates.size();
		return jmsTemplates.get(index);
	}

	public void sendMessage(FDCContext context) {
		this.getJmsTemplate().convertAndSend(destination, context);
	}
	
	public ActiveMQQueue getDestination() {
		return destination;
	}

	public void setDestination(ActiveMQQueue destination) {
		this.destination = destination;
	}

	public void setJmsTemplates(List<JmsTemplate> jmsTemplate) {
		this.jmsTemplates = jmsTemplate;
	}
}