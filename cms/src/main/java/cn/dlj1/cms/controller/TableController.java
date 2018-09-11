package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.TableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据表格接口
 *
 *
 */
public interface TableController<T extends Entity> extends Controller<T> {

    @GetMapping("/table")
    @ResponseBody
    default Result table(@Validated Query query) {
        return getTableService().table(query);
    }

    default TableService getTableService() {
        return (TableService) getService();
    }

}
