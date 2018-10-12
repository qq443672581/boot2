package cn.dlj1.rabbitMQ.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Producer {

    @Test
    public void test() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("test");
        factory.setPassword("test");
        //设置 RabbitMQ 地址
        factory.setHost("localhost");
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //获得信道
        Channel channel = conn.createChannel();

        // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
        channel.queueDeclare("test", false, false, false, null);

        String message = String.format("当前时间：%s", new Date().getTime());
        // 推送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
        channel.basicPublish("", "test", null, message.getBytes("UTF-8"));

        channel.close();
        conn.close();
    }

}
