package cn.dlj1.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@SpringBootApplication
public class Test {

    @ShellMethod("介绍")
    public int add(int a, int b) {
        return a + b;
    }


    public static void main(String[] args) {
        new SpringApplication(Test.class).run(args);
    }

}
