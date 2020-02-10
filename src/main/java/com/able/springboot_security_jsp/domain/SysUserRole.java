package com.able.springboot_security_jsp.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "sys_user_role")
@Data
public class SysUserRole {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "UID")
    private Integer uid;
    @Column(name = "RID")
    private Integer rid;
}
