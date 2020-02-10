package com.able.springboot_security_jsp.config;

import com.able.springboot_security_jsp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
/**
 * prePostEnabled spring表达式的方式
 * securedEnabled springSecurity的方式
 * jsr250Enable jsr250的方式
 */
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //认证用户的来源
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//      auth.inMemoryAuthentication()
//              .withUser("user")
//              .password("{noop}123")
//              .roles("USER");
        //使用数据库的数据来实现认证操作
        auth.userDetailsService(userService).
                passwordEncoder(bCryptPasswordEncoder());
    }

    //配置SpringSecurity相关信息
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        //释放静态资源 指定资源拦截规则 指定自定义认证页面  指定退出认证规则  csrf配置
        final ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();
        //释放静态资源

        expressionInterceptUrlRegistry.antMatchers("/login.jsp", "failer.jsp",  "/css/**", "/img/**", "/plugins/**").permitAll();
        //指定资源拦截规则
        expressionInterceptUrlRegistry.antMatchers("/**").hasAnyRole("USER","ADMIN");
        expressionInterceptUrlRegistry.antMatchers("/product").hasAnyRole("USER");
        expressionInterceptUrlRegistry.anyRequest().authenticated();
        //指定自定义认证页面
        expressionInterceptUrlRegistry.and().formLogin()
                .loginPage("/login.jsp").loginProcessingUrl("/login").successForwardUrl("/index.jsp")
                .failureForwardUrl("/failer.jsp").permitAll();
        //指定认证退出
        expressionInterceptUrlRegistry.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp")
                .invalidateHttpSession(true).permitAll();
        //取消CSRF过滤器
        expressionInterceptUrlRegistry.and().csrf().disable();

//        http.authorizeRequests()
//                .antMatchers("/login.jsp", "failer.jsp", "/css/**", "/img/**", "/plugins/**").permitAll()
//                .antMatchers("/product").hasAnyRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login.jsp")
//                .loginProcessingUrl("/login")
//                .successForwardUrl("/index.jsp")
//                .failureForwardUrl("/failer.jsp")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/logout")
//                .invalidateHttpSession(true)
//                .logoutSuccessUrl("/login.jsp")
//                .and()
//                .csrf()
//                .disable();


    }

}
