package cn.dlj1.cms.service;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.utils.JSONUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 */
public interface ExportService extends Service {

    static Log log = LogFactory.getLog(ExportService.class);

    /**
     * 获取类字段
     *
     * @return
     */
    default Result getExportInfos() {
        Entity entity = getDao().selectOneById("test", 1);

        List<Entity> entitys = getDao().selectAll("test");

        log.info(JSONUtils.toJSONString(entity));

        return new Result.Success(entitys);
//        return new Result.Success(ClassUtils.getStringFields(getModuleClazz()));
    }

}
