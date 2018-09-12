package cn.dlj1.cms.web.entity;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
import java.sql.Time;

/**
 * 用户表
 *
 */
@TableName("user")
public class User extends LongEntity {

    @Cloumn("姓名")
    private String name;

    @Cloumn("年龄")
    private Integer age;

    @Cloumn(value = "生日", search = true)
    private Date birthday;

    @Cloumn(value = "时间")
    @TableField("myTime")
    private Time myTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Time getMyTime() {
        return myTime;
    }

    public void setMyTime(Time myTime) {
        this.myTime = myTime;
    }
}

