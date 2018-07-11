package cn.dlj1.boot2.session_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@SpringBootApplication
@RestController
// 30秒过期
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("get")
    public Object get(HttpSession session){
        return session.getAttribute("prop1");
    }

    @RequestMapping("set")
    public Object set(HttpSession session){
        long s = new Date().getTime();
        session.setAttribute("prop1",s);
        session.setAttribute("prop2",s);
        return s;
    }

}
