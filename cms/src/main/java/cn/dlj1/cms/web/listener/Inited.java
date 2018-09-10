package cn.dlj1.cms.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Inited implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        Map<String, ?> map = context.getBeansOfType(HttpMessageConverter.class);
        for (String o : map.keySet()) {
            System.out.println(map.get(o));
        }

    }
}
