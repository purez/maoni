package com.application.catny.mapper;

import com.application.catny.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    User login(@Param("username") String username,@Param("password") String password);
}
