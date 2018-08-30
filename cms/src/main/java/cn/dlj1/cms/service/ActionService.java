package cn.dlj1.cms.service;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;

public interface ActionService<K extends Key, T extends Entity> {

    Result add();

    Result edit();

    Result delete();

}
