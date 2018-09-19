package com.tenkent.infrastructure.utility;

import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.exception.ServiceException;
import com.tenkent.infrastructure.log.LoggerManager;

/**
 * 服务参数检查工具
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class ServiceCheckUtility {
    private ServiceCheckUtility() {
    }
    
    /**
     * 错误检查
     * @param result
     * @param dec
     * @param code
     * @param msg
     */
    public static void errorCheck(boolean result, String dec, String code, String msg) {
        LoggerManager.error(ServiceCheckUtility.class, null, msg);
        errorCheck(result, dec, code);
    }
    
    /**
     * 错误检查
     * @param result
     * @param code
     * @param dec
     */
    public static void errorCheck(boolean result, String dec, String code) {
        if (result) {
            throw new ServiceException(code, dec);
        }
    }
    
    /**
     * 错误检查 默认参数检查错误
     * @param result
     * @param dec
     */
    public static void errorCheck(boolean result, String dec) {
        errorCheck(result, dec, SysErrorsEnum.SYS_PARAM_ERROE.getCode());
    }
    
    /**
     * 参数空检查
     * @param o
     * @param fieldName
     * @param code
     */
    public static void emptyCheck(Object o, String fieldName, String code) {
        if (ObjectUtils.isEmpty(o)) {
            throw new ServiceException(code, fieldName + "为空");
        }
    }
    
    /**
     * 非空检查 默认参数检查错误
     * @param o
     * @param fieldName
     */
    public static void emptyCheck(Object o, String fieldName) {
        emptyCheck(o, fieldName, SysErrorsEnum.SYS_PARAM_ERROE.getCode());
    }
    
    /**
     * 查询结果为空检查
     * @param o
     * @param fieldName
     */
    public static void noDataCheck(Object o, String fieldName) {
        errorCheck(ObjectUtils.isEmpty(o), fieldName + "没有数据", SysErrorsEnum.SYS_DATA_NOT_EXIST_ERROR.getCode());
    }
    
    /**
     * 字符类型参数如果非空，进行长度校验
     * @param param 字符类型参数
     * @param fieldName 字段名
     */
    public static void lengthCheck(String param, int maxLength, String fieldName) {
        if (StringUtility.isNotBlank(param) && param.trim().length() > maxLength) {
            throw new ServiceException(SysErrorsEnum.SYS_PARAM_ERROE.getCode(), fieldName + "超过最大长度" + maxLength);
        }
    }
    
    /**
     * 字符类型参数非空校验和长度校验
     * @param param 字符类型参数
     * @param fieldName 字段名
     */
    public static void emptyCheck(String param, int maxLength, String fieldName) {
        emptyCheck(param, fieldName, SysErrorsEnum.SYS_PARAM_ERROE.getCode());
        
        if (param.trim().length() > maxLength) {
            throw new ServiceException(SysErrorsEnum.SYS_PARAM_ERROE.getCode(), fieldName + "超过最大长度" + maxLength);
        }
    }
}
