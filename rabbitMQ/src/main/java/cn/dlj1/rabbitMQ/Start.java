package cn.dlj1.rabbitMQ;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class Start {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Start.class,args);
    }

    @RabbitListener(queues = "test")
    public void processA(byte[] content) {
        System.out.println(new String(content));
    }


//    @PostConstruct
    public void customer(){

        Map map = new HashMap();
        map.put("a",1);
        map.put("b",2);
        String s = JSON.toJSONString(map);
        rabbitTemplate.convertAndSend("test","",s);
    }

}
