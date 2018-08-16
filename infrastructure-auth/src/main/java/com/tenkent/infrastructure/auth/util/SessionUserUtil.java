package com.tenkent.infrastructure.auth.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tenkent.infrastructure.auth.api.model.ISessionUser;

/**
 * token user 帮助类
 * @author  qinzhengliang
 * @version  [版本号, 2018年5月4日]
 */
public class SessionUserUtil
{
    private SessionUserUtil()
    {
    }
    
    /**
     * 用户对象转换String
     * @param user
     * @return
     */
    public static <T extends ISessionUser> String getStringSessionUser(T user)
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("clazzName", user.getClass().getName());
        Gson gSon = new Gson();
        jo.addProperty("value", gSon.toJson(user));
        String sessionValue = jo.toString();
        return sessionValue;
    }
    
    /**
     * 缓存String数据转换成用户对象
     * @param subject
     * @return
     */
    public static ISessionUser getSessionUserFromJsonString(String subject)
    {
        // 解析缓存数据
        JsonParser jsonParser = new JsonParser();
        JsonElement sessionElement = jsonParser.parse(subject);
        JsonObject sessionObject = sessionElement.getAsJsonObject();
        
        String clazzName = sessionObject.get("clazzName").getAsString();
        String value = sessionObject.get("value").getAsString();
        
        Class<?> clazz = null;
        try
        {
            clazz = Class.forName(clazzName);
        }
        catch (ClassNotFoundException e)
        {
            return null;
        }
        
        // 转换成用户对象  
        return (ISessionUser)JsonUtil.fromJSONString(value, clazz);
    }
}
