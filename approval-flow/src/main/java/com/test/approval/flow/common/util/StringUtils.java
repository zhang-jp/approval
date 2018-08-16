package com.test.approval.flow.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils
{
    /**
     * 设置请求参数
     * @param map
     */
    public static String mapToString(Map<String, String[]> map)
    {
        if (map == null)
        {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)map).entrySet())
        {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr((param.getKey().equalsIgnoreCase("password") ? "" : paramValue), 100));
        }
        return params.toString();
    }
    
    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length)
    {
        if (str == null)
        {
            return "";
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : StringEscapeUtils.unescapeHtml4(str).toCharArray())
            {
                currentLength += String.valueOf(c).getBytes("utf-8").length;
                if (currentLength <= length - 3)
                {
                    sb.append(c);
                }
                else
                {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
}
