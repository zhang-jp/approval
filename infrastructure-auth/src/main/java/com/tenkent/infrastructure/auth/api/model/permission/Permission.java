package com.tenkent.infrastructure.auth.api.model.permission;

/**
 * 权限
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月9日]
 */
public class Permission
{
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限名称
     */
    private String code;
    
    /**
     * 权限URL前缀
     */
    private String prefixUrl;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getPrefixUrl()
    {
        return prefixUrl;
    }
    
    public void setPrefixUrl(String prefixUrl)
    {
        this.prefixUrl = prefixUrl;
    }
}
