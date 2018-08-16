package com.tenkent.infrastructure.auth.jwt.core;

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
import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.core.Section;
import com.tenkent.infrastructure.auth.jwt.api.IJwtProvider;
import com.tenkent.infrastructure.auth.jwt.api.model.JwtToken;
import com.tenkent.infrastructure.auth.util.WebUtility;
import com.tenkent.infrastructure.auth.util.WebUtils;

/**
 * 安全认证
 * @author  qinzhengliang
 * @version  [版本号, 2017年8月24日]
 */
public class JwtAuthFilter implements Filter
{
    Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    
    /**
    * 认证常量
    */
    private static final String AUTHC = "authc";
    
    /**
     * 路径匹配
     */
    private List<Section> sectionList;
    
    /**
     * JWT
     */
    private IJwtProvider jwtProvider;
    
    /**
     * token刷新地址片段
     */
    private String refreshTokenSectionUrl = "/api/token/refresh";
    
    /**
     * 地址匹配器
     */
    private PathMatcher pathMatcher = new AntPathMatcher();
    
    public JwtAuthFilter()
    {
    }
    
    public JwtAuthFilter(IJwtProvider jwtProvider, List<Section> sectionList)
    {
        this.sectionList = sectionList;
        this.jwtProvider = jwtProvider;
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
        
        // 获取请求URI
        String requestUri = WebUtils.getPathWithinApplication((HttpServletRequest)request);
        
        // 是否是刷新token
        if (pathMatcher.match(refreshTokenSectionUrl, requestUri))
        {
            // 获取请求中的token
            String token = getSessionId(request, response);
            if (StringUtils.isEmpty(token))
            {
                // 不存在sessionId 返回错误响应
                sendJsonResp(response, "认证token不存在");
                return;
            }
            
            try
            {
                JwtToken jt = jwtProvider.refresh(token);
                sendRefreshTokenJsonResp(response, jt);
                return;
            }
            catch (Exception e)
            {
                logger.error("刷新token失败", e);
                sendJsonResp(response, "刷新token失败");
                return;
            }
        }
        
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
            // 获取请求中的token
            String token = getSessionId(request, response);
            if (StringUtils.isEmpty(token))
            {
                // 不存在sessionId 返回错误响应
                sendJsonResp(response, returnMsg);
                return;
            }
            
            try
            {
                ISessionUser sessionUser = jwtProvider.verifyToken(token);
                if (null == sessionUser)
                {
                    logger.error("认证失败");
                    sendJsonResp(response, returnMsg);
                    return;
                }
                
                httpRequest.setAttribute("sessionUser", sessionUser);
            }
            catch (Exception e)
            {
                logger.error("认证失败", e);
                sendJsonResp(response, returnMsg);
                return;
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
    
    /**
     * 返回刷新token响应
     * @param response
     * @param returnMsg
     * @throws IOException
     */
    private void sendRefreshTokenJsonResp(ServletResponse response, JwtToken jt)
        throws IOException
    {
        logger.info("刷新TOKEN成功");
        JsonObject jo = new JsonObject();
        jo.addProperty("success", true);
        
        JsonObject token = new JsonObject();
        token.addProperty("token", jt.getToken());
        token.addProperty("refreshDate", jt.getRefreshDate());
        
        jo.add("data", token);
        jo.addProperty("returnCode", "10000000");
        jo.addProperty("returnMsg", "成功");
        
        WebUtility.sendJsonResponse((HttpServletResponse)response, jo.toString());
    }
}
