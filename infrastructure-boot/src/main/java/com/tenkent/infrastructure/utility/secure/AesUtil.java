package com.tenkent.infrastructure.utility.secure;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.tenkent.infrastructure.log.LoggerManager;

/**
 * AES加解密
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2017年4月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AesUtil
{
    /**
     * 私有构造方法<默认构造函数>
     */
    private AesUtil()
    {
    }
    
    /**
     * 算法/模式/补码方式 
     */
    private static final String SECRET = "AES/ECB/PKCS5Padding";
    
    /**
     *  AES 加密    
     * @param sSrc
     * @param iv
     * @param sKey
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String Encrypt(String sSrc, String iv, String sKey)
    {
        try
        {
            
            //判断Key是否为空   
            if (sKey == null)
            {
                return null;
            }
            
            // 判断Key是否为16位    
            if (sKey.length() != 16)
            {
                return null;
            }
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(SECRET);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            
            return Base64.getEncoder().encodeToString(encrypted);//此处使用BAES64做转码功能，同时能起到2次加密的作用。    
        }
        catch (Exception e)
        {
            LoggerManager.error(AesUtil.class, e, "AES加密失败:{}", sSrc);
            return null;
        }
    }
    
    /**
     *  AES 解密   
     * @param sSrc
     * @param iv
     * @param sKey
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String Decrypt(String sSrc, String iv, String sKey)
    {
        try
        {
            // 判断Key是否正确    
            if (sKey == null)
            {
                return null;
            }
            // 判断Key是否为16位    
            if (sKey.length() != 16)
            {
                return null;
            }
            byte[] raw = sKey.getBytes("utf8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(SECRET);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用bAES64解密    
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
            
            try
            {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            }
            catch (Exception e)
            {
                LoggerManager.error(AesUtil.class, e, "AES解密失败:{}", sSrc);
                return null;
            }
        }
        catch (Exception ex)
        {
            LoggerManager.error(AesUtil.class, ex, "bAES64解密失败:{}", sSrc);
            return null;
        }
    }
}
