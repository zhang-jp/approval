package com.tenkent.infrastructure.exception;

import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.utility.StringUtility;

/**
 * 框架层异常
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class InfrastructureException extends BaseRuntimeException {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public InfrastructureException(String message) {
        super(SysErrorsEnum.SYS_INFRASTRUCTURE_ERROE.getCode(), message);
    }
    
    public InfrastructureException(String code, String message) {
        super(code, message);
    }
    
    public InfrastructureException(String messageTemplate, Object... params) {
        super(SysErrorsEnum.SYS_INFRASTRUCTURE_ERROE.getCode(), StringUtility.format(messageTemplate, params));
    }
    
    public InfrastructureException(Throwable cause, String message) {
        super(SysErrorsEnum.SYS_INFRASTRUCTURE_ERROE.getCode(), message, cause);
    }
    
    public InfrastructureException(Throwable cause, String messageTemplate, Object... params) {
        super(SysErrorsEnum.SYS_INFRASTRUCTURE_ERROE.getCode(), StringUtility.format(messageTemplate, params), cause);
    }
}
