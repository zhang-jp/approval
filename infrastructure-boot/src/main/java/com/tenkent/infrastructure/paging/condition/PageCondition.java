package com.tenkent.infrastructure.paging.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询条件
 * @author  qinzhengliang
 * @version  [版本号, 2018年5月30日]
 * @param <T>
 */
public class PageCondition<T extends CustomInfo>
{
    /**
     * 当前页
     */
    private int pageNum;
    
    /**
     * 每页的数量
     */
    private int pageSize;
    
    /**
     * 排序
     */
    private String orderBy;
    
    /**
     * 业务定制条件
     */
    private T customInfo;
    
    public int getPageNum()
    {
        return pageNum;
    }
    
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getOrderBy()
    {
        return orderBy;
    }
    
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    
    public T getCustomInfo()
    {
        return customInfo;
    }
    
    public void setCustomInfo(T customInfo)
    {
        this.customInfo = customInfo;
    }
    
    /**
     * 获取查询条件
     * @return
     */
    public Map<String, Object> getCustomMap()
    {
        if (null == customInfo)
        {
            return new HashMap<>();
        }
        return customInfo.getCustomMap();
    }
}
