package com.tenkent.infrastructure.auth.api.model.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class MenuTree
{
    private List<MenuNode> nodes;
    
    private MenuNode root;
    
    public MenuTree(List<MenuNode> nodes)
    {
        this.nodes = nodes;
        this.root = new MenuNode();
        root.setId(0L);
        root.setParentId(null);
        root.setName("ROOT");
        root.setLink("");
    }
    
    public MenuNode build()
    {
        if (CollectionUtils.isEmpty(nodes))
        {
            return root;
        }
        
        build(root);
        return root;
    }
    
    private void build(MenuNode node)
    {
        List<MenuNode> childrens = getChildren(node);
        if (CollectionUtils.isNotEmpty(childrens))
        {
            node.getChildNodeList().addAll(childrens);
            
            for (MenuNode child : childrens)
            {
                build(child);
            }
        }
    }
    
    private List<MenuNode> getChildren(MenuNode node)
    {
        List<MenuNode> children = new ArrayList<MenuNode>();
        Long id = node.getId();
        for (MenuNode child : nodes)
        {
            if (id.equals(child.getParentId()))
            {
                children.add(child);
            }
        }
        return children;
    }
}