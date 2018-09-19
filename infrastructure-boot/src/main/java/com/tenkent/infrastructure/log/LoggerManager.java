package com.tenkent.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenkent.infrastructure.utility.StringUtility;

/**
 * 日志管理器
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class LoggerManager {
    public static void info(Class<?> clazz, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message, args);
    }
    
    public static void error(Class<?> clazz, Exception ex, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        
        logger.error(StringUtility.format(message, args), ex);
    }
}
