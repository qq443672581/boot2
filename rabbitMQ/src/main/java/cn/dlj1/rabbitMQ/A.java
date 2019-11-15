package cn.dlj1.rabbitMQ;

import com.rabbitmq.client.*;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class A {

    public static void main(String[] args){
get();


    }

    static  void push(){
        Channel channel = getChannel();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000 * 10; i++) {

            try {
                channel.basicPublish("test","",null,
                        (DateFormatUtils.format(new Date(),"HH:mm:ss")).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("time:" + (System.currentTimeMillis() - time));
        close(channel);
    }
    static void get(){
        Channel channel = getChannel();

        try {
            channel.queueDeclare("x",false,false,false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        close(channel);
    }

    static Channel getChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("u");
        factory.setPassword("123456");
        factory.setVirtualHost("test");
        factory.setHost("jd.dlj1.cn");
        factory.setPort(5672);
        try {
            return factory.newConnection().createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void close(Channel channel){
        try {
            channel.close();
            channel.getConnection().close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
