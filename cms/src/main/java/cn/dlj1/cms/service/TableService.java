package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.condition.Cnd;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Pager;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.QueryWrapperParse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Map;

public interface TableService<T extends Entity> extends Service<T> {

    @Override
    Dao<T> getDao();

    default Result table(Query query) {
        if (null == query.getPager()) {
            query.setPager(new Pager().init());
        }

        Result result = validate(query);
        if (result != Result.SUCCESS) {
            return result;
        }

        queryCount(query);
        if (query.getPager().isEmpty()) {
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

    default void queryCount(Query query) {
        Cnd[] cnds = query.getCnds();
        QueryWrapper<T> queryWrapper = null;
        if (cnds.length > 0) {
            queryWrapper = new QueryWrapper<T>();
            QueryWrapperParse.parse(queryWrapper, cnds);
        }

        int count = getDao().selectCount(queryWrapper);
        query.getPager().setCount(count);

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
