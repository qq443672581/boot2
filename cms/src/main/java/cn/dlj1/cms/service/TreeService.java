package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;

public interface TreeService<T extends Entity> extends Service<T> {

    @Override
    Dao<T> getDao();

    default Result tree(Query query) {

        return Result.SUCCESS;
    }


}
