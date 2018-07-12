package cn.dlj1.boot2.jwt;

import cn.dlj1.boot2.jwt.filter.JwtFilter;
import cn.dlj1.boot2.jwt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
public class App {

    @Autowired
    private LoginService loginService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // 注册filter
    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    // 测试接口
    @RequestMapping
    public String index() {
        return "这是一个简单的springboot项目";
    }

    // 登录接口
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String passport, String password) {
        return loginService.login(request, response, passport, password);
    }

}
