package cn.dlj1.cms.web.entity;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("test")
public class Test extends LongEntity {

    @Cloumn("姓名")
    private String name;

    @Cloumn("年龄")
    private int age;

    @Cloumn(value = "生日", search = true)
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}

