package cn.dlj1.cms.entity;

import java.io.Serializable;

/**
 * 实体 父类
 */
public interface Entity {

    Serializable getId();

    public static interface insert {
    }

    public static interface update {
    }
}
