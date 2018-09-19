package com.tenkent.infrastructure.utility.secure;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.tenkent.infrastructure.constant.SysErrorsEnum;
import com.tenkent.infrastructure.exception.InfrastructureException;

/**
 * DES加解密工具类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class Des3 {
    private static final String KEY_ALGORITHM = "DESede";
    
    private static final String DES_ALGORITHM = "desede";
    
    private static final String DES_CBC_TRANSFORMATION = "/CBC/PKCS5Padding";
    
    private static final String DES_ECB_TRANSFORMATION = "/ECB/PKCS5Padding";
    
    private Des3() {
    }
    
    /** 
     * 生成密钥 
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String generateKey() {
        try {
            //实例化密钥生成器  
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //初始化密钥生成器  
            kg.init(168);
            //生成密钥  
            SecretKey secretKey = kg.generateKey();
            //获取二进制密钥编码形式  
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 generate key error");
        }
    }
    
    /**
     * DES3加密
     * <功能详细描述>
     * @param key
     * @param keyiv
     * @param data
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String des3EncodeCBC(String key, String keyiv, String data) {
        try {
            byte[] str5 = des3EncodeCBC(key.getBytes(), keyiv.getBytes(), data.getBytes());
            return Base64.getEncoder().encodeToString(str5);
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 encode error");
        }
    }
    
    /**
     * DE3解密
     * <功能详细描述>
     * @param key
     * @param keyiv
     * @param str
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String des3DecodeCBC(String key, String keyiv, String str) {
        try {
            byte[] dstr = Base64.getDecoder().decode(str);
            byte[] data = des3DecodeCBC(key.getBytes(), keyiv.getBytes(), dstr);
            return new String(data);
        }
        catch (Exception e) {
            throw new InfrastructureException(SysErrorsEnum.SYS_PARAM_ERROE.getCode(), "des3 decode error");
        }
    }
    
    /**
     * ECB加密,不要IV
     * 
     * @param key 密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM + DES_ECB_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 encode error");
        }
    }
    
    /**
     * ECB解密,不要IV
     * 
     * @param key 密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM + DES_ECB_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 decode error");
        }
    }
    
    /**
     * CBC加密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM + DES_CBC_TRANSFORMATION);
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 encode error");
        }
    }
    
    /**
     * CBC解密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM + DES_CBC_TRANSFORMATION);
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            return cipher.doFinal(data);
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "des3 decode error");
        }
    }
}
