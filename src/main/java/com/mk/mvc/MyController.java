package com.mk.mvc;
import com.mk.rabbitmq.spring.SpringProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController
{
    @Autowired
    private SpringProducer mqProducer;

    @RequestMapping("/redis/lock")
    public String testHandler()
    {

        return "测试";
    }

    /**
     * 延迟消息发送
     * @param msg
     * @param delayTime
     * @return
     */
    @RequestMapping("/mq/delay/{msg}/{delayTime}")
    public String delayMessage(@PathVariable String msg,@PathVariable Integer delayTime)
    {
        mqProducer.sendMessageWithDelayMessage(msg,delayTime);
        return "消息已经发送";
    }

    /**
     * 正常消息发送
     * @return
     */
    @RequestMapping("/mq")
    public String message()
    {
        mqProducer.sendMessage();
        return "消息已经发送";
    }

    /**
     * 正常消息发送
     * @return
     */
    @RequestMapping("/mq/priority")
    public String priorityMessage()
    {
        mqProducer.sendMessageToPriorityQueue();
        return "消息已经发送";
    }


}
