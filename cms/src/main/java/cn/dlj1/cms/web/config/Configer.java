package cn.dlj1.cms.web.config;

import cn.dlj1.cms.web.filter.RequestFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Configer {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        return registration;
    }

}
