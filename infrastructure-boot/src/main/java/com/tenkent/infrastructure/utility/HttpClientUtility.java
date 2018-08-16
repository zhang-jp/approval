/*
 * 文 件 名:  HttpClientUtility.java
 * 版    权:  Tenkent Technologies Co., Ltd. Copyright 2005-2016,  All rights reserved
 * 描    述:  <http请求工具类>
 * 修 改 人:  shiyajie
 * 修改时间:  2017年4月28日
 */
package com.tenkent.infrastructure.utility;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

import com.tenkent.infrastructure.constant.GlobalConstant;
import com.tenkent.infrastructure.log.LoggerManager;

/**
 * http请求工具类
 * 
 * @author  shiyajie
 * @version  [版本号, 2017年4月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpClientUtility
{

    public static String sendPost(String url, String param)
    {
        return sendPost(url, param, GlobalConstant.CHARSET_UTF8, GlobalConstant.CONTENT_TYPE_JSON);
    }

    public static String sendPost(String url, String param, String contentType)
    {
        String type = StringUtility.isEmpty(contentType) ? GlobalConstant.CONTENT_TYPE_JSON : contentType;
        return sendPost(url, param, GlobalConstant.CHARSET_UTF8, type);
    }

    public static String sendHeaderPost(String url, String param, Map<String, String> map, String contentType)
    {
        String type = StringUtility.isNotEmpty(contentType) ? GlobalConstant.CONTENT_TYPE_JSON : contentType;
        return sendHeaderPost(url, param, map, GlobalConstant.CHARSET_UTF8, type);
    }

    public static String sendPost(String url, String param, String charset, String contentType)
    {
        LoggerManager.info(HttpClientUtility.class, "START sendPost... url:{}, param:{}, charset:{}, contentType:{}", url, param, charset,
                contentType);

        String reqCharset = StringUtility.isEmpty(charset) ? GlobalConstant.CHARSET_UTF8 : charset;
        String type = StringUtility.isEmpty(contentType) ? GlobalConstant.CONTENT_TYPE_JSON : contentType;

        String result = StringUtils.EMPTY;
        PostMethod method = null;
        try
        {
            HttpClient client = new HttpClient();
            //设置连接超时时间为30秒（连接初始化时间）
            client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);

            //带参数的那个请求的访问串
            method = new PostMethod(url);
            // 发送请求
            //指定传送字符集为UTF-8格式
            RequestEntity requestEntity = new StringRequestEntity(param, type, reqCharset);
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);
            if (statusCode == 200)
            {
                //解析返回支付Json数据
                result = method.getResponseBodyAsString();
            }

            // 关闭连接
            client.getHttpConnectionManager().closeIdleConnections(1);
            LoggerManager.info(HttpClientUtility.class, "END sendPost...success, url:{}, param:{}, result:{}", url, param, result);
            return result;
        }
        catch (Exception e)
        {
            LoggerManager.error(HttpClientUtility.class, e, "invoke interface FAILED, url:{}, param:{}, charset:{}", url, param, charset,
                    result);
            return StringUtils.EMPTY;
        }
        finally
        {
            if (method != null)
            {
                // 释放连接
                method.releaseConnection();
            }
        }
    }

    public static String sendHeaderPost(String url, String param, Map<String, String> map, String charset, String contentType)
    {
        LoggerManager.info(HttpClientUtility.class, "START sendPost... url:{}, param:{}, charset:{}, contentType:{}", url, param, charset,
                contentType);

        String reqCharset = StringUtility.isEmpty(charset) ? GlobalConstant.CHARSET_UTF8 : charset;
        String type = StringUtility.isEmpty(contentType) ? GlobalConstant.CONTENT_TYPE_JSON : contentType;

        String result = StringUtils.EMPTY;
        PostMethod method = null;
        try
        {
            HttpClient client = new HttpClient();
            //设置连接超时时间为30秒（连接初始化时间）
            client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);

            //带参数的那个请求的访问串
            method = new PostMethod(url);
            //设置消息头
            if (CollectionUtility.isNotEmpty(map))
            {
                for (Map.Entry<String, String> entry : map.entrySet())
                {
                    method.addRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            // 发送请求
            //指定传送字符集为UTF-8格式
            RequestEntity requestEntity = new StringRequestEntity(param, type, reqCharset);
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);
            if (statusCode == 200)
            {
                //解析返回支付Json数据
                result = method.getResponseBodyAsString();
            }

            // 关闭连接
            client.getHttpConnectionManager().closeIdleConnections(1);
            LoggerManager.info(HttpClientUtility.class, "END sendPost...success, url:{}, param:{}, result:{}", url, param, result);
            return result;
        }
        catch (Exception e)
        {
            LoggerManager.error(HttpClientUtility.class, e, "invoke interface FAILED, url:{}, param:{}, charset:{}", url, param, charset,
                    result);
            return StringUtils.EMPTY;
        }
        finally
        {
            if (method != null)
            {
                // 释放连接
                method.releaseConnection();
            }
        }
    }

}
