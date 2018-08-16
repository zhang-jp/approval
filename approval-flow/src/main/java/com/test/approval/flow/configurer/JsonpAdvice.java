/*
 * 文 件 名:  JsonpAdvice.java
 * 版    权:  tenkent Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  jsonp配置
 * 修 改 人:  shiyajie
 * 修改时间:  2018年5月7日
 */
package com.test.approval.flow.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * jsonp配置
 * @author  zhangjiaping
 * @version  [版本号, 2018年8月16日]
 */
@Configuration
@ControllerAdvice(basePackages = "com.test.approval.flow.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice
{
    public JsonpAdvice()
    {
        super("callback", "jsonp");
    }
}
