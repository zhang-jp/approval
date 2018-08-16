package com.tenkent.infrastructure.auth.core;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.FactoryBean;

/**
 * spring mvc 使用
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月19日]
 */
public class AuthFilterFactoryBean implements FactoryBean<Filter>
{
    private AuthSessionManage sessionManage;
    
    /**
     * 路径匹配
     */
    private List<Section> sectionList;
    
    /**
     * 权限检查标识
     */
    private boolean permissionCheckFlg = false;
    
    private Filter instance;
    
    public AuthFilterFactoryBean()
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
        return new AuthFilter(sessionManage, sectionList, permissionCheckFlg);
    }
    
    @Override
    public Class<?> getObjectType()
    {
        return AuthFilter.class;
    }
    
    public AuthSessionManage getSessionManage()
    {
        return sessionManage;
    }
    
    public void setSessionManage(AuthSessionManage sessionManage)
    {
        this.sessionManage = sessionManage;
    }
    
    public boolean isPermissionCheckFlg()
    {
        return permissionCheckFlg;
    }
    
    public void setPermissionCheckFlg(boolean permissionCheckFlg)
    {
        this.permissionCheckFlg = permissionCheckFlg;
    }
}
