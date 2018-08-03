package cn.dlj1.boot2.auth.entity.menu;

public interface Menu {

    String id();

    String name();

    String path();

    boolean isAction();

    boolean isUsed();

    boolean isShow();

}
