package cn.dlj1.cms.controller;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.Component.Table;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据表格接口
 *
 * @param <K>
 * @param <T>
 */
public interface TableController<K extends Key, T extends Entity> extends Controller<K, T> {



    @RequestMapping("/table")
    @ResponseBody
    default Result table(Query query) {
        return getTable().table(null,null);
    }

    default Table getTable(){
        return (Table)getC();
    }

}
