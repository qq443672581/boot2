package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.FieldUtils;
import cn.dlj1.cms.service.supports.QueryWrapperParse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface TableService<T extends Entity> extends Service<T> {

    @Override
    Dao<T> getDao();

    default Result table(Query<T> query) {
        // 初始化分页
        query.initPager();

        // 参数钩子
        tableQueryParams(query);

        // 参数验证
        tableValidate(query);

        // 数据最后的查询
        List<Map<String, Object>> list = tableQuery(query);

        // 其他要返回的数据
        Object others = tableOthers(query);

        // 最后的回调钩子
        Result result = tableCallback(query, list, others);

        return result;
    }

    // 对请求数据进行修改编辑
    default void tableQueryParams(Query<T> query) {
    }

    default void tableValidate(Query<T> query) {
        // 字段验证
        FieldUtils.searchFieldCheck(getModuleClazz(), query.getFields());
    }

    default List<Map<String, Object>> tableQuery(Query<T> query) {
        // 可以转成一个map 进行处理多表VO处理： CndUtils.toObjMap(query.getCnds());

        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        wrapper.select(query.getFields());

        QueryWrapperParse.parseCnd(wrapper, query.getCnds());
        QueryWrapperParse.parseSort(wrapper, query.getSort());

        IPage<Map<String, Object>> iPage =
                getDao().selectMapsPage(query.getPager(), wrapper);

        query.getPager().setTotal(iPage.getTotal());
        query.getPager().initPages();

        return iPage.getRecords();
    }

    default Object tableOthers(Query<T> query) {
        return null;
    }

    default Result tableCallback(Query<T> query, List<Map<String, Object>> list, Object others) {
        Result result = new Result.Success(list);
        result.setPager(query.getPager());
        result.setOthers(others);

        return result;
    }


}