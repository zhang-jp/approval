package com.test.approval.flow.pojo.dto;

import java.util.List;

import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.api.model.menu.MenuNode;
import com.tenkent.infrastructure.auth.api.model.permission.Permission;
import com.test.approval.flow.repository.entity.User;

public class LoginUserDto implements ISessionUser
{
    private User user;
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    @Override
    public String getIdentityId()
    {
        return null;
    }
    
    @Override
    public List<Permission> getPermissionList()
    {
        return null;
    }
    
    @Override
    public MenuNode getMenuInfo()
    {
        return null;
    }
}
