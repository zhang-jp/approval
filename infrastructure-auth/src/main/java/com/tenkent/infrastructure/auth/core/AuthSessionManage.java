package com.tenkent.infrastructure.auth.core;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.tenkent.infrastructure.auth.api.IAuthSession;
import com.tenkent.infrastructure.auth.api.IAuthSessionCache;
import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.util.SessionUserUtil;

public class AuthSessionManage
{
    private IAuthSessionCache sessionCache;
    
    private int sessionTimeOut;
    
    public AuthSessionManage()
    {
    }
    
    public AuthSessionManage(IAuthSessionCache sessionCache, int sessionTimeOut)
    {
        this.sessionCache = sessionCache;
        this.sessionTimeOut = sessionTimeOut;
    }
    
    public String login(IAuthSession t)
    {
        String sessionId = UUID.randomUUID().toString();
        sessionCache.set(sessionId, SessionUserUtil.getStringSessionUser(t.getSubject()), sessionTimeOut);
        
        t.setSessionId(sessionId);
        return sessionId;
    }
    
    public IAuthSession getSession(String sessionId)
    {
        // 从缓存获取用户数据
        String subject = sessionCache.get(sessionId);
        if (StringUtils.isEmpty(subject))
        {
            return null;
        }
        
        // 延长过期时间
        expire(sessionId);
        
        // 解析
        ISessionUser su = SessionUserUtil.getSessionUserFromJsonString(subject);
        
        // 设置session返回
        AuthSession session = new AuthSession();
        session.setSessionId(sessionId);
        session.setSubject(su);
        return session;
    }
    
    public boolean updateSession(IAuthSession t)
    {
        return sessionCache.update(t.getSessionId(), SessionUserUtil.getStringSessionUser(t.getSubject()), sessionTimeOut);
    }
    
    public boolean expire(String sessionId)
    {
        return sessionCache.update(sessionId, sessionTimeOut);
    }
    
    public void removeSubject(String key)
    {
        sessionCache.remove(key);
    }
    
    public IAuthSessionCache getSessionCache()
    {
        return sessionCache;
    }
    
    public void setSessionCache(IAuthSessionCache sessionCache)
    {
        this.sessionCache = sessionCache;
    }
    
    public int getSessionTimeOut()
    {
        return sessionTimeOut;
    }
    
    public void setSessionTimeOut(int sessionTimeOut)
    {
        this.sessionTimeOut = sessionTimeOut;
    }
    
}
