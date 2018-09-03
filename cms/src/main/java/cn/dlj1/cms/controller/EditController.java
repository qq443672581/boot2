package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.ActionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 修改接口
 */
public interface EditController<T extends Entity> extends Controller<T> {


    @RequestMapping("/edit")
    @ResponseBody
    default Result edit(@Validated T entity) {
        return getActionService().edit(entity);
    }

}
