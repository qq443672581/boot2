package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.TreeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 树接口
 */
public interface TreeController<T extends Entity> extends Controller<T> {

    @RequestMapping("/tree")
    @ResponseBody
    default Result tree(@Validated Query query) {
        return getTreeService().tree(query);
    }

    default TreeService getTreeService() {
        return (TreeService) getService();
    }

}
