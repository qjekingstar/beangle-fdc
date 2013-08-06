package org.beangle.fdc.jms.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.beangle.fdc.context.FDCContext;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class FDCMessageConverter implements MessageConverter{
	
    public Object fromMessage(Message message) throws JMSException,MessageConversionException {
        Object object = null;
        if(message  instanceof ObjectMessage) {
            byte[] obj = (byte[])((ObjectMessage)message).getObject();
            ByteArrayInputStream bis = new ByteArrayInputStream(obj);
            try {
                ObjectInputStream ois = new ObjectInputStream(bis);
                object = ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public Message toMessage(Object object, Session session) throws JMSException,MessageConversionException {
        ObjectMessage objectMessage = session.createObjectMessage();
        Object sendObject = object;
        if(object instanceof FDCContext){
        	FDCContext context =  (FDCContext) object;
        	sendObject = context.toJSONString();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(sendObject);
            
            byte[] objMessage = bos.toByteArray();
            objectMessage.setObject(objMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMessage;
    }
}