package cn.dlj1.cms.entity;

import java.io.Serializable;

/**
 * 实体 父类
 */
public interface Entity extends Serializable {

    Serializable getId();

    public static interface add {

    }

    public static interface delete {
    }

    public static interface edit {

    }

    public static interface view {

    }
}
