package com.tenkent.infrastructure.auth.api;

public interface IAuthSessionCache
{
    void set(String key, String loginUser, int seconds);
    
    boolean update(String key, String loginUser, int seconds);
    
    boolean update(String key, int seconds);
    
    String get(String key);
    
    void remove(String key);
}
