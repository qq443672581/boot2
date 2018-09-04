package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Pager;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.QueryWrapperParse;
import cn.dlj1.cms.service.supports.FieldCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface TableService<T extends Entity> extends Service<T> {

    @Override
    Dao<T> getDao();

    default Result table(Query query) {
        query.initPager();

        queryParams(query);

        List<Map<String, Object>> data = query(query);

        queryData(query, data);

        Object others = getOthers(query);

        Result result = callback(query.getPager(), data, others);

        return result;
    }

    default void queryParams(Query query) {
    }

    default List<Map<String, Object>> query(Query query) {
        Pager pager = query.getPager();
        QueryWrapper<T> wrapper = new QueryWrapper<T>();

        // 字段验证
        FieldCheck.searchFieldCheck(getModuleClazz(), query.getFields());
        wrapper.select(query.getFields());

        QueryWrapperParse.parseCnd(wrapper, query.getCnds());
        QueryWrapperParse.parseSort(wrapper, query.getSort());

        IPage<Map<String, Object>> page = getDao().selectMapsPage(
                new Page<T>(pager.getNow(), pager.getPageSize()),
                wrapper);

        pager.setCount(page.getTotal());
        if (pager.getCount() != 0) {
            pager.setPages((int)
                    (pager.getCount() % pager.getPageSize() == 0 ?
                            (pager.getCount() / pager.getPageSize()) :
                            (pager.getCount() / pager.getPageSize() + 1))
            );
        }
        return page.getRecords();
    }

    default void queryData(Query query, List<Map<String, Object>> list) {
    }

    default Object getOthers(Query query) {
        return null;
    }

    default Result callback(Pager pager, List<Map<String, Object>> data, Object others) {
        Result result = new Result.Success(data);
        result.setPager(pager);
        result.setOthers(others);

        return result;
    }


}
