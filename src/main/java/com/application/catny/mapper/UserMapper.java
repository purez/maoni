package com.application.catny.mapper;

import com.application.catny.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username} and password= #{password}")
    public User checkUsers(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username=#{username}")
    public User getUserFromUsername(@Param("username") String username);

    @Select("select * from user where logined=#{logined}")
    public User getUsersFromLogined(@Param("logined") String logined);

    @Select("select * from user where account=#{account}")
    public User getUserFromAccount(@Param("account") String account);

    @Select("select * from user")
    public List<User> selectUsers();

    @Options(useGeneratedKeys = true, keyProperty = "pid")
    @Insert("insert into user(pid,account,password) values(#{pid},#{account},#{password})")
    public boolean insert(User user);

    @Delete("delete from user where username = #{username}")
    public void delete(String username);

    @Update("update user set password = #{password} where username = #{username}")
    public void updataPassword(@Param("password") String password, @Param("username") String username);

    @Update("update user set logined = #{logined} where username = #{username}")
    public void updataLoginedByUsername(@Param("username") String username, @Param("logined") String logined);

}
