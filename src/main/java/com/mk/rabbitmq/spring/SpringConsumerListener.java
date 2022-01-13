package com.mk.rabbitmq.spring;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component("springConsumerListener")
public class SpringConsumerListener implements MessageListener
{
    @Override
    public void onMessage(Message message)
    {
       System.out.println(new String(message.getBody()));
    }
}
