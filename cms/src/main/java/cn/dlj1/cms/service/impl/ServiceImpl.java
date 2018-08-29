package cn.dlj1.cms.service.impl;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现
 */
public class ServiceImpl<K extends Key, T extends Entity> implements Service<K, T> {

    private Dao<K, T> dao;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Dao<K, T> getDao() {

        return dao;
    }

    @Override
    public void setDao(Dao<K, T> dao) {
        this.dao = dao;
    }


    @Override
    public HttpServletRequest getRequest() {
        return request;
    }
}
