package com.tenkent.infrastructure.utility.secure;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

import com.tenkent.infrastructure.exception.BaseRuntimeException;
import com.tenkent.infrastructure.log.LoggerManager;

/**
 * MD5加密工具类
 * 
 * @author  秦正亮
 * @version  [版本号, 2016年11月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Md5Utility
{
    /**
     * 私有构造方法
     */
    private Md5Utility()
    {
    }
    
    /** 
     * MD5加密 
     * @param message 要进行MD5加密的字符串 
     * @return 加密结果为32位小写字符串 
     */
    public static String getMD532Big(String message, String salt)
    {
        MessageDigest messageDigest = null;
        StringBuilder md5StrBuff = new StringBuilder();
        try
        {
            //加密算法
            messageDigest = MessageDigest.getInstance("MD5");
            //重置
            messageDigest.reset();
            
            //加盐
            messageDigest.update(salt.getBytes("UTF-8"));
            
            //加密后二进制数据
            byte[] byteArray = messageDigest.digest(message.getBytes("UTF-8"));
            
            //二进制数据转换
            for (int i = 0; i < byteArray.length; i++)
            {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                }
                else
                {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        }
        catch (Exception e)
        {
            LoggerManager.error(Md5Utility.class, e, "MD5摘要异常,message:{},salt:{}", message, salt);
            throw new BaseRuntimeException("MD5摘要异常", e);
        }
        return md5StrBuff.toString();
    }
    
    /**
     * 摘要
     * @param message
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getMd5(String message)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes("UTF-8"));
            byte b[] = md.digest();
            
            StringBuilder md5StrBuff = new StringBuilder();
            //二进制数据转换
            for (int i = 0; i < b.length; i++)
            {
                if (Integer.toHexString(0xFF & b[i]).length() == 1)
                {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & b[i]));
                }
                else
                {
                    md5StrBuff.append(Integer.toHexString(0xFF & b[i]));
                }
            }
            //32位加密  
            return md5StrBuff.toString();
        }
        catch (Exception e)
        {
            LoggerManager.error(Md5Utility.class, e, "MD5摘要异常,message:{}", message);
            return StringUtils.EMPTY;
        }
    }
    
    /**
     * 摘要
     * @param message
     * @return
     */
    public static String getMd5(String message, String chatset)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes(chatset));
            byte b[] = md.digest();
            
            StringBuilder md5StrBuff = new StringBuilder();
            //二进制数据转换
            for (int i = 0; i < b.length; i++)
            {
                if (Integer.toHexString(0xFF & b[i]).length() == 1)
                {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & b[i]));
                }
                else
                {
                    md5StrBuff.append(Integer.toHexString(0xFF & b[i]));
                }
            }
            //32位加密  
            return md5StrBuff.toString();
        }
        catch (Exception e)
        {
            LoggerManager.error(Md5Utility.class, e, "MD5摘要异常,message:{}", message);
            return StringUtils.EMPTY;
        }
    }
}
