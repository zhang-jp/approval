package com.test.approval.flow.service;

import com.test.approval.flow.pojo.dto.LoginDto;

/**
 * 登录 服务
 * @author  zhangjiaping
 * @version  [版本号, 2018年8月16日]
 */
public interface ILoginService
{
    /**
     * 用户登陆
     * @param dto
     * @return
     */
    String login(LoginDto dto);
}
