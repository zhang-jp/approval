package com.test.approval.flow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenkent.infrastructure.auth.util.AuthUtil;
import com.tenkent.infrastructure.base.BaseDto;
import com.tenkent.infrastructure.exception.util.RspUtil;
import com.tenkent.infrastructure.log.LoggerManager;
import com.test.approval.flow.pojo.dto.LoginDto;
import com.test.approval.flow.service.ILoginService;

import io.swagger.annotations.ApiOperation;

/**
 * 用户登陆
 * @author  zhangjiaping
 * @version  [版本号, 2018年7月5日]
 */
@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    private ILoginService iLoginService;
    
    /**
     * 用户登陆
     * @param dto
     * @return
     */
    @RequestMapping(value = "/logon")
    @ApiOperation(value = "用户登录", httpMethod = "POST")
    public BaseDto<String> login(@RequestBody LoginDto dto)
    {
        return RspUtil.buildSuccessRsp(iLoginService.login(dto));
    }
    
    /**
     * 用户登录校验
     * @return
     */
    @RequestMapping(value = "/loginCheck")
    @ApiOperation(value = "用户登录校验", httpMethod = "GET")
    public BaseDto<String> loginCheck()
    {
        return RspUtil.buildSuccessRsp("");
    }
    
    /**
     * 用户登出
     * @return
     */
    @RequestMapping(value = "/logout")
    @ApiOperation(value = "退出", httpMethod = "GET")
    public BaseDto<String> logout()
    {
        AuthUtil.logout();
        LoggerManager.info(getClass(), "登出");
        return RspUtil.buildSuccessRsp("");
    }
}
