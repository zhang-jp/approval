package com.tenkent.infrastructure.auth.util;

import com.tenkent.infrastructure.auth.api.IAuthSession;
import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.core.AuthSession;
import com.tenkent.infrastructure.auth.core.AuthSessionManage;

/**
 * 认证帮助类
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月22日]
 */
public class AuthUtil
{
    private static AuthSessionManage sessionManage;
    
    private static final ThreadLocal<IAuthSession> LOCAL_SESSION = new ThreadLocal<IAuthSession>();
    
    /**
     * 设置会话管理器
     * @param sessionManage
     */
    public static void setSessionManage(AuthSessionManage sessionManage)
    {
        AuthUtil.sessionManage = sessionManage;
    }
    
    /**
     * 获取session信息
     * @param sessionId
     * @return
     */
    public static IAuthSession get(String sessionId)
    {
        // 先清除线程原有数据
        //LOCAL_SESSION.remove();
        
        // 获取缓存信息
        IAuthSession session = sessionManage.getSession(sessionId);
        LOCAL_SESSION.set(session);
        
        return session;
    }
    
    /**
     * 登录
     * @param user
     * @return
     */
    public static String login(ISessionUser user)
    {
        IAuthSession session = new AuthSession();
        session.setSubject(user);
        String sessionId = sessionManage.login(session);
        LOCAL_SESSION.set(session);
        return sessionId;
    }
    
    /**
     * 更新Session
     * @param user
     * @return
     */
    public static IAuthSession update(ISessionUser user)
    {
        IAuthSession session = LOCAL_SESSION.get();
        session.setSubject(user);
        sessionManage.updateSession(session);
        LOCAL_SESSION.set(session);
        return LOCAL_SESSION.get();
    }
    
    /**
     * 获取用户信息
     * @return
     */
    public static ISessionUser getLoginUser()
    {
        IAuthSession session = LOCAL_SESSION.get();
        if (null == session)
        {
            return null;
        }
        return session.getSubject();
    }
    
    /**
     * 获取sessionId
     * @return
     */
    public static String getSessionId()
    {
        IAuthSession session = LOCAL_SESSION.get();
        if (null == session)
        {
            return null;
        }
        return session.getSessionId();
    }
    
    /**
     * 退出
     */
    public static void logout()
    {
        IAuthSession session = LOCAL_SESSION.get();
        sessionManage.removeSubject(session.getSessionId());
        LOCAL_SESSION.remove();
    }
    
    /**
     * 移除本地变量
     */
    public static void clearLocalSession()
    {
        LOCAL_SESSION.remove();
    }
}