package cn.dlj1.cms.service;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;

import java.util.List;
import java.util.Map;

public interface TreeService<T extends Entity> extends Service<T> {

    default Result tree(Query query) {
        List<Map<String, Object>> list = getDao().selectMaps(null);

        return Result.SUCCESS;
    }


}
