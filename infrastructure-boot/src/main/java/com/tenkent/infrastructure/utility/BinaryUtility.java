/*
 * 文 件 名:  BinaryUtility.java
 * 版    权:  Tenkent Technologies Co., Ltd. Copyright 2005-2016,  All rights reserved
 * 描    述:  <二进制工具类>
 * 修 改 人:  shiyajie
 * 修改时间:  2017年3月13日
 */
package com.tenkent.infrastructure.utility;

/**
 * 二进制工具类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class BinaryUtility {
    /** 
     * 私有函数
     */
    private BinaryUtility() {
        
    }
    
    /**
     * 二进制按位减去某数
     * 
     * @param sourceValue 原二进制数
     * @param figure 需要减去的位数
     * @return 减去后的数
     */
    public static int minus(int sourceValue, int figure) {
        // 按位取2的n－1次方，得到要减去的数字
        int target = (int)Math.pow(2, figure - 1.0D);
        
        // 取要减去数字的16位正数补码
        int complement = 0xFF ^ target;
        
        // 先判断被减数是不是含有这一位，
        // 如果没有（答案位0），则返回原数，否则返回被减数&补码
        return (sourceValue & target) == 0 ? sourceValue : (sourceValue & complement);
    }
    
    /**
     * 获取设置位 值： 0或者1
     * @param settings 设置值
     * @param bit 位
     * @return
     */
    public static int getBit(int settings, int bit) {
        return (settings >> (bit - 1)) & 0x1;
    }
    
    /**
     * 设置 比特位为1
     * @param settings
     * @param bit
     * @return
     */
    public static int setBitOne(int settings, int bit) {
        return settings | (int)Math.pow(2, bit - 1);
    }
    
    /**
     * 设置 比特位为0
     * @param settings
     * @param bit
     * @return
     */
    public static int setBitZero(int settings, int bit) {
        return settings & ~(int)Math.pow(2, bit - 1);
    }
    
    /*
    public static void main(String[] args)
    {
        //System.out.println(2 << 1);
        //System.out.println(2 ^ 3);
        //System.out.println(Math.pow(2, 1));
        
        //System.out.println(byteToBit((byte)0x40));
        //System.out.println(byteToBit7((byte)0x40));
        //System.out.println(Integer.SIZE);
        //System.out.println(byteToBit((byte)~4));
        
        System.out.println(setBitZero(8446, 8));
        System.out.println(setBitOne(8318, 1));
    }
    
    public static String byteToBit(byte b)
    {
        return "" + (byte)((b >> 7) & 0x1) + (byte)((b >> 6) & 0x1) + (byte)((b >> 5) & 0x1) + (byte)((b >> 4) & 0x1) + (byte)((b >> 3) & 0x1)
            + (byte)((b >> 2) & 0x1) + (byte)((b >> 1) & 0x1) + (byte)((b >> 0) & 0x1);
    }
    */
}
