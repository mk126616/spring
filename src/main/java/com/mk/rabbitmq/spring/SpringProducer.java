package com.mk.rabbitmq.spring;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
@Service
public class SpringProducer
{
    @Autowired
    private RabbitTemplate amqpTemplate;

    /**
     * 死信队列消息
     */
    @Test
    public void sendMessage(){
        amqpTemplate.convertAndSend("spring-test-exchange","routing_key_spring", "第一条消息", msg -> {msg.getMessageProperties().setExpiration("10000");return msg;});
        System.out.println("消息发送成功");
    }

    /**
     * 延迟交换机插件实现延迟消息
     */
    public void sendMessageWithDelayMessage(String message,int delayTime){
        CorrelationData correlationData = new CorrelationData(); //设置消息发送确认回调的信息
        amqpTemplate.convertAndSend("delayExchange","delay-routing-key",message, msg -> {correlationData.setReturnedMessage(msg);msg.getMessageProperties().setDelay(delayTime);return msg;},correlationData);
        System.out.println("消息发送成功");
    }


    /**
     * 发送消息给优先级队列
     */
    @Test
    public void sendMessageToPriorityQueue(){
        for(int i = 1;i<11;i++){

            if(i==5){
                amqpTemplate.convertAndSend("priorityExchange","priority-routing-key","消息"+i, msg -> {msg.getMessageProperties().setPriority(5);return msg;});
            }else{
                amqpTemplate.convertAndSend("priorityExchange","priority-routing-key","消息"+i, msg -> {msg.getMessageProperties().setPriority(1);return msg;});
            }
        }
    }
}
