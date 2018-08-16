package com.tenkent.infrastructure.auth.util;

import com.google.gson.Gson;

public class JsonUtil
{
    public static String toJSONString(Object object)
    {
        Gson gSon = new Gson();
        return gSon.toJson(object);
    }
    
    public static <T> T fromJSONString(String json, Class<T> classOfT)
    {
        Gson gSon = new Gson();
        return gSon.fromJson(json, classOfT);
    }
}
