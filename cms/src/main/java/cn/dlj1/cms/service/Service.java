package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;

/**
 * 服务顶级接口
 */
public interface Service/*<K extends Key, T extends Entity>*/ {

    /**
     * 获取Dao
     *
     * @return
     */
    Dao getDao();

//    /**
//     * 获取主键类型
//     *
//     * @return
//     */
//    default Class<K> getKeyType() {
//        return (Class<K>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
//    }
//
//    /**
//     * 获取模块类型
//     *
//     * @return
//     */
//    default Class<T> getModuleClazz() {
//        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
//    }

}
