package com.tenkent.infrastructure.auth.core;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.google.gson.JsonObject;
import com.tenkent.infrastructure.auth.api.IAuthSession;
import com.tenkent.infrastructure.auth.api.model.permission.Permission;
import com.tenkent.infrastructure.auth.util.AuthUtil;
import com.tenkent.infrastructure.auth.util.WebUtility;
import com.tenkent.infrastructure.auth.util.WebUtils;

/**
 * 安全认证
 * @author  qinzhengliang
 * @version  [版本号, 2017年8月24日]
 */
public class AuthFilter implements Filter
{
    Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    
    /**
    * 认证常量
    */
    private static final String AUTHC = "authc";
    
    /**
     * 路径匹配
     */
    private List<Section> sectionList;
    
    /**
     * 权限检查开关
     */
    private boolean permissionCheckFlg = false;
    
    /**
     * 地址匹配器
     */
    private PathMatcher pathMatcher = new AntPathMatcher();
    
    public AuthFilter()
    {
    }
    
    public AuthFilter(AuthSessionManage sessionManage, List<Section> sectionList)
    {
        this.sectionList = sectionList;
        AuthUtil.setSessionManage(sessionManage);
    }
    
    public AuthFilter(AuthSessionManage sessionManage, List<Section> sectionList, boolean permissionCheckFlg)
    {
        this.sectionList = sectionList;
        this.permissionCheckFlg = permissionCheckFlg;
        AuthUtil.setSessionManage(sessionManage);
    }
    
    @Override
    public void init(FilterConfig filterConfig)
        throws ServletException
    {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse HttpResponse = (HttpServletResponse)response;
        HttpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // 过滤器只处理 GET POST 请求
        if (!"GET".equalsIgnoreCase(httpRequest.getMethod()) && !"POST".equals(httpRequest.getMethod()))
        {
            chain.doFilter(request, response);
            return;
        }
        
        // 匹配地址空检查
        if (CollectionUtils.isEmpty(sectionList))
        {
            chain.doFilter(request, response);
            return;
        }
        
        // 清空线程数据
        AuthUtil.clearLocalSession();
        
        // 获取请求URI
        String requestUri = WebUtils.getPathWithinApplication((HttpServletRequest)request);
        
        // 认证错误信息
        String returnMsg = "认证失败";
        
        // 获取地址片段
        Section section = getMatchedSection(requestUri);
        if (null == section)
        {
            // 没有匹配的地址片段不需要认证
            chain.doFilter(request, response);
            return;
        }
        
        // 存在匹配的片段，校验认证类型
        if (AUTHC.equals(section.getAuthFlg()))
        {
            // 需要获取登录信息
            // 获取请求中的sessionId
            String sessionId = getSessionId(request, response);
            if (StringUtils.isEmpty(sessionId))
            {
                // 不存在sessionId 返回错误响应
                sendJsonResp(response, returnMsg);
                return;
            }
            
            IAuthSession session = AuthUtil.get(sessionId);
            if (null == session)
            {
                // 缓存session信息不存在 返回错误响应
                sendJsonResp(response, returnMsg);
                return;
            }
            
            // 权限验证
            if (permissionCheckFlg)
            {
                Permission permission = getMatchedPermission(requestUri, session.getSubject().getPermissionList());
                if (null == permission)
                {
                    // 没有匹配的权限，用户不能操作接口
                    returnMsg = "没有操作权限";
                    sendJsonResp(response, returnMsg);
                    return;
                }
            }
        }
        else
        {
            // 不需要登录认证
            // 获取请求中的sessionId
            String sessionId = getSessionId(request, response);
            if (StringUtils.isNotEmpty(sessionId))
            {
                // 设置线程用户信息
                AuthUtil.get(sessionId);
            }
        }
        
        // 执行chain
        chain.doFilter(request, response);
        
    }
    
    @Override
    public void destroy()
    {
    }
    
    /**
     * 获取请求sessionId
     * @param request
     * @param response
     * @return
     */
    private String getSessionId(ServletRequest request, ServletResponse response)
    {
        String token = request.getParameter("token");
        HttpServletRequest rq = (HttpServletRequest)request;
        
        if (StringUtils.isEmpty(token))
        {
            token = rq.getHeader("token");
        }
        return token;
    }
    
    /**
     * 获取匹配的地址片段
     * @param requestUri
     * @return
     */
    private Section getMatchedSection(String requestUri)
    {
        // 遍历地址路径 检查匹配
        for (Section section : sectionList)
        {
            // 请求地址 匹配
            if (pathMatcher.match(section.getMatchedPath(), requestUri))
            {
                return section;
            }
        }
        
        return null;
    }
    
    /**
     * 获取匹配的权限
     * @param requestUri
     * @param permissionList
     * @return
     */
    private Permission getMatchedPermission(String requestUri, List<Permission> permissionList)
    {
        for (Permission permission : permissionList)
        {
            // 权限匹配检查
            if (requestUri.equalsIgnoreCase(permission.getPrefixUrl()))
            {
                return permission;
            }
        }
        
        return null;
    }
    
    /**
     * 返回json类型响应
     * @param response
     * @param returnMsg
     * @throws IOException
     */
    private void sendJsonResp(ServletResponse response, String returnMsg)
        throws IOException
    {
        logger.info("认证失败");
        JsonObject jo = new JsonObject();
        jo.addProperty("success", false);
        jo.addProperty("returnCode", "10000004");
        jo.addProperty("returnMsg", returnMsg);
        WebUtility.sendJsonResponse((HttpServletResponse)response, jo.toString());
    }
}
