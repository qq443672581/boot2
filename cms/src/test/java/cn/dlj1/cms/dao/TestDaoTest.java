package cn.dlj1.cms.dao;

import cn.dlj1.cms.web.dao.TestDao;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDaoTest {

    @Autowired
    private TestDao dao;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        Wrapper<cn.dlj1.cms.web.entity.Test> q = new QueryWrapper<cn.dlj1.cms.web.entity.Test>().eq("id", 1);

        List<cn.dlj1.cms.web.entity.Test> userList = dao.selectList(q);
        userList.forEach(System.out::println);
    }

}
