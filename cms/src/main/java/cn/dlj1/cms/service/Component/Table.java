package cn.dlj1.cms.service.Component;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Pager;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;

import java.util.List;
import java.util.Map;

public interface Table<K extends Key, T extends Entity> extends Component<K,T> {

    default Result table(T clazz, Query query) {
        Result result = validate(query);
        if (result != Result.SUCCESS) {
            return result;
        }

        Pager pager = queryCount(query);
        if(pager == Pager.EMPTY){
            return Result.SUCCESS;
        }

        List<Map<String, Object>> data = queryData(query);
        Object others = getOthers(query);

        result = callback(query, data, others);
        return result;
    }

    Result validate(Query query);

    Pager queryCount(Query query);

    List<Map<String, Object>> queryData(Query query);

    Object getOthers(Query query);

    Result callback(Query query, List<Map<String, Object>> data, Object others);


}
