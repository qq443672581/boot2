package cn.dlj1.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

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
    public void test(HttpServletResponse response) throws IOException {
//        response.setContentType("image/png");
//        PrintWriter writer = new PrintWriter(response.getOutputStream());

ByteArrayOutputStream stream = new ByteArrayOutputStream(1);
stream.write(new byte[]{1});
stream.writeTo(response.getOutputStream());
stream.close();

//
//        writer.write(new char[]{'a'});
//
//        writer.close();

    }


}
