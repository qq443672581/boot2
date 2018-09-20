package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.web.auth.annotation.Menu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据表格接口
 */
public interface TableController<T extends Entity> extends Controller<T> {

    @GetMapping("/table")
    @ResponseBody
    @Menu(value = "数据表格")
    default Result table(@Validated Query<T> query) {
        return getTableService().table(query);
    }

    @GetMapping("/actions")
    @ResponseBody
    @Menu(value = "页面操作项")
    default Result actions(HttpServletRequest request) {
        return Result.SUCCESS;
    }


    default TableService getTableService() {
        return (TableService) getService();
    }

}
