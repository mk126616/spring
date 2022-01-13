package com.mk.rabbitmq.spring;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟交换机插件实现每个消息不同时间的延迟
 */
@Configuration
public class SpringRabbitmqConfig
{
    /**
     * 延迟交换机插件实现延迟队列
     * @return
     */
//    @Bean
    public Queue delayQueue(){
        return new Queue("delayQueue",false,true,true);
    }
//    @Bean
    public CustomExchange delayExchange(){
        Map<String,Object> argument = new HashMap<>();
        argument.put("x-delayed-type","direct");
        return new CustomExchange("delayExchange","x-delayed-message",false,true,argument);
    }
//    @Bean
    public Binding binding(@Qualifier("delayQueue") Queue delayQueue,@Qualifier("delayExchange")CustomExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("delay-routing-key").noargs();
    }

    /**
     * 优先级队列
     */
    @Bean
    public Queue priorityQueue(){
        Map<String,Object> argument = new HashMap<>();
        argument.put("x-max-priority",10);//优先级队列
        argument.put("x-queue-mode","lazy"); //惰性队列
        return new Queue("priorityQueue",true,false,false,argument);
    }
    @Bean
    public DirectExchange priorityExchange(){
        return new DirectExchange("priorityExchange", true, false);
    }
    @Bean
    public Binding priorityBinding(@Qualifier("priorityQueue") Queue queue,@Qualifier("priorityExchange")DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("priority-routing-key");
    }
}
