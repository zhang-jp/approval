package com.test.approval.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenkent.infrastructure.constant.GlobalConstant;
import com.tenkent.infrastructure.utility.ServiceCheckUtility;
import com.test.approval.flow.common.constant.ApprovalErrorsEnum;
import com.test.approval.flow.repository.dao.UserMapper;
import com.test.approval.flow.repository.entity.User;
import com.test.approval.flow.service.IUserService;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 根据用户id查询用户信息
     */
    @Override
    public User getUserById(Long userId)
    {
        ServiceCheckUtility.emptyCheck(userId, "用户ID");
        User user = new User();
        user.setEnabledFlag(GlobalConstant.ENABLED_FLAG);
        user.setUserStatus(GlobalConstant.ACTIVE);
        user.setId(userId);
        user = userMapper.selectOne(user);
        ServiceCheckUtility.errorCheck(user == null,
            ApprovalErrorsEnum.USER_INFO_NULL_ERROR.getInfo(),
            ApprovalErrorsEnum.USER_INFO_NULL_ERROR.getCode());
        return user;
    }
    
    /**
     * 根据账号查询用户信息
     */
    @Override
    public User getUserByUserAccount(String userAccount)
    {
        ServiceCheckUtility.emptyCheck(userAccount, "用户账号");
        User user = new User();
        user.setEnabledFlag(GlobalConstant.ENABLED_FLAG);
        user.setUserStatus(GlobalConstant.ACTIVE);
        user.setUserAccount(userAccount);
        user = userMapper.selectOne(user);
        ServiceCheckUtility.errorCheck(user == null,
            ApprovalErrorsEnum.USER_INFO_NULL_ERROR.getInfo(),
            ApprovalErrorsEnum.USER_INFO_NULL_ERROR.getCode());
        return user;
    }
    
}
