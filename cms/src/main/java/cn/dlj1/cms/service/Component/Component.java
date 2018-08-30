package cn.dlj1.cms.service.Component;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;

/**
 *
 *
 */
public interface Component<K extends Key, T extends Entity> {

    Dao<K, T> getDao();

}
