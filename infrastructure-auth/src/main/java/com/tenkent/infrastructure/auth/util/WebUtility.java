package com.tenkent.infrastructure.auth.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/7/23.
 */
public class WebUtility
{
    private WebUtility()
    {
    }
    
    public static boolean isAjaxRequest(HttpServletRequest httpServletRequest)
    {
        return "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
    }
    
    public static void sendJsonResponse(HttpServletResponse httpServletResponse, String jsonString)
        throws IOException
    {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        
        try (PrintWriter out = httpServletResponse.getWriter())
        {
            out.append(jsonString);
            out.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException("json data out error", e);
        }
    }
    
    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        
        if (ip.length() > 15)
        {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++)
            {
                String strIp = (String)ips[index];
                if (!("unknown".equalsIgnoreCase(strIp)))
                {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
