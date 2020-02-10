package com.able.springboot_security_jsp.service;

import com.able.springboot_security_jsp.domain.SysRole;
import com.able.springboot_security_jsp.domain.SysUser;
import com.able.springboot_security_jsp.domain.SysUserRole;
import com.able.springboot_security_jsp.mapper.RoleMapper;
import com.able.springboot_security_jsp.mapper.UserMapper;
import com.able.springboot_security_jsp.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserRoleMapper userRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        List<SysUser> sysUserList = userMapper.select(sysUser);
        if (CollectionUtils.isEmpty(sysUserList)) {
            return null;
        }
        final List<Integer> userIds = sysUserList.stream().map(SysUser::getId).collect(Collectors.toList());
        Example example=new Example(SysUserRole.class);
        example.createCriteria().andIn("uid",userIds);
        final List<SysUserRole> sysUserRoles = userRoleMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(sysUserRoles)) {
            final List<Integer> roleIds = sysUserRoles.stream().map(SysUserRole::getRid).collect(Collectors.toList());
            Example example1=new Example(SysRole.class);
            example1.createCriteria().andIn("id",roleIds);
            final List<SysRole> sysRoles = roleMapper.selectByExample(example1);

            sysUserList = sysUserList.stream().map(x -> {
                x.setRoles(sysRoles);
                return x;
            }).collect(Collectors.toList());
        }
        log.info("查询到的信息为:{}",sysUserList.get(0));
        return sysUserList.get(0);
    }
}
