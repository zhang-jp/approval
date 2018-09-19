package com.tenkent.infrastructure.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 集合帮助类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class CollectionUtility {
    private CollectionUtility() {
    }
    
    /**
     * 以 conjunction 为分隔符将集合转换为字符串
     *
     * @param <T>         被处理的集合
     * @param collection  集合
     * @param conjunction 分隔符
     * @return 连接后的字符串
     */
    public static <T> String join(Iterable<T> collection, String conjunction) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (T item : collection) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }
    
    /**
     * 以 conjunction 为分隔符将数组转换为字符串
     *
     * @param <T>         被处理的集合
     * @param array       数组
     * @param conjunction 分隔符
     * @return 连接后的字符串
     */
    public static <T> String join(T[] array, String conjunction) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (T item : array) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }
    
    /**
     * 将Set排序（根据Entry的值）
     *
     * @param set 被排序的Set
     * @return 排序后的Set
     */
    public static List<Map.Entry<Long, Long>> sortEntrySetToList(Set<Map.Entry<Long, Long>> set) {
        List<Map.Entry<Long, Long>> list = new LinkedList<>(set);
        Collections.sort(list, (o1, o2) -> {
            if (o1.getValue() > o2.getValue())
                return 1;
            if (o1.getValue() < o2.getValue())
                return -1;
            return 0;
        });
        return list;
    }
    
    /**
     * 切取部分数据
     *
     * @param <T>             集合元素类型
     * @param surplusAlaDatas 原数据
     * @param partSize        每部分数据的长度
     * @return 切取出的数据或null
     */
    public static <T> List<T> popPart(Deque<T> surplusAlaDatas, int partSize) {
        if (surplusAlaDatas == null || surplusAlaDatas.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<T> currentAlaDatas = new ArrayList<>();
        int size = surplusAlaDatas.size();
        // 切割
        if (size > partSize) {
            for (int i = 0; i < partSize; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        }
        return currentAlaDatas;
    }
    
    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param excludedEnd 结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }
    
    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd   结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd) {
        return range(includedStart, excludedEnd, 1);
    }
    
    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param start 开始的数字（包含）
     * @param end   结束的数字（不包含）
     * @param step          步进
     * @return 数字列表
     */
    public static int[] range(int start, int end, int step) {
        int includedStart = start;
        int excludedEnd = end;
        int stepTemp = step;
        
        if (includedStart > excludedEnd) {
            int tmp = includedStart;
            includedStart = excludedEnd;
            excludedEnd = tmp;
        }
        
        if (stepTemp <= 0) {
            stepTemp = 1;
        }
        
        int deviation = excludedEnd - includedStart;
        int length = deviation / stepTemp;
        if (deviation % stepTemp != 0) {
            length += 1;
        }
        int[] range = new int[length];
        for (int i = 0; i < length; i++) {
            range[i] = includedStart;
            includedStart += stepTemp;
        }
        return range;
    }
    
    /**
     * 截取数组的部分
     *
     * @param list  被截取的数组
     * @param startTemp 开始位置（包含）
     * @param endTemp   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(List<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        int startTemp = start < 0 ? 0 : start;
        int endTemp = end < 0 ? 0 : end;
        
        if (startTemp > endTemp) {
            int tmp = startTemp;
            startTemp = endTemp;
            endTemp = tmp;
        }
        
        final int size = list.size();
        if (endTemp > size) {
            if (startTemp >= size) {
                return null;
            }
            endTemp = size;
        }
        
        return list.subList(startTemp, endTemp);
    }
    
    /**
     * 截取集合的部分
     *
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(Collection<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        
        return sub(new ArrayList<>(list), start, end);
    }
    
    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    
    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }
    
    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }
    
    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    
    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
    
    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        final Class<?> componetType = array.getClass().getComponentType();
        boolean isPrimitive = false;
        if (null != componetType) {
            isPrimitive = componetType.isPrimitive();
        }
        for (T t : array) {
            if (t == value) {
                return true;
            }
            return (!isPrimitive && null != value && value.equals(t));
        }
        return false;
    }
    
    /**
     * 将Entry集合转换为HashMap
     *
     * @param entryCollection entry集合
     * @return Map
     */
    public static <T, K> Map<T, K> toMap(Collection<Map.Entry<T, K>> entryCollection) {
        Map<T, K> map = new HashMap<>();
        for (Map.Entry<T, K> entry : entryCollection) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
    
    /**
     * 将集合转换为排序后的Set
     *
     * @param collection 集合
     * @param comparator 比较器
     * @return treeSet
     */
    public static <T> Set<T> toTreeSet(Collection<T> collection, Comparator<T> comparator) {
        final Set<T> treeSet = new TreeSet<>(comparator);
        for (T t : collection) {
            treeSet.add(t);
        }
        return treeSet;
    }
    
    /**
     * 排序集合
     *
     * @param collection 集合
     * @param comparator 比较器
     * @return treeSet
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        Collections.sort(list, comparator);
        return list;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Integer[] wrap(int... values) {
        final int length = values.length;
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Long[] wrap(long... values) {
        final int length = values.length;
        Long[] array = new Long[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Character[] wrap(char... values) {
        final int length = values.length;
        Character[] array = new Character[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Byte[] wrap(byte... values) {
        final int length = values.length;
        Byte[] array = new Byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Short[] wrap(short... values) {
        final int length = values.length;
        Short[] array = new Short[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Float[] wrap(float... values) {
        final int length = values.length;
        Float[] array = new Float[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Double[] wrap(double... values) {
        final int length = values.length;
        Double[] array = new Double[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
    
    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Boolean[] wrap(boolean... values) {
        final int length = values.length;
        Boolean[] array = new Boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }
}
