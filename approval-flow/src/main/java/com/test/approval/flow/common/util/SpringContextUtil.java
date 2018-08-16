package com.test.approval.flow.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring容器，以访问容器中定义的其他bean
 * @author  zhangjiaping
 * @version  [版本号, 2018年8月16日]
 */
@Component
public class SpringContextUtil implements ApplicationContextAware
{
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;
    
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        if (SpringContextUtil.applicationContext == null)
        {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }
    
    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    
    public static Boolean containsBean(String beanName)
    {
        return applicationContext.containsBean(beanName);
    }
    
    public static Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return applicationContext.getBean(requiredType);
    }
}