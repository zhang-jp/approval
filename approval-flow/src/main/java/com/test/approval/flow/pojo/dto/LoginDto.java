package com.test.approval.flow.pojo.dto;

public class LoginDto
{
    private String userAccount;
    
    private String userPassword;
    
    public String getUserAccount()
    {
        return userAccount;
    }
    
    public void setUserAccount(String userAccount)
    {
        this.userAccount = userAccount;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }
    
    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
}
