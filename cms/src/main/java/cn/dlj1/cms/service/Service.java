package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务上级接口
 */
public interface Service<K extends Key,T extends Entity> {

    Dao<K,T> getDao();

    void setDao(Dao<K,T> dao);

    HttpServletRequest getRequest();

}
