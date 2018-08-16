package com.tenkent.infrastructure.auth.api;

import com.tenkent.infrastructure.auth.api.model.ISessionUser;

public interface IAuthSession
{
    ISessionUser getSubject();
    
    void setSubject(ISessionUser subject);
    
    void removeSubject();
    
    String getSessionId();
    
    void setSessionId(String sessionId);
}
