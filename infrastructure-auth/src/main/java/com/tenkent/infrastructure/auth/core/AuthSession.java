package com.tenkent.infrastructure.auth.core;

import com.tenkent.infrastructure.auth.api.IAuthSession;
import com.tenkent.infrastructure.auth.api.model.ISessionUser;

public class AuthSession implements IAuthSession
{
    private ISessionUser subject;
    
    private String sessionId;
    
    @Override
    public String getSessionId()
    {
        return sessionId;
    }
    
    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
    
    @Override
    public ISessionUser getSubject()
    {
        return subject;
    }
    
    @Override
    public void setSubject(ISessionUser subject)
    {
        this.subject = subject;
    }
    
    @Override
    public void removeSubject()
    {
        subject = null;
    }
}
