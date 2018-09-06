package cn.dlj1.cms.lqbz;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 动态代理
 */
public class Proxy {

    public static interface Student {
        void say(String name);
    }

    public static class DefaultStudent implements Student {
        @Override
        public void say(String name) {
            System.out.println(name + ",说你好");
        }
    }

    public static interface Teacher {
        void say(String name);
    }

    public static class DefaultTeacher implements Teacher {
        @Override
        public void say(String name) {
            System.out.println(name + ",说你好");
        }
    }

    public static class ProxyHandler implements InvocationHandler {

        private static final List list = new ArrayList();
        private Object obj;

        public ProxyHandler(Object clazz) {
            this.obj = clazz;
        }

        public Object bind() {
            return
                    java.lang.reflect.Proxy.newProxyInstance(
                            obj.getClass().getClassLoader(),
                            obj.getClass().getInterfaces(),
                            this
                    );
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            list.add(args[0]);

            result = method.invoke(obj, args);
            System.out.println(list.size());

            return result;
        }
    }

    @Test
    public void test() {

        Student student = (Student) new ProxyHandler(new Proxy.DefaultStudent()).bind();
        Teacher teacher = (Teacher) new ProxyHandler(new Proxy.DefaultTeacher()).bind();

        student.say("tom");
        teacher.say("tom2");


    }


}
