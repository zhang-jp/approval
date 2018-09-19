package com.tenkent.infrastructure.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tenkent.infrastructure.constant.GlobalConstant;

/**
 * map集合工具类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
@SuppressWarnings("rawtypes")
public class MapUtility {
    /***
     * 根据KEY值获取MAP中的对应数据并转化成Long类型
     * @param map
     * @param key
     * @return
     */
    public static Long getMapLong(Map map, String key) {
        return (map.get(key) == null || StringUtility.isEmpty(String.valueOf(map.get(key)))) ? null
            : Long.parseLong(String.valueOf(map.get(key)));
    }
    
    /***
     * 根据KEY值获取MAP中的对应数据并转化成String类型
     * @param map
     * @param key
     * @return
     */
    public static String getMapString(Map map, String key) {
        return map.get(key) == null ? null : String.valueOf(map.get(key));
    }
    
    /***
     * 根据KEY值获取MAP中的对应数据是否为空
     * @param map
     * @param key
     * @return
     */
    public static boolean isMapKeyNullOrEmpty(Map map, String key) {
        return map.get(key) == null || map.get(key).equals(GlobalConstant.STRING_EMPTY);
    }
    
    /***
     * 根据KEY值获取MAP中的对应数据并转化成String的List
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> getMapStringList(Map map, String key) {
        if (!isMapKeyNullOrEmpty(map, key)) {
            return (List<String>)map.get(key);
        }
        return null;
    }
    
    /***
     * 根据KEY值获取MAP中的对应数据并转化成Long的List
     * @param map
     * @param key
     * @return
     */
    public static List<Long> getMapLongList(Map map, String key) {
        if (!isMapKeyNullOrEmpty(map, key)) {
            List<Long> longList = new ArrayList<Long>();
            List list = (List)map.get(key);
            for (Object o : list) {
                longList.add(Long.parseLong(o.toString()));
            }
            return longList;
        }
        
        return null;
    }
    
    /**
     * 获取map中指定key的整型值
     * @param map
     * @param key
     * @return
     */
    public static Integer getMapInt(Map map, String key) {
        return (map.get(key) == null || StringUtility.isEmpty(String.valueOf(map.get(key)))) ? null
            : Integer.parseInt(String.valueOf(map.get(key)));
    }
    
    /**
     * 获取map中指定key的整型值
     * 如果没有指定key，则返回默认值
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getMapInt(Map map, String key, int defaultValue) {
        return (map.get(key) == null || StringUtility.isEmpty(String.valueOf(map.get(key)))) ? defaultValue
            : Integer.parseInt(String.valueOf(map.get(key)));
    }
    
    /**
     * 获取map中指定key的整型值
     * @param map
     * @param key
     * @return
     */
    public static Byte getMapByte(Map map, String key) {
        return (map.get(key) == null || StringUtility.isEmpty(String.valueOf(map.get(key)))) ? null
            : Byte.parseByte(String.valueOf(map.get(key)));
    }
    
    /**
     * 获取map中指定key的整型值
     * @param map
     * @param key
     * @return
     */
    public static Double getMapDouble(Map map, String key) {
        return (getMapString(map, key) == null || StringUtility.isEmpty(getMapString(map, key))) ? null
            : Double.parseDouble(String.valueOf(map.get(key)));
    }
    
    /**
     * 获取Map中指定key的日期类型值
     * @param map
     * @param key
     * @return
     */
    public static Date getMapDate(Map map, String key) {
        return map.get(key) == null ? null : (Date)map.get(key);
    }
    
    /**
     * 获取Map中指定key的BigDecimal类型值
     * @param map
     * @param key
     * @return
     */
    public static BigDecimal getMapBigDecimal(Map map, String key) {
        return getMapDouble(map, key) == null ? null : new BigDecimal(getMapDouble(map, key));
    }
    
    /**
     * 判断map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        if (null == map || map.size() == 0 || map.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断map是否不为空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
