package com.test.approval.flow.service;

import com.test.approval.flow.repository.entity.User;

/**
 * 用户 服务
 * @author  zhangjiaping
 * @version  [版本号, 2018年8月16日]
 */
public interface IUserService
{
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    User getUserById(Long userId);
    
    /**
     * 根据账号查询用户信息
     * @param userAccount
     * @return
     */
    User getUserByUserAccount(String userAccount);
}
