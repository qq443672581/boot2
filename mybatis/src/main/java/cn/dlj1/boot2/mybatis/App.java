package cn.dlj1.boot2.mybatis;

import cn.dlj1.boot2.mybatis.entity.User;
import cn.dlj1.boot2.mybatis.mapper.UserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@SpringBootApplication
@RestController
//@MapperScan("cn.dlj1.boot2.mybatis.mapper")
public class App {

    private static Log log = LogFactory.getLog(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all")
    public Object all(){
        return userMapper.findAll();
    }

    @GetMapping("/one")
    public Object one(int id){
        return userMapper.findOne(id);
    }

    @GetMapping("/insert")
    public Object insert(User user){
        Object o = userMapper.insert(user);
        System.out.println(        user.getId());
        return user;
    }



}
