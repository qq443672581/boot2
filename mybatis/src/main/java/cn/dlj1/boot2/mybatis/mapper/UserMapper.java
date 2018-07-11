package cn.dlj1.boot2.mybatis.mapper;

import cn.dlj1.boot2.mybatis.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("<script>select * from user where id=#{id} </script>")
    User findOne(int id);

    @Insert("<script> insert into user(name,age) value(#{name},#{age})</script>")
    int insert(User user);

}
