package cn.dlj1.cms.controller;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.TableService;
import org.springframework.web.servlet.ModelAndView;

/**
 * 数据表格接口
 *
 * @param <K>
 * @param <T>
 */
public interface TableController<K extends Key, T extends Entity> extends Controller<K, T> {

    TableService getTabeleService();

    Result table(Query query);

    ModelAndView exportPage(Query query);

    String export(Query query);

}
