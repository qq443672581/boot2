package cn.dlj1.boot2.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@SpringBootApplication

@RestController
public class App {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(100);

    @RequestMapping
    public String index() {
        return "这是一个简单的springboot项目";
    }

    @Autowired
    private RestTemplate template;
    static AtomicInteger in;
    static long time = System.currentTimeMillis();

    @GetMapping("/test")
    public String test() {

        time = System.currentTimeMillis();
        in = new AtomicInteger(0);
        int size = 50000;

        for (int i = 0; i < size; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                long time = System.currentTimeMillis();
                template.getForEntity("http://192.168.1.222:8080/", String.class);
                in.addAndGet(1);
                System.out.println(finalI+"  "+ (System.currentTimeMillis() - time));
            });
        }

        return "这是一个简单的springboot项目";
    }


}
