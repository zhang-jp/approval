/*package com.tenkent.infrastructure.auth.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tenkent.infrastructure.auth.core.AuthFilter;
import com.tenkent.infrastructure.auth.core.AuthSessionManage;
import com.tenkent.infrastructure.auth.core.RedisSessionCache;
import com.tenkent.infrastructure.auth.core.Section;

import redis.clients.jedis.JedisPool;

*//**
 * 权限认证
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年12月13日]
 *//*
@Configuration
public class BootAuthConfiguration
{
    @Autowired
    private JedisPool jedisPool;
    
    *//**
     * 配置过滤器
     * @return
     *//*
    @Bean
    public FilterRegistrationBean someFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/*");
        registration.setName("authFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
    
    *//**
     * 创建authFilter
     * @return
     *//*
    @Bean(name = "authFilter")
    public Filter authFilter()
    {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section("/sysLogin/login", "anon"));
        sectionList.add(new Section("/file/upload", "anon"));
        sectionList.add(new Section("/swagger-ui.html", "anon"));
        sectionList.add(new Section("/swagger-resources/**", "anon"));
        sectionList.add(new Section("/v2/api-docs**", "anon"));
        sectionList.add(new Section("/webjars/springfox-swagger-ui/**", "anon"));
        sectionList.add(new Section("/**", "authc"));
        
        RedisSessionCache sessionCache = new RedisSessionCache(jedisPool, "cvs:user");
        AuthSessionManage sessionManage = new AuthSessionManage(sessionCache, 1800);
        return new AuthFilter(sessionManage, sectionList, false);
    }
}*/