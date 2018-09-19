package com.tenkent.infrastructure.constant;

/**
 * 系统错误码信息
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public enum SysErrorsEnum {
    //系统异常
    SYS_ERROE("10000001", "系统异常"),
    
    //参数错误
    SYS_PARAM_ERROE("10000002", "参数错误"),
    
    //没有数据
    SYS_NODATA_ERROE("10000003", "没有数据"),
    
    //授权错误
    SYS_AUTH_ERROR("10000004", "授权错误"),
    
    // 白名单错误
    SYS_WHITELIST_ERROR("10000005", "不在白名单内"),
    
    //签名错误
    SYS_SIGN_ERROE("10000006", "消息错误"),
    
    //网络异常
    SYS_NETWORK_ERROR("10000007", "网络异常"),
    
    //操作异常
    SYS_OPERATE_ERROE("10000008", "操作异常"),
    
    //数据库操作异常
    SYS_DATABASE_ERROE("10000009", "数据库操作异常"),
    
    //基础框架异常
    SYS_INFRASTRUCTURE_ERROE("10000010", "基础框架异常"),
    
    // 接口没有授权
    SYS_NO_INTERFACE_AUTH_ERROR("10000011", "没有操作权限"),
    
    // ---- 数据校验通用错误码 ----
    // 数据已停用
    SYS_DATA_DISABLED_ERROR("10000011", "数据已停用"),
    
    // 数据已存在
    SYS_DATA_EXIST_ERROR("10000012", "数据已存在"),
    
    // 数据不存在
    SYS_DATA_NOT_EXIST_ERROR("10000013", "数据不存在"),
    
    // 系统登录验证异常
    SYS_AUTH_LOGIN_ERROR("11000001", "系统登录验证异常"),
    
    //操作成功
    SYS_SUCCESS("10000000", "成功");
    
    private String code;
    
    private String info;
    
    private SysErrorsEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getInfo() {
        return info;
    }
    
    public String errToString() {
        return code + ":" + info;
    }
}
