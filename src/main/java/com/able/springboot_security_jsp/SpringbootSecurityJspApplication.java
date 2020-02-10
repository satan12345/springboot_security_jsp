package com.able.springboot_security_jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.able.springboot_security_jsp.mapper")
public class SpringbootSecurityJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJspApplication.class, args);
	}

}
