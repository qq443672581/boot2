package cn.dlj1.cms.service;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.request.query.ExportQuery;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.ExportUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;
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
    default byte[] export(ExportQuery query) {
        Result result = table(query);
        List<Map<String, Object>> data = null;
        if (result.isSuccess()) {
            data = (List<Map<String, Object>>) result.getData();
        }

        Map<String, String> map = null;
        try {
            map = JSON.parseObject(query.getExportFields(), Map.class);

        } catch (Exception e) {
            throw new MessageException("导出字段格式不正确!");
        }

        try {
            return ExportUtils.export("1", "用户", "备注", map, data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessageException("导出失败!");
        }

    }

}
