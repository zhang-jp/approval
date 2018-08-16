/*
 * 文 件 名:  RspDataUtil.java
 * 版    权:  Tenkent Technologies Co., Ltd. Copyright 2005-2016,  All rights reserved
 * 描    述:  <构造响应的工具类>
 * 修 改 人:  shiyajie
 * 修改时间:  2017年3月14日
 */
package com.tenkent.infrastructure.exception.util;

import com.tenkent.infrastructure.base.BaseDto;
import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.exception.BaseRuntimeException;

/**
 * 构造响应的工具类
 * 
 * @author  shiyajie
 * @version  [版本号, 2017年3月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RspUtil
{
    /**
     * 构造成功的响应对象
     * 
     * @param data 返回数据
     * @return 成功的响应对象
     */
    public static <T> BaseDto<T> buildSuccessRsp(T data)
    {
        return new BaseDto<T>(true, data, SysErrorsEnum.SYS_SUCCESS.getCode(), SysErrorsEnum.SYS_SUCCESS.getInfo());
    }
    
    /**
     * 构造失败的响应对象
     * 
     * @param data 返回数据
     * @return 成功的响应对象
     */
    public static <T> BaseDto<T> buildErrorRsp()
    {
        return new BaseDto<T>(false, SysErrorsEnum.SYS_ERROE.getCode(), SysErrorsEnum.SYS_ERROE.getInfo());
    }
    
    /**
     * 构造失败的响应对象
     * 
     * @param data 返回数据
     * @return 成功的响应对象
     */
    public static <T> BaseDto<T> buildErrorRsp(SysErrorsEnum enumError)
    {
        return new BaseDto<T>(false, enumError.getCode(), enumError.getInfo());
    }
    
    /**
     * 构造失败的响应对象
     * 
     * @param data 返回数据
     * @return 成功的响应对象
     */
    public static <T> BaseDto<T> buildNoDataErrorRsp()
    {
        return new BaseDto<T>(false, SysErrorsEnum.SYS_NODATA_ERROE.getCode(),
            SysErrorsEnum.SYS_NODATA_ERROE.getInfo());
    }
    
    /**
     * 构造失败的响应对象
     * @param code
     * @param error
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> BaseDto<T> buildErrorRsp(String code, String error)
    {
        return new BaseDto<T>(false, code, error);
    }
    
    /**
     * 构造失败的响应对象
     * @param e
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> BaseDto<T> buildErrorRsp(BaseRuntimeException e)
    {
        return new BaseDto<T>(false, e.getErrorCode(), e.getDetailMessage());
    }
}
