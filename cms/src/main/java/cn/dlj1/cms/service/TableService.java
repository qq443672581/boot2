package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Pager;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;

import java.util.List;
import java.util.Map;

public interface TableService extends Service {

    @Override
    Dao getDao();

    default Result table(Query query) {

        Result result = validate(query);
        if (result != Result.SUCCESS) {
            return result;
        }

        Pager pager = queryCount(query);
        if (pager == Pager.EMPTY) {
            return Result.SUCCESS;
        }

        List<Map<String, Object>> data = queryData(query);
        Object others = getOthers(query);

        result = callback(query, data, others);

        return result;
    }

    default Result validate(Query query) {
        return Result.SUCCESS;
    }

    default Pager queryCount(Query query) {
        return Pager.EMPTY;
    }

    default List<Map<String, Object>> queryData(Query query) {
        return null;
    }

    default Object getOthers(Query query) {
        return null;
    }

    default Result callback(Query query, List<Map<String, Object>> data, Object others) {
        return null;
    }


}
