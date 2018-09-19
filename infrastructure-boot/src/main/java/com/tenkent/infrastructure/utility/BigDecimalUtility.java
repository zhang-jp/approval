package com.tenkent.infrastructure.utility;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class BigDecimalUtility {
    public static boolean isLesserThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == -1;
    }
    
    public static boolean isLessThanOrEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }
    
    public static boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }
    
    /**
     * 进行加法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        a = null2Zero(a);
        b = null2Zero(b);
        return a.add(b);
    }
    
    /**
     * 进行减法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        a = null2Zero(a);
        b = null2Zero(b);
        return a.subtract(b);
    }
    
    /**
     * 进行乘法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul(int a, BigDecimal b) {
        return mul(new BigDecimal(a), b);
    }
    
    /**
     * 进行乘法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul(String a, BigDecimal b) {
        return mul(new BigDecimal(a), b);
    }
    
    /**
     * 进行乘法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        a = null2Zero(a);
        b = null2Zero(b);
        return a.multiply(b);
    }
    
    /**
     * 进行除法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal div(Integer a, Integer b) {
        return div(new BigDecimal(a), new BigDecimal(b));
    }
    
    /**
     * 进行除法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return div(a, b, 2);
    }
    
    /**
     * 进行除法运算 并 四舍五入
     * @param a
     * @param b
     * @param len
     * @return
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b, int len) {
        return a.divide(b, len, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 进行除法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divDown(Integer a, Integer b) {
        return divDown(new BigDecimal(a), new BigDecimal(b));
    }
    
    /**
     * 进行除法运算
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divDown(BigDecimal a, BigDecimal b) {
        return divHalfDown(a, b, 2);
    }
    
    /**
     * 进行除法运算 并 四舍五入
     * @param a
     * @param b
     * @param len
     * @return
     */
    public static BigDecimal divHalfDown(BigDecimal a, BigDecimal b, int len) {
        return a.divide(b, len, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 始终对非零舍弃部分前面的数字加1
     * @param arg
     * @return
     */
    public static int roundUp(BigDecimal arg) {
        return roundUp(arg, 0).intValue();
    }
    
    public static BigDecimal roundUp(BigDecimal arg, int len) {
        return arg.setScale(len, BigDecimal.ROUND_UP);
    }
    
    /**
     * 四舍五入
     * @param argd
     * @return
     */
    public static BigDecimal roundHalfUp(BigDecimal arg) {
        return roundHalfUp(arg, 2);
    }
    
    /**
     * 四舍五入
     * @param arg
     * @param len
     * @return
     */
    public static BigDecimal roundHalfUp(BigDecimal arg, int len) {
        return arg.setScale(len, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal null2Zero(BigDecimal arg) {
        return arg == null ? BigDecimal.ZERO : arg;
    }
    
    /**
     * 校验小数位数
     * @param value 数值
     * @param digit 位数
     * @return 是否是x位小数
     */
    public static boolean isDigitDeceimal(String value, int digit) {
        if (StringUtility.isEmpty(value)) {
            return false;
        }
        
        String[] arr = value.split("\\.");
        if (ArrayUtils.isEmpty(arr) || arr.length > 2) {
            return false;
        }
        
        if (arr.length == 1) {
            return NumberUtils.isNumber(arr[0]);
        }
        else {
            return NumberUtils.isNumber(arr[0]) && NumberUtils.isNumber(arr[1]) && arr[1].length() <= digit;
        }
    }
}
