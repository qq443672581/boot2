package cn.dlj1.cms.web.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.ExportService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.web.dao.TestDao;
import cn.dlj1.cms.web.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService implements
        TableService<Test>,
        ActionService<Test>,
        ExportService<Test> {

    @Autowired
    private TestDao dao;

    @Override
    public Dao getDao() {
        return dao;
    }

    @Override
    public Object getOthers(Query query) {
        return dao.selectIds();
    }
}
