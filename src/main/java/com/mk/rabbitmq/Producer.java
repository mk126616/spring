package com.mk.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 */
public class Producer
{

    /**
     * 同步确认消息
     * @throws Exception
     */
    @Test
    public void publishMessage() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 声明一个队列。
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
         * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
         * 参数五：队列的附加属性
         * 注意：
         * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
         * 2.队列名可以任意取值，但需要与消息接收者一致。
         * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
         */
        channel.queueDeclare("myQueue", true, false, false, null);

        //开启消息发布确认
        channel.confirmSelect();
        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String message = scanner.next();
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示使用默认的交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */
            channel.basicPublish("", "myQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            //确认消息发送是否成功：阻塞的去等待broker返回,可以增加时间参数，超时则抛出异常
            boolean sendSuccess = channel.waitForConfirms(3000);
            if (sendSuccess)
            {
                System.out.println(String.format("消息已经被发送:%s", message));
            }
            else
            {
                System.out.println(String.format("消息发送失败:%s", message));
            }

        }

    }

    /**
     * 异步确认消息
     * @throws Exception
     */
    @Test
    public void publishMessageAsync() throws Exception
    {
        //消息容器
        ConcurrentHashMap<Long, String> messageCache = new ConcurrentHashMap<>();
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 声明一个队列。
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
         * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
         * 参数五：队列的附加属性
         * 注意：
         * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
         * 2.队列名可以任意取值，但需要与消息接收者一致。
         * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
         */
        channel.queueDeclare("myQueue", true, false, false, null);

        //开启消息发布确认
        channel.confirmSelect();
        /**
         * 交换机获取到消息后未送达队列调用
         */
        channel.addReturnListener(new ReturnCallback()
        {
            @Override
            public void handle(Return aReturn)
            {
                System.out.println("消息未被送达队列");
            }
        });
        //异步消息发布确认监听器(交换机是否获取到消息都会调用)
        channel.addConfirmListener(new ConfirmListener()
        {
            @Override
            public void handleAck(long deliveryTag, boolean b) throws IOException
            {
                //删除消息缓存
                messageCache.remove(deliveryTag);
                //l为消息标识，可以确定那个消息成功那个失败
                System.out.println(String.format("交换机获取到了消息,消息标识：%s", deliveryTag));

            }

            @Override
            public void handleNack(long deliveryTag, boolean b) throws IOException
            {
                //删除消息缓存
                messageCache.put(channel.getNextPublishSeqNo(), messageCache.get(deliveryTag));
                messageCache.remove(deliveryTag);
                //重新发送消息
                channel.basicPublish("", "myQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, messageCache.get(deliveryTag).getBytes());
                System.out.println(String.format("消息发布失败,已重发，消息标识：%s", deliveryTag));
            }
        });

        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String message = scanner.next();
            messageCache.put(channel.getNextPublishSeqNo(), message);
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示使用默认的交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */
            channel.basicPublish("", "myQueue", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }

    }

    /**
     * 交换机使用
     * @throws Exception
     */
    @Test
    public void publishMessageUseExchange() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //队列1
        String queueName1 = "exchangeQueue1";
        //队列2
        String queueName2 = "exchangeQueue2";
        //队列1
//        String queueName3 = "exchangeQueue3";
//        //队列2
//        String queueName4 = "exchangeQueue4";
        //交换机名称
        String exchangeName = "fanoutExchange";
        channel.queueDelete(queueName1);
        channel.queueDelete(queueName2);
//        channel.queueDelete(queueName3);
//        channel.queueDelete(queueName4);
        channel.exchangeDelete(exchangeName);


        Map<String,Object> map1 = new HashMap<>();
        map1.put("k1","v1");
        map1.put("k2","v2");
        Map<String,Object> map2 = new HashMap<>();
        map1.put("kk1","vv1");
        map1.put("kk2","vv2");
        /**
         * 声明一个队列。
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
         * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
         * 参数五：队列的附加属性
         * 注意：
         * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
         * 2.队列名可以任意取值，但需要与消息接收者一致。
         * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
         */
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);
//        channel.queueDeclare(queueName3, true, false, false, null);
//        channel.queueDeclare(queueName4, true, false, false, null);
        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：路由策略
         */
//        channel.exchangeDeclare(exchangeName,BuiltinExchangeType.HEADERS);
//        //队列绑定交换机
//        channel.queueBind(queueName1,exchangeName,"陕西.渭南");
//        channel.queueBind(queueName2,exchangeName,"陕西.#");
//        channel.queueBind(queueName3,exchangeName,"甘肃.#");
//        channel.queueBind(queueName4,exchangeName,"#");

        channel.exchangeDeclare(exchangeName,BuiltinExchangeType.HEADERS);
        //队列绑定交换机
        channel.queueBind(queueName1,exchangeName,"",map1);
        channel.queueBind(queueName2,exchangeName,"",map2);



        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String scannerInfo = scanner.next();
            String routingKey = scannerInfo.split(":")[0];
            String message = scannerInfo.split(":")[1];
            Map<String,Object> map = null;
            if(routingKey.equals("1")){
                map = map1;
            }else{
                map = map2;
            }
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示不用交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */
            AMQP.BasicProperties properties= MessageProperties.PERSISTENT_TEXT_PLAIN.builder().headers(map).build();
            channel.basicPublish(exchangeName, routingKey, properties, message.getBytes());
            System.out.println(String.format("消息推送完毕：%s",message));
        }
    }


    /**
     * 交换机使用
     * @throws Exception
     */
    @Test
    public void publishMessageUseExchange2() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //队列1
        String queueName1 = "exchangeQueue1";
        //队列2
        String queueName2 = "exchangeQueue2";
        //交换机名称
        String exchangeName = "fanoutExchange";
        channel.queueDelete(queueName1);
        channel.queueDelete(queueName2);
        channel.exchangeDelete(exchangeName);

        /**
         * 声明一个队列。
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
         * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
         * 参数五：队列的附加属性
         * 注意：
         * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
         * 2.队列名可以任意取值，但需要与消息接收者一致。
         * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
         */
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);
        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：路由策略
         */
        Map<String,Object> arg = new HashMap<>();
        arg.put("alternate-exchange","backupExchange");//备份交换机
        channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT);
        //队列绑定交换机
        channel.queueBind(queueName1,exchangeName,"q1");
        channel.queueBind(queueName2,exchangeName,"q1");
        channel.queueBind(queueName2,exchangeName,"q2");



        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String scannerInfo = scanner.next();
            String routingKey = scannerInfo.split(":")[0];
            String message = scannerInfo.split(":")[1];
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示不用交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */
            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(String.format("消息推送完毕：%s",message));
        }
    }


    /**
     * 给spring消费者发送消息
     * @throws Exception
     */
    @Test
    public void publishMessageUseExchange3() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //交换机名称
        String exchangeName = "spring_exchange";

        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String message = scanner.next();
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示不用交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */
            channel.basicPublish(exchangeName, "routingKey", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(String.format("消息推送完毕：%s",message));
        }
    }

    /**
     * 死信队列
     * @throws Exception
     */
    @Test
    public void publishMessageWithDeadQueue() throws Exception
    {
        //获取连接
        Connection connection = RabbitMqUtils.getConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //队列1
        String normalQueue = "normalQueue";
        //队列2
        String diedQueue = "diedQueue";
        //交换机名称
        String normalExchangeName = "normalExchange";
        //交换机名称
        String diedExchangeName = "diedExchange";
        channel.queueDelete(normalQueue);
        channel.queueDelete(diedQueue);
        channel.exchangeDelete(normalExchangeName);
        channel.exchangeDelete(diedExchangeName);
        /**
         * 声明一个队列。
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否排外  如果排外则这个队列只允许有一个消费者
         * 参数四：是否自动删除队列，如果为true表示没有消息也没有消费者连接自动删除队列
         * 参数五：队列的附加属性
         * 注意：
         * 1.声明队列时，如果已经存在则放弃声明，如果不存在则会声明一个新队列；
         * 2.队列名可以任意取值，但需要与消息接收者一致。
         * 3.下面的代码可有可无，一定在发送消息前确认队列名称已经存在RabbitMQ中，否则消息会发送失败。
         */
        Map<String,Object> param = new HashMap<>();//正常队列的参数
        param.put("x-dead-letter-exchange", diedExchangeName);//指定该队列的死信交换机
        channel.queueDeclare(normalQueue, false, false, false, param);
        //声明死信队列
        channel.queueDeclare(diedQueue, false, false, false, null);

        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：路由策略
         */
        channel.exchangeDeclare(normalExchangeName,BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(diedExchangeName,BuiltinExchangeType.TOPIC);
        //队列绑定交换机
        channel.queueBind(normalQueue,normalExchangeName,"#");
        channel.queueBind(diedQueue,diedExchangeName,"#");


        //控住台获取信息发送消息
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String scannerInfo = scanner.next();
            String routingKey = scannerInfo.split(":")[0];
            String message = scannerInfo.split(":")[1];

            //声明过期时间
            AMQP.BasicProperties properties = MessageProperties.PERSISTENT_TEXT_PLAIN.builder().expiration("5000").build();
            /**
             * 发送消息到MQ
             * 参数一：交换机名称，为""表示不用交换机
             * 参数二:为队列名称或者routingKey.当指定了交换机就是routingKey
             * 参数三：消息的属性信息  MessageProperties.PERSISTENT_TEXT_PLAIN为消息持久化
             * 参数四：消息内容的字节数组
             */

            channel.basicPublish(normalExchangeName, routingKey, properties, message.getBytes());
            System.out.println(String.format("消息推送完毕：%s",message));
        }
    }
}
