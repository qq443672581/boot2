package cn.dlj1.boot2.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 */
@SpringBootApplication
@RestController
@EnableCaching
public class App {

    @Autowired
    private HttpServletRequest request;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping
    public String index() {
        // CacheManager cacheManager = new SimpleCacheManager();
        return "index";
    }

    @Cacheable(value = "test", key = "#id")
    @RequestMapping("get/{id}")
    public String get(@PathVariable String id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @CachePut(value = "test", key = "#id")
    @RequestMapping("set/{id}")
    public String set(@PathVariable String id) {
        // 如果不定义自己的CacheManger 使用的是 org.springframework.cache.concurrent.ConcurrentMapCacheManager
        Map<String, CacheManager> map = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBeansOfType(CacheManager.class);
        for (Map.Entry<String, CacheManager> kv : map.entrySet()) {
            System.out.println(kv.getKey());
            System.out.println(kv.getValue());
        }
        return id + 1;
    }

    @CacheEvict(value = "test", key = "#id")
    @RequestMapping("del/{id}")
    public String del(@PathVariable String id) {
        return "success";
    }


}
