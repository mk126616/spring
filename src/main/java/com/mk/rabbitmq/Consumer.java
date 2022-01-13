package com.mk.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer
{
    @Test
    public void consumerMessage() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //设置不公平分发，根据消费者处理效率分发，能者多劳
        channel.basicQos(2);

        //获取到消息回调
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(String.format("接收到消息:%s", new String(delivery.getBody())));
            //手动确认，并且只确认当前消息，不确认信道中其他消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback = s -> System.out.println("消息被撤销");
        /**
         * 接收消息。会持续坚挺，不能关闭channel和Connection
         * 参数一：队列名称
         * 参数二：消息是否自动确认，true表示自动确认接收完消息以后会自动将消息从队列移除。否则需要手动ack消息
         * 参数三：消息接收者的标签，用于多个消费者同时监听一个队列时用于确认不同消费者。
         * 参数四：消息接收者
         */
            //消息推送
         channel.basicConsume("myQueue", false, deliverCallback, cancelCallback);
        //消息获取
//        GetResponse response = channel.basicGet("myQueue", false);
//        System.out.println("接收到消息："+new String(response.getBody()));
        Thread.currentThread().sleep(99999999999L);
    }

}
