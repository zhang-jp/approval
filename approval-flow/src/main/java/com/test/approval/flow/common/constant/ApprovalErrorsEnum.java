package com.test.approval.flow.common.constant;

public enum ApprovalErrorsEnum
{
    //登录模块信息
    LOGIN_TOKEN_ERROR("100001", "登录异常"),
    LOGIN_ACCOUNT_PASSWORD_ERROR("100002", "账号或者密码错误"),
    
    //用户模块
    USER_INFO_NULL_ERROR("200001", "用户不存在");
    
    private String code;
    
    private String info;
    
    private ApprovalErrorsEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }
    
    public static ApprovalErrorsEnum errorOf(String code)
    {
        for (ApprovalErrorsEnum error : values())
        {
            if (error.getCode().equals(code))
            {
                return error;
            }
        }
        return null;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public String getInfo()
    {
        return info;
    }
    
    public String errToString()
    {
        return code + ":" + info;
    }
}
