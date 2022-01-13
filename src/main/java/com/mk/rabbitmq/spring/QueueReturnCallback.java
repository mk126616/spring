package com.mk.rabbitmq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 交换机接收到消息后消息未送到目的队列时调用
 */
@Component("queueReturnCallback")
public class QueueReturnCallback implements RabbitTemplate.ReturnCallback
{

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey)
    {
        System.out.println("消息发送失败");
    }
}
