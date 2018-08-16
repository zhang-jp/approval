package com.tenkent.infrastructure.utility.secure;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tenkent.infrastructure.log.LoggerManager;

public class SignUtil
{
    /**
     * 字符串签名
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getSha1(String str)
    {
        if (null == str || str.length() == 0)
        {
            return StringUtils.EMPTY;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try
        {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            
            return new String(buf);
        }
        catch (Exception e)
        {
            LoggerManager.error(SignUtil.class, e, "签名失败，param:{}", str);
            return StringUtils.EMPTY;
        }
    }
    
    /**
     * map 签名
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getMapSign(Map<String, String> map)
    {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (StringUtils.isNotEmpty(entry.getValue()))
            {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        
        String result = StringUtils.EMPTY;
        int size = list.size();
        if (size > 0)
        {
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++)
            {
                sb.append(arrayToSort[i]);
            }
            result = sb.substring(0, sb.length() - 1);
            result = getSha1(result);
        }
        return result;
    }
}
