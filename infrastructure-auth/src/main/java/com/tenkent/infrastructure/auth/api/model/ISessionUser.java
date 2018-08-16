package com.tenkent.infrastructure.auth.api.model;

import java.util.List;

import com.tenkent.infrastructure.auth.api.model.menu.MenuNode;
import com.tenkent.infrastructure.auth.api.model.permission.Permission;

/**
 * 认证用户信息
 * @author  qinzhengliang
 * @version  [版本号, 2018年1月22日]
 */
public interface ISessionUser
{
    /**
     * 用户ID 唯一
     * @return
     */
    String getIdentityId();
    
    /**
     * 接口权限
     * @return
     */
    List<Permission> getPermissionList();
    
    /**
     * 菜单资源
     * @return
     */
    MenuNode getMenuInfo();
}
