package com.mk.rabbitmq.spring;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 交换机接收或失败都会回调
 */
@Component("exchangeConfirmCallback")
public class ExchangeConfirmCallback implements RabbitTemplate.ConfirmCallback
{

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause)
    {
        if(ack){
            System.out.println("消息被交换机接收");
        }else{
            System.out.println("消息未被交换机接收到");
        }
    }
}
