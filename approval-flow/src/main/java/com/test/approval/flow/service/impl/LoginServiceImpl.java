package com.test.approval.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenkent.infrastructure.auth.util.AuthUtil;
import com.tenkent.infrastructure.utility.ServiceCheckUtility;
import com.tenkent.infrastructure.utility.secure.Md5Utility;
import com.test.approval.flow.common.constant.ApprovalErrorsEnum;
import com.test.approval.flow.pojo.dto.LoginDto;
import com.test.approval.flow.pojo.dto.LoginUserDto;
import com.test.approval.flow.repository.entity.User;
import com.test.approval.flow.service.ILoginService;
import com.test.approval.flow.service.IUserService;

@Service
public class LoginServiceImpl implements ILoginService
{
    @Autowired
    private IUserService iUserService;
    
    /**
     * 用户登录
     */
    @Override
    public String login(LoginDto dto)
    {
        //校验账号是否为空
        ServiceCheckUtility.emptyCheck(dto.getUserAccount(), "账号");
        
        //校验账号是否为空
        ServiceCheckUtility.emptyCheck(dto.getUserPassword(), "密码");
        
        //查询用户信息
        User user = iUserService.getUserByUserAccount(dto.getUserAccount());
        
        //校验密码
        boolean check = checkUserPassword(user.getUserPassword(), dto.getUserPassword(), user.getSalt());
        ServiceCheckUtility.errorCheck(!check,
            ApprovalErrorsEnum.LOGIN_ACCOUNT_PASSWORD_ERROR.getInfo(),
            ApprovalErrorsEnum.LOGIN_ACCOUNT_PASSWORD_ERROR.getCode());
        
        //业务处理 登录
        LoginUserDto login = new LoginUserDto();
        login.setUser(user);
        return AuthUtil.login(login);
    }
    
    /**
     * 密码校验
     * @param dbPassword
     * @param userPasspord
     * @param dbSalt
     * @return
     */
    private boolean checkUserPassword(String dbPassword, String userPasspord, String dbSalt)
    {
        //校验密码
        String passwordMd5 = Md5Utility.getMD532Big(userPasspord, dbSalt);
        return passwordMd5.equalsIgnoreCase(dbPassword);
    }
}
