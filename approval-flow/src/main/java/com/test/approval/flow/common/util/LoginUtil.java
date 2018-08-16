package com.test.approval.flow.common.util;

import com.tenkent.infrastructure.auth.util.AuthUtil;
import com.test.approval.flow.pojo.dto.LoginUserDto;
import com.test.approval.flow.repository.entity.User;

/**
 * 通用帮助类
 * @author  zhangjiaping
 * @version  [版本号, 2018年7月12日]
 */
public class LoginUtil
{
    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId()
    {
        return getUser().getId();
    }
    
    /**
     * 获取用户信息
     * @return
     */
    public static User getUser()
    {
        return getLoginUserDto().getUser();
    }
    
    public static LoginUserDto getLoginUserDto()
    {
        return (LoginUserDto)AuthUtil.getLoginUser();
    }
}
