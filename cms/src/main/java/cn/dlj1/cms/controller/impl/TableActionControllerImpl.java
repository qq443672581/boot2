package cn.dlj1.cms.controller.impl;

import cn.dlj1.cms.controller.ActionController;
import cn.dlj1.cms.controller.TableController;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.TableService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @param <K>
 * @param <T>
 */
public abstract class TableActionControllerImpl<K extends Key, T extends Entity>
        extends TableControllerImpl<K, T> implements TableController<K, T>, ActionController<K, T> {

    private static Log log = LogFactory.getLog(TableActionControllerImpl.class);

    @Override
    public abstract TableService getTabeleService();


    @ResponseBody
    @RequestMapping(value = {"/table"}, method = {RequestMethod.POST, RequestMethod.GET})
    @Override
    public Result table(Query query) {

        log.info(JSON.toJSONString(query));

        return getTabeleService().table(query);
    }

    @Override
    public ModelAndView exportPage(Query query) {
        return null;
    }

    @Override
    public String export(Query query) {
        return null;
    }

    @Override
    public abstract String getModulePath();

}
