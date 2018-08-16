package com.test.approval.flow.configurer;

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

/**
 * 权限认证
 * @author  zhangjiaping
 * @version  [版本号, 2018年8月16日]
 */
@Configuration
public class AuthFilterConfiguration
{
    @Autowired
    private JedisPool jedisPool;
    
    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("authFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
    
    /**
     * 创建authFilter
     * @return
     */
    @Bean(name = "authFilter")
    public Filter authFilter()
    {
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(Section.build("/login/logon", "anon"));
        sectionList.add(Section.build("/swagger-ui.html", "anon"));
        sectionList.add(Section.build("/swagger-resources/**", "anon"));
        sectionList.add(Section.build("/v2/api-docs**", "anon"));
        sectionList.add(Section.build("/webjars/springfox-swagger-ui/**", "anon"));
        sectionList.add(Section.build("/function/getUserUsableFuncs", "anon"));
        sectionList.add(Section.build("/**", "authc"));
        
        RedisSessionCache sessionCache = new RedisSessionCache(jedisPool, "approval:user");
        AuthSessionManage sessionManage = new AuthSessionManage(sessionCache, 1800);
        return new AuthFilter(sessionManage, sectionList);
    }
}