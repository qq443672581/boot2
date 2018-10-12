package cn.dlj1.rabbitMQ.test;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    @Test
    public void test() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("test");
        factory.setPassword("test");
        factory.setHost("localhost");
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //获得信道
        final Channel channel = conn.createChannel();

        channel.queueDeclare("test", false, false, false, null);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "utf-8"); // 消息正文
                System.out.println("收到消息 => " + message);

                // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于当前id的消息】
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume("test", true, consumer);

        channel.close();
        conn.close();
    }

}
