package com.tenkent.infrastructure.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tenkent.infrastructure.log.LoggerManager;

/**
 * Created by FirenzesEagle on 2016/7/7 0007.
 * Email:liumingbo2008@gmail.com
 */
public class XmlUtility
{
    private XmlUtility()
    {
    }
    
    /**
     * 将微信服务器发送的Request请求中Body的XML解析为Map
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseRequestXmlToMap(HttpServletRequest request)
    {
        try
        {
            // 解析结果存储在HashMap中
            InputStream inputStream = request.getInputStream();
            return parseInputStreamToMap(inputStream);
        }
        catch (Exception e)
        {
            LoggerManager.error(XmlUtility.class, e, "parse request xml to map exption");
            return new HashMap<>();
        }
    }
    
    /**
     * 将输入流中的XML解析为Map
     *
     * @param inputStream
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> parseInputStreamToMap(InputStream inputStream)
    {
        try
        {
            // 解析结果存储在HashMap中
            Map<String, String> map = new HashMap<String, String>();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            //遍历所有子节点
            for (Element e : elementList)
            {
                map.put(e.getName(), e.getText());
            }
            return map;
        }
        catch (Exception e)
        {
            LoggerManager.error(XmlUtility.class, e, "parse request xml to map exption");
            return new HashMap<>();
        }
    }
    
    /**
     * 将String类型的XML解析为Map
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXmlStringToMap(String str)
    {
        try
        {
            InputStream inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            return parseInputStreamToMap(inputStream);
        }
        catch (Exception e)
        {
            LoggerManager.error(XmlUtility.class, e, "parse request xml to map exption");
            return new HashMap<>();
        }
    }
    
}
