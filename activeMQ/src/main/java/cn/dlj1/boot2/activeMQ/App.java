package cn.dlj1.boot2.activeMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // 测试接口
    @RequestMapping
    public String index() {
        return "这是一个简单的springboot项目";
    }


}
