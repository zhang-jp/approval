package com.tenkent.infrastructure.exception;

import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.utility.StringUtility;

/**
 * 持久层异常
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年3月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RepositoryException extends BaseRuntimeException
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public RepositoryException(String message)
    {
        super(SysErrorsEnum.SYS_DATABASE_ERROE.getCode(), message);
    }
    
    public RepositoryException(String messageTemplate, Object... params)
    {
        super(SysErrorsEnum.SYS_DATABASE_ERROE.getCode(), StringUtility.format(messageTemplate, params));
    }
    
    public RepositoryException(Throwable cause, String message)
    {
        super(SysErrorsEnum.SYS_DATABASE_ERROE.getCode(), message, cause);
    }
    
    public RepositoryException(Throwable cause, String messageTemplate, Object... params)
    {
        super(SysErrorsEnum.SYS_DATABASE_ERROE.getCode(), StringUtility.format(messageTemplate, params), cause);
    }
}
