package com.able.springboot_security_jsp.mapper;

import com.able.springboot_security_jsp.config.MyMapper;
import com.able.springboot_security_jsp.domain.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends MyMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roles", column = "id", javaType = List.class,
                    many = @Many(select = "com.itheima.mapper.RoleMapper.findByUid"))
    })
     SysUser findByName(String username);
}
