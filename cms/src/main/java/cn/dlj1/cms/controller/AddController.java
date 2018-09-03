package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.ActionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 添加接口
 */
public interface AddController<T extends Entity> extends Controller<T> {


    @RequestMapping("/add")
    @ResponseBody
    default Result add(@Validated T entity) {
        return getActionService().add(entity);
    }

}
