package com.tenkent.infrastructure.exception;

import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.utility.StringUtility;

/**
 * 控制层异常 
 * @author  qinzhengliang
 * @version  [版本号, 2017年3月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RestException extends BaseRuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public RestException(String message)
    {
        super(SysErrorsEnum.SYS_ERROE.getCode(), message);
    }
    
    public RestException(String errorCode, String message)
    {
        super(errorCode, message);
    }
    
    public RestException(String errorCode, String messageTemplate, Object... params)
    {
        super(errorCode, StringUtility.format(messageTemplate, params));
    }
    
    public RestException(Throwable cause, String errorCode, String message)
    {
        super(errorCode, message, cause);
    }
    
    public RestException(Throwable cause, String errorCode, String messageTemplate, Object... params)
    {
        super(errorCode, StringUtility.format(messageTemplate, params), cause);
    }
}
