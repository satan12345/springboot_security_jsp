package com.able.springboot_security_jsp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {

    @RequestMapping("findAll")
    @Secured("ROLE_PRODUCT")
    public String findAll(){

        return "product-list";
    }
}
