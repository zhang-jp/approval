package com.tenkent.infrastructure.base;

/**
 * 返回controller数据封装
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class BaseDto<T> {
    /**
     * 调用是否成功
     */
    private boolean success;
    
    /**
     * 成功时返回的数据
     */
    private T data;
    
    /**
     * 响应码
     */
    private String returnCode;
    
    /**
     * 响应描述
     */
    private String returnMsg;
    
    public BaseDto(boolean success, T data, String returnCode, String returnMsg) {
        this.success = success;
        this.data = data;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }
    
    public BaseDto(boolean success, String returnCode, String returnMsg) {
        this.success = success;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getReturnCode() {
        return returnCode;
    }
    
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    
    public String getReturnMsg() {
        return returnMsg;
    }
    
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}