package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Pager;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import org.omg.CORBA.Request;

import java.util.List;
import java.util.Map;

/**
 * 数据表格接口
 *
 *      前端查询请求 => 主方法(table);
 *      table 方法调用：
 *
 *          bindQuery   绑定查询属性
 *          validate    验证属性
 *          queryCount  查询总条数
 *          queryData   查询数据
 *          getOthers   其他附加数据
 *          callback    返回
 *
 *
 * @param <K>
 * @param <T>
 */
public interface TableService<K extends Key, T extends Entity> extends Service<K, T> {

    @Override
    Dao<K,T> getDao();

    Result table();

    Query bindQuery();

    Result validate(Query query);

    Pager queryCount(Query query);

    List<Map<String,Object>> queryData(Query query);

    Object getOthers(Query query);

    Result callback(Query query, List<Map<String,Object>> data, Object others);

    String[] getBtnsKey();

    void export();

}
