package com.tenkent.infrastructure.utility;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机数工具类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class RandomUtility {
    /**
     * 随机
     */
    private static Random rand = new SecureRandom();
    
    /**
     * 基数
     */
    private static final String[] CODE_BASE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
        "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d",
        "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    
    /**
     * 数字基数
     */
    private static final String[] DIGIT_BASE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    //定义验证码字符.去除了O和I等容易混淆的字母
    private static final String[] ALPHA = {"A", "B", "C", "D", "E", "F", "G", "H", "G", "K", "M", "N", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "2", "3", "4", "5", "6", "7", "8"};
    
    private RandomUtility() {
    }
    
    /**
     * 获取随机数
     * @param length
     * @param base
     * @return
     */
    private static String getRandomStr(int length, String[] base) {
        //随机验证码
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int nextInt = rand.nextInt();
            int randInt = Math.abs(nextInt == Integer.MIN_VALUE ? Integer.MAX_VALUE : nextInt);
            sb.append(base[randInt % base.length]);
        }
        
        return sb.toString();
    }
    
    /**
     * 获取字母数字随机数
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        return getRandomStr(length, CODE_BASE);
    }
    
    /**
     * 获取数字随机数
     * @param length
     * @return
     */
    public static String getDigitNumber(int length) {
        return getRandomStr(length, DIGIT_BASE);
    }
    
    /**
     * 获取随机验证码
     * @param length
     * @return
     */
    public static String captchaNumber(int length) {
        return getRandomStr(length, ALPHA);
    }
    
    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        }
        else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }
    
    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}
