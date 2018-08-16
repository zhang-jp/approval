package com.tenkent.infrastructure.exception;

import com.tenkent.infrastructure.utility.StringUtility;

/**
 * Created by 6418000384 on 2016/7/15.
 */
public class BaseException extends Exception
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String errorCode = "";
    
    private String detailMessage = "";
    
    public String getDetailMessage()
    {
        return detailMessage;
    }
    
    public void setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public BaseException()
    {
        super();
    }
    
    public BaseException(String message)
    {
        super(message);
    }
    
    public BaseException(String messageTemplate, Object... params)
    {
        super(StringUtility.format(messageTemplate, params));
    }
    
    public BaseException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BaseException(Throwable cause, String messageTemplate, Object... params)
    {
        super(StringUtility.format(messageTemplate, params), cause);
        
    }
}
