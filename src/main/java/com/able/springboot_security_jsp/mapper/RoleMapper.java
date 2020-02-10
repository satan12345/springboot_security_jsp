package com.able.springboot_security_jsp.mapper;

import com.able.springboot_security_jsp.config.MyMapper;
import com.able.springboot_security_jsp.domain.SysRole;
import com.able.springboot_security_jsp.domain.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends MyMapper<SysRole> {

    @Select("SELECT r.id, r.role_name roleName, r.role_desc roleDesc " +
            "FROM sys_role r, sys_user_role ur " +
            "WHERE r.id=ur.rid AND ur.uid=#{uid}")
    List<SysRole> findByUid(Integer uid);
}
