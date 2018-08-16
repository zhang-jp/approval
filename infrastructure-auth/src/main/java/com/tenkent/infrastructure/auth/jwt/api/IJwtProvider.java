package com.tenkent.infrastructure.auth.jwt.api;

import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.jwt.api.model.JwtToken;

public interface IJwtProvider
{
    <T extends ISessionUser> JwtToken create(T user);
    
    JwtToken refresh(String token);
    
    ISessionUser verifyToken(String token);
}
