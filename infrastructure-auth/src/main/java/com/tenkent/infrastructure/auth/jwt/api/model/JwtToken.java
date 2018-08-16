package com.tenkent.infrastructure.auth.jwt.api.model;

public class JwtToken
{
    String token;
    
    String refreshDate;
    
    public JwtToken()
    {
    }
    
    public JwtToken(String token, String refreshDate)
    {
        this.token = token;
        this.refreshDate = refreshDate;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getRefreshDate()
    {
        return refreshDate;
    }
    
    public void setRefreshDate(String refreshDate)
    {
        this.refreshDate = refreshDate;
    }
}
