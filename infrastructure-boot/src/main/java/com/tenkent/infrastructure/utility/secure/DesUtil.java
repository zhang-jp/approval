package com.tenkent.infrastructure.utility.secure;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.tenkent.infrastructure.exception.InfrastructureException;

public class DesUtil
{
    private static final String DES = "DES";
    
    private DesUtil()
    {
    }
    
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     */
    public static String encrypt(String data, String key)
    {
        try
        {
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            return Base64.getEncoder().encodeToString(bt);
        }
        catch (Exception e)
        {
            throw new InfrastructureException(e, "des encrypt error");
        }
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     */
    public static String decrypt(String data, String key)
    {
        try
        {
            if (data == null)
                return null;
            byte[] buf = Base64.getDecoder().decode(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        }
        catch (Exception e)
        {
            throw new InfrastructureException(e, "des decrypt error");
        }
    }
    
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     */
    private static byte[] encrypt(byte[] data, byte[] key)
    {
        try
        {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
            
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            
            return cipher.doFinal(data);
        }
        catch (Exception e)
        {
            throw new InfrastructureException(e, "des decrypt error");
        }
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key)
    {
        try
        {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
            
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            
            return cipher.doFinal(data);
        }
        catch (Exception e)
        {
            throw new InfrastructureException(e, "des decrypt error");
        }
    }
}