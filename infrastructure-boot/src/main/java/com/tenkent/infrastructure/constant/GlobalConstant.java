package com.tenkent.infrastructure.constant;

/**
 * 全局静态常量
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class GlobalConstant {
    public static final byte ENABLED_FLAG = 1;
    
    public static final byte DISABLED_FLAG = 0;
    
    /**
     * 状态：启用
     */
    public static final byte ACTIVE = 1;
    
    /**
     * 状态：停用
     */
    public static final byte INACTIVE = 0;
    
    public static final String PAGE_NUM = "pageNum";
    
    public static final String PAGE_SIZE = "pageSize";
    
    public static final String SUCCESS = "success";
    
    public static final String FAIL = "fail";
    
    public static final String ERROR = "error";
    
    public static final String STRING_EMPTY = "";
    
    public static final boolean FALSE = false;
    
    public static final boolean TRUE = true;
    
    public static final String CHARSET_UTF8 = "utf-8";
    
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    public static final String GLOBAL_EXCEPTION = "系统异常";
    
    public static final String GLOBAL_EXCEPTION_LOGGER = "异常信息:{}，调用参数:{}";
}
