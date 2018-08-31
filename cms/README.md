### CMS 系统

模块化开发，需要什么模块实现什么模块，这个便捷的功能是通过jdk1.8之后的default关键字实现的：
```java

public interface A{
    default String get(){
        return null;
    }
}
public interface B{
    default void post(Entity entity){
        //...
    }
}

public class C implements A,B{
    public static void main(String[] args){
        C c = new C();
        c.get();
        c.post(null);
    }
}

```