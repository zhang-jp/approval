package com.tenkent.infrastructure.exception;

import com.tenkent.infrastructure.utility.StringUtility;

/**
 * 基类异常
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年1月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseRuntimeException extends RuntimeException
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
    
    public BaseRuntimeException()
    {
        super();
    }
    
    public BaseRuntimeException(String message)
    {
        super(message);
    }
    
    public BaseRuntimeException(String errorCode, String detailMessage)
    {
        super(detailMessage);
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
    
    public BaseRuntimeException(String messageTemplate, Object... params)
    {
        super(StringUtility.format(messageTemplate, params));
    }
    
    public BaseRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BaseRuntimeException(Throwable cause)
    {
        super(cause);
    }
    
    public BaseRuntimeException(String errorCode, String detailMessage, Throwable cause)
    {
        super(detailMessage, cause);
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
    
    public BaseRuntimeException(Throwable cause, String messageTemplate, Object... params)
    {
        super(StringUtility.format(messageTemplate, params), cause);
    }
}
