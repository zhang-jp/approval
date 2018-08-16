package com.tenkent.infrastructure.auth.api.model.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuNode
{
    private Long id;
    
    private Long parentId;
    
    private String name;
    
    private String link;
    
    private List<MenuNode> childNodeList = new ArrayList<>();
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Long getParentId()
    {
        return parentId;
    }
    
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    public List<MenuNode> getChildNodeList()
    {
        return childNodeList;
    }
    
    public void setChildNodeList(List<MenuNode> childNodeList)
    {
        this.childNodeList = childNodeList;
    }
}