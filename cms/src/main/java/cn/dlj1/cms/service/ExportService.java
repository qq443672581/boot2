package cn.dlj1.cms.service;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.ExportUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 */
public interface ExportService<T extends Entity> extends TableService<T> {

    static Log log = LogFactory.getLog(ExportService.class);

    /**
     * 获取类字段
     *
     * @return
     */
    default Result getExportInfos() {
        Class<T> clazz = getModuleClazz();
        Map map = ExportUtils.getExportFields(clazz);
        return new Result.Success(map);
    }

    /**
     * 导出
     *
     * @return
     */
    default byte[] export(Query query) {
        return new String("测试的").getBytes();
    }

}
