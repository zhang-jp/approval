package com.tenkent.infrastructure.auth.jwt.core;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.FactoryBean;

import com.tenkent.infrastructure.auth.core.Section;
import com.tenkent.infrastructure.auth.jwt.api.IJwtProvider;

/**
 * spring mvc 使用
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月19日]
 */
public class JwtAuthFilterFactoryBean implements FactoryBean<Filter>
{
    private IJwtProvider jwtProvider;
    
    private List<Section> sectionList;
    
    private Filter instance;
    
    public JwtAuthFilterFactoryBean()
    {
    }
    
    public List<Section> getSectionList()
    {
        return sectionList;
    }
    
    public void setSectionList(List<Section> sectionList)
    {
        this.sectionList = sectionList;
    }
    
    public boolean isSingleton()
    {
        return true;
    }
    
    @Override
    public Filter getObject()
        throws Exception
    {
        if (instance == null)
        {
            instance = createInstance();
        }
        return instance;
    }
    
    protected Filter createInstance()
    {
        return new JwtAuthFilter(jwtProvider, sectionList);
    }
    
    @Override
    public Class<?> getObjectType()
    {
        return JwtAuthFilter.class;
    }
    
    public IJwtProvider getJwtProvider()
    {
        return jwtProvider;
    }
    
    public void setJwtProvider(IJwtProvider jwtProvider)
    {
        this.jwtProvider = jwtProvider;
    }
}
