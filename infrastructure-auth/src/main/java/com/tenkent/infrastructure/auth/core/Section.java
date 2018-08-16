package com.tenkent.infrastructure.auth.core;

/**
 * 路径认证片段
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月19日]
 */
public class Section
{
    /**
     * 匹配的路径
     */
    private String matchedPath;
    
    /**
     * 认证标识
     */
    private String authFlg;
    
    public Section()
    {
    }
    
    public Section(String matchedPath, String authFlg)
    {
        this.matchedPath = matchedPath;
        this.authFlg = authFlg;
    }
    
    public String getMatchedPath()
    {
        return matchedPath;
    }
    
    public void setMatchedPath(String matchedPath)
    {
        this.matchedPath = matchedPath;
    }
    
    public String getAuthFlg()
    {
        return authFlg;
    }
    
    public void setAuthFlg(String authFlg)
    {
        this.authFlg = authFlg;
    }
    
    public static Section build(String matchedPath, String authFlg)
    {
        return new Section(matchedPath, authFlg);
    }
}
