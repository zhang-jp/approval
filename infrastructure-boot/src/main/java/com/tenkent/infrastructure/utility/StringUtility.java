package com.tenkent.infrastructure.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tenkent.infrastructure.constant.CharacterConstant;
import com.tenkent.infrastructure.exception.InfrastructureException;
import com.tenkent.infrastructure.log.LoggerManager;

/**
 * Created by 10115916 on 2016/7/15 0015.
 */
public class StringUtility
{
    /**
     * 私有构造函数
     */
    private StringUtility()
    {
    }
    
    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str)
    {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为不可见字符（如空格）<br>
     * 3、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }
    
    /**
     * 是否包含空字符串
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasBlank(String... strs)
    {
        if (CollectionUtility.isEmpty(strs))
        {
            return true;
        }
        
        for (String str : strs)
        {
            if (isBlank(str))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 字符串是否为空，空的定义如下 1、为null <br>
     * 2、为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
    
    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(String str)
    {
        return str == null ? CharacterConstant.EMPTY : str;
    }
    
    /**
     * 当给定字符串为空字符串时，转换为<code>null</code>
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(String str)
    {
        return isEmpty(str) ? null : str;
    }
    
    /**
     * 是否包含空字符串
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(String... strs)
    {
        for (String str : strs)
        {
            if (isEmpty(str))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 去除字符串两边的空格符，如果为null返回null
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String trim(String str)
    {
        return (null == str) ? null : str.trim();
    }
    
    /**
     * 获得set或get方法对应的标准属性名<br/>
     * 例如：setName 返回 name
     *
     * @param getOrSetMethodName
     * @return 如果是set或get方法名，返回field， 否则null
     */
    public static String getGeneralField(String getOrSetMethodName)
    {
        if (getOrSetMethodName.startsWith("get") || getOrSetMethodName.startsWith("set"))
        {
            return cutPreAndLowerFirst(getOrSetMethodName, 3);
        }
        return null;
    }
    
    /**
     * 生成set方法名<br/>
     * 例如：name 返回 setName
     *
     * @param fieldName 属性名
     * @return setXxx
     */
    public static String genSetter(String fieldName)
    {
        return upperFirstAndAddPre(fieldName, "set");
    }
    
    /**
     * 生成get方法名
     *
     * @param fieldName 属性名
     * @return getXxx
     */
    public static String genGetter(String fieldName)
    {
        return upperFirstAndAddPre(fieldName, "get");
    }
    
    /**
     * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
     * 例如：str=setName, preLength=3 -> return name
     *
     * @param str       被处理的字符串
     * @param preLength 去掉的长度
     * @return 处理后的字符串，不符合规范返回null
     */
    public static String cutPreAndLowerFirst(String str, int preLength)
    {
        if (str == null)
        {
            return null;
        }
        if (str.length() > preLength)
        {
            char first = Character.toLowerCase(str.charAt(preLength));
            if (str.length() > preLength + 1)
            {
                return first + str.substring(preLength + 1);
            }
            return String.valueOf(first);
        }
        return null;
    }
    
    /**
     * 原字符串首字母大写并在其首部添加指定字符串 例如：str=name, preString=get -> return getName
     *
     * @param str       被处理的字符串
     * @param preString 添加的首部
     * @return 处理后的字符串
     */
    public static String upperFirstAndAddPre(String str, String preString)
    {
        if (str == null || preString == null)
        {
            return null;
        }
        return preString + upperFirst(str);
    }
    
    /**
     * 大写首字母<br>
     * 例如：str = name, return Name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String upperFirst(String str)
    {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
    /**
     * 小写首字母<br>
     * 例如：str = Name, return name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String lowerFirst(String str)
    {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
    
    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(String str, String prefix)
    {
        if (str != null && str.startsWith(prefix))
        {
            return str.substring(prefix.length());
        }
        return str;
    }
    
    /**
     * 忽略大小写去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
     */
    public static String removePrefixIgnoreCase(String str, String prefix)
    {
        if (str != null && str.toLowerCase().startsWith(prefix.toLowerCase()))
        {
            return str.substring(prefix.length());
        }
        return str;
    }
    
    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix)
    {
        if (str != null && str.endsWith(suffix))
        {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }
    
    /**
     * 忽略大小写去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffixIgnoreCase(String str, String suffix)
    {
        if (str != null && str.toLowerCase().endsWith(suffix.toLowerCase()))
        {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }
    
    /**
     * 清理空白字符
     *
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(String str)
    {
        if (str == null)
        {
            return null;
        }
        
        return str.replaceAll("\\s*", CharacterConstant.EMPTY);
    }
    
    /**
     * 切分字符串<br/>
     * a#b#c -> [a,b,c] a##b#c -> [a,"",b,c]
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator)
    {
        return split(str, separator, 0);
    }
    
    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, int limit)
    {
        if (str == null)
        {
            return null;
        }
        List<String> list = new ArrayList<>(limit == 0 ? 16 : limit);
        if (limit == 1)
        {
            list.add(str);
            return list;
        }
        
        boolean isNotEnd = true; // 未结束切分的标志
        
        int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++)
        {
            char c = str.charAt(i);
            if (isNotEnd && c == separator)
            {
                list.add(sb.toString());
                // 清空StringBuilder
                
                sb.delete(0, sb.length());
                
                // 当达到切分上限-1的量时，将所剩字符全部作为最后一个串
                
                if (limit != 0 && list.size() == limit - 1)
                {
                    isNotEnd = false;
                }
            }
            else
            {
                sb.append(c);
            }
        }
        list.add(sb.toString());
        return list;
    }
    
    /**
     * 切分字符串<br>
     * from jodd
     *
     * @param str       被切分的字符串
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String[] split(String str, String delimiter)
    {
        if (str == null)
        {
            return null;
        }
        if (str.trim().length() == 0)
        {
            return new String[] {str};
        }
        
        int dellen = delimiter.length(); // del length
        
        int maxparts = (str.length() / dellen) + 2; // one more for the last
        
        int[] positions = new int[maxparts];
        
        int i = 0;
        int j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = str.indexOf(delimiter, j)) != -1)
        {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = str.length();
        
        String[] result = new String[count];
        
        for (i = 0; i < count; i++)
        {
            result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }
    
    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" example: abcdefgh 2 3 -> c abcdefgh 2 -3 -> cde
     *
     * @param string    String
     * @param fromIndex 开始的index（包括）
     * @param toIndex   结束的index（不包括）
     * @return 字串
     */
    public static String sub(String string, int fromIndex, int toIndex)
    {
        int len = string.length();
        
        if (fromIndex < 0)
        {
            fromIndex = len + fromIndex;
            
            if (toIndex == 0)
            {
                toIndex = len;
            }
        }
        
        if (toIndex < 0)
        {
            toIndex = len + toIndex;
        }
        
        if (toIndex < fromIndex)
        {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }
        
        if (fromIndex == toIndex)
        {
            return CharacterConstant.EMPTY;
        }
        
        char[] strArray = string.toCharArray();
        char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
        return new String(newStrArray);
    }
    
    /**
     * 切割前部分
     *
     * @param string  字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的字符串
     */
    public static String subPre(String string, int toIndex)
    {
        return sub(string, 0, toIndex);
    }
    
    /**
     * 切割后部分
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后的字符串
     */
    public static String subSuf(String string, int fromIndex)
    {
        if (isEmpty(string))
        {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }
    
    /**
     * 重复某个字符
     *
     * @param c     被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count)
    {
        char[] result = new char[count];
        for (int i = 0; i < count; i++)
        {
            result[i] = c;
        }
        return new String(result);
    }
    
    /**
     * 重复某个字符串
     *
     * @param str   被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(String str, int count)
    {
        
        // 检查
        
        final int len = str.length();
        final long longSize = (long)len * (long)count;
        final int size = (int)longSize;
        if (size != longSize)
        {
            throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + longSize);
        }
        
        final char[] array = new char[size];
        str.getChars(0, len, array, 0);
        int n;
        for (n = len; n < size - n; n <<= 1)
        {//n <<= 1相当于n *2
            
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }
    
    /**
     * 比较两个字符串是否相同，如果为null或者空串则算不同
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 是否非空相同
     */
    public static boolean equalsNotEmpty(String str1, String str2)
    {
        if (isEmpty(str1))
        {
            return false;
        }
        return str1.equals(str2);
    }
    
    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... values)
    {
        if (CollectionUtility.isEmpty(values) || isBlank(template))
        {
            return template;
        }
        
        final StringBuilder sb = new StringBuilder();
        final int length = template.length();
        
        int valueIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++)
        {
            if (valueIndex >= values.length)
            {
                sb.append(sub(template, i, length));
                break;
            }
            
            currentChar = template.charAt(i);
            if (currentChar == '{')
            {
                final char nextChar = template.charAt(++i);
                if (nextChar == '}')
                {
                    sb.append(values[valueIndex++]);
                }
                else
                {
                    sb.append('{').append(nextChar);
                }
            }
            else
            {
                sb.append(currentChar);
            }
            
        }
        
        return sb.toString();
    }
    
    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, List<String> values)
    {
        if (CollectionUtility.isEmpty(values) || isBlank(template))
        {
            return template;
        }
        
        final StringBuilder sb = new StringBuilder();
        final int length = template.length();
        
        int valueIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++)
        {
            if (valueIndex >= values.size())
            {
                sb.append(sub(template, i, length));
                break;
            }
            
            currentChar = template.charAt(i);
            if (currentChar == '{')
            {
                final char nextChar = template.charAt(++i);
                if (nextChar == '}')
                {
                    sb.append(values.get(valueIndex++));
                }
                else
                {
                    sb.append('{').append(nextChar);
                }
            }
            else
            {
                sb.append(currentChar);
            }
            
        }
        
        return sb.toString();
    }
    
    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map)
    {
        if (null == map || map.isEmpty())
        {
            return template;
        }
        
        for (Map.Entry<?, ?> entry : map.entrySet())
        {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return template;
    }
    
    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集
     * @return 编码后的字节码
     */
    public static byte[] encode(String str, String charset)
    {
        if (str == null)
        {
            return null;
        }
        
        try
        {
            return str.getBytes(charset);
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerManager.error(StringUtility.class, e, format("encode error, Charset [{}] unsupported!", charset));
            throw new InfrastructureException(e, format("Charset [{}] unsupported!", charset));
        }
    }
    
    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集
     * @return 解码后的字符串
     */
    public static String decode(byte[] data, String charset)
    {
        if (data == null)
        {
            return null;
        }
        
        try
        {
            return new String(data, charset);
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerManager.error(StringUtility.class, e, format("decode error, Charset [{}] unsupported!", charset));
            throw new InfrastructureException(e, format("Charset [{}] unsupported!", charset));
        }
    }
    
    /**
     * 将多个对象字符化<br>
     * 每个对象字符化后直接拼接，无分隔符
     *
     * @param objs 对象数组
     * @return 字符串
     */
    public static String str(Object... objs)
    {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs)
        {
            sb.append(obj);
        }
        return sb.toString();
    }
    
    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(byte[] bytes, String charset)
    {
        return new String(bytes, Charset.forName(charset));
    }
    
    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br> 例如：HelloWorld->hello_world
     *
     * @param camelCaseStr 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String toUnderlineCase(String camelCaseStr)
    {
        if (camelCaseStr == null)
        {
            return null;
        }
        
        final int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++)
        {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if (i < (length - 1))
            {
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
            }
            if (Character.isUpperCase(c))
            {
                if (!isPreUpperCase || !isNextUpperCase)
                {
                    if (i > 0)
                    {
                        sb.append(CharacterConstant.UNDERLINE);
                    }
                }
                isPreUpperCase = true;
            }
            else
            {
                isPreUpperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
    
    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br> 例如：hello_world->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(String name)
    {
        if (name == null)
        {
            return null;
        }
        if (name.contains(CharacterConstant.UNDERLINE))
        {
            name = name.toLowerCase();
            
            StringBuilder sb = new StringBuilder(name.length());
            boolean upperCase = false;
            for (int i = 0; i < name.length(); i++)
            {
                char c = name.charAt(i);
                
                if (c == '_')
                {
                    upperCase = true;
                }
                else if (upperCase)
                {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                }
                else
                {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        else
            return name;
    }
    
    /**
     * 包装指定字符串
     *
     * @param str    被包装的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 包装后的字符串
     */
    public static String wrap(String str, String prefix, String suffix)
    {
        return format("{}{}{}", prefix, str, suffix);
    }
    
    /**
     * 指定字符串是否被包装
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(String str, String prefix, String suffix)
    {
        return str.startsWith(prefix) && str.endsWith(suffix);
    }
    
    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符串
     * @return 是否被包装
     */
    public static boolean isWrap(String str, String wrapper)
    {
        return isWrap(str, wrapper, wrapper);
    }
    
    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符
     * @return 是否被包装
     */
    public static boolean isWrap(String str, char wrapper)
    {
        return isWrap(str, wrapper, wrapper);
    }
    
    /**
     * 指定字符串是否被包装
     *
     * @param str        字符串
     * @param prefixChar 前缀
     * @param suffixChar 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(String str, char prefixChar, char suffixChar)
    {
        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }
    
    /**
     * 补充字符串以满足最小长度 StrUtil.padPre("1", 3, '0');//"001"
     *
     * @param str       字符串
     * @param minLength 最小长度
     * @param padChar   补充的字符
     * @return 补充后的字符串
     */
    public static String padPre(String str, int minLength, char padChar)
    {
        if (str.length() >= minLength)
        {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = str.length(); i < minLength; i++)
        {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }
    
    /**
     * 补充字符串以满足最小长度 StrUtil.padEnd("1", 3, '0');//"100"
     *
     * @param str       字符串
     * @param minLength 最小长度
     * @param padChar   补充的字符
     * @return 补充后的字符串
     */
    public static String padEnd(String str, int minLength, char padChar)
    {
        if (str.length() >= minLength)
        {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(str);
        for (int i = str.length(); i < minLength; i++)
        {
            sb.append(padChar);
        }
        return sb.toString();
    }
    
    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder()
    {
        return new StringBuilder();
    }
    
    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(int capacity)
    {
        return new StringBuilder(capacity);
    }
    
    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(String... strs)
    {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs)
        {
            sb.append(str);
        }
        return sb;
    }
    
    /**
     * 获得字符串对应字符集的byte数组
     *
     * @param str     字符串
     * @param charset 字符集编码
     * @return byte数组
     */
    public static byte[] bytes(String str, String charset)
    {
        if (null == str)
        {
            return null;
        }
        if (isBlank(charset))
        {
            return null;
        }
        return str.getBytes(Charset.forName(charset));
    }
    
    /***
     * 判断 String 是否int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input)
    {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
    
    /**
     * 数字型String字符串转换成int型数组
     *
     * @param str string类型的数组
     * @return
     */
    public static Integer[] stringToIntegerArray(String[] str)
    {
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++)
        {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }
    
    /**
     * 获取文件后缀
     *
     * @param src 文件路径/名称	文件路径   C:\Users\Public\Pictures\Sample Pictures\test.jpg
     * @return 如果文件后缀        jpg
     */
    public static String getFileExt(String src)
    {
        
        String filename = src.substring(src.lastIndexOf(File.separator) + 1, src.length());//获取到文件名
        
        return filename.substring(filename.lastIndexOf(CharacterConstant.EN_DOT_CHAR) + 1);
    }
    
    /**
     * 获取文件名称，不带文件后缀部分
     *
     * @param src 文件路径   C:\Users\Public\Pictures\Sample Pictures\test.jpg
     * @return 文件名称 不带文件后缀   	test
     */
    public static String getFileName(String src)
    {
        
        String filename = src.substring(src.lastIndexOf(File.separator) + 1, src.length());//获取到文件名
        
        return filename.substring(0, filename.lastIndexOf(CharacterConstant.EN_DOT_CHAR));
    }
    
    /** 
     * 判断是否是中文字符，为空认为不是
     * @param str 待判断的字符
     * @return 是否是中文字符
     */
    public static boolean isChineseChar(String str)
    {
        if (isEmpty(str))
        {
            return false;
        }
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str.trim());
        return m.find() ? true : false;
    }
    
    /**
     * 字母
     * <功能详细描述>
     * @param c
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLetter(char c)
    {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }
    
    /**  
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1  
     * @param String s 需要得到长度的字符串  
     * @return int 得到的字符串长度  
     */
    public static int length(String s)
    {
        if (s == null)
        {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++)
        {
            len++;
            if (!isLetter(c[i]))
            {
                len++;
            }
        }
        return len;
    }
}
