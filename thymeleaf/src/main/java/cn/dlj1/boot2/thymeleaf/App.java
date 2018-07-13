package cn.dlj1.boot2.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SpringBootApplication

@Controller
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping
    public String index(Model model){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");

        model.addAttribute("list", list);
        return "index";
    }

}
