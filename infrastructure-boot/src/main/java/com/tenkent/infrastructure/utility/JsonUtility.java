package com.tenkent.infrastructure.utility;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json工具类
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年3月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsonUtility
{
    public static String toJSONString(Object object)
    {
        //Gson gSon = new Gson();
        Gson gSon = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gSon.toJson(object);
    }
    
    public static <T> T fromJSONString(String json, Class<T> classOfT)
    {
        Gson gSon = new Gson();
        return gSon.fromJson(json, classOfT);
    }
    
    /**
     * 将获取到的json字符串转换为对象集合进行返回
     * @param jsonStr 需要解析的json字符串
     * @param classOfT 类模板
     * @return
     */
    public static <T> List<T> getObjList(String jsonStr, Class<T> classOfT)
    {
        List<T> list = new ArrayList<>();
        if (jsonStr.startsWith("[") && jsonStr.endsWith("]"))
        {//当字符串以“[”开始，以“]”结束时，表示该字符串解析出来为集合
             //截取字符串，去除中括号
            String tempjsonData = jsonStr.substring(1, jsonStr.length() - 1);
            //将字符串以"},"分解成数组
            String[] strArr = tempjsonData.split("\\},");
            //分解后的字符串数组的长度
            int strArrLength = strArr.length;
            //遍历数组，进行解析，将字符串解析成对象
            for (int i = 0; i < strArrLength; i++)
            {
                String newJsonString;
                if (i == strArrLength - 1)
                {
                    newJsonString = strArr[i];
                }
                else
                {
                    newJsonString = strArr[i] + "}";
                }
                T bean = fromJSONString(newJsonString, classOfT);
                list.add(bean);
            }
        }
        return list;
    }
}
