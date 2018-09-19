package com.tenkent.infrastructure.paging;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageHelper;
import com.tenkent.infrastructure.constant.GlobalConstant;
import com.tenkent.infrastructure.exception.InfrastructureException;
import com.tenkent.infrastructure.paging.condition.CustomInfo;
import com.tenkent.infrastructure.paging.condition.PageCondition;
import com.tenkent.infrastructure.utility.MapUtility;

/**
 * 分页page帮助类
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class PageUtility {
    private PageUtility() {
    }
    
    /**
     * 构建分页查询条件
     * @param map
     * @param clazz
     * @return
     */
    public static <T extends CustomInfo> PageCondition<T> buildCondition(Map<String, Object> map, Class<T> clazz) {
        // 分页页码设置
        int pageNum = MapUtility.getMapInt(map, GlobalConstant.PAGE_NUM, 1);
        int pageSize = MapUtility.getMapInt(map, GlobalConstant.PAGE_SIZE, 10);
        
        PageCondition<T> pageCondition = new PageCondition<>();
        pageCondition.setPageNum(pageNum);
        pageCondition.setPageSize(pageSize);
        
        // 业务定制参数
        T condition;
        try {
            condition = clazz.newInstance();
        }
        catch (Exception e) {
            throw new InfrastructureException(e, "构建分页参数查询条件异常");
        }
        
        pageCondition.setCustomInfo(condition);
        return pageCondition;
    }
    
    /**
     * 分页查询，包括排序
     * @param condition
     */
    public static void startPage(PageCondition<?> condition) {
        if (StringUtils.isEmpty(condition.getOrderBy())) {
            PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        }
        else {
            PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        }
    }
    
    /**
     * 分页查询
     * @param map
     */
    public static void startPage(Map<String, Object> map) {
        int pageNum = MapUtility.getMapInt(map, GlobalConstant.PAGE_NUM, 1);
        int pageSize = MapUtility.getMapInt(map, GlobalConstant.PAGE_SIZE, 10);
        PageHelper.startPage(pageNum, pageSize);
    }
    
    /**
     * 分页查询，包括排序
     * 
     * @param map 查询条件
     * @param orderBy 排序
     */
    public static void startPage(Map<String, Object> map, String orderBy) {
        int pageNum = MapUtility.getMapInt(map, GlobalConstant.PAGE_NUM, 1);
        int pageSize = MapUtility.getMapInt(map, GlobalConstant.PAGE_SIZE, 10);
        PageHelper.startPage(pageNum, pageSize, orderBy);
    }
    
    public static void startPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
    }
    
    /**
     * 分页查询，包括排序
     * 
     * @param pageNum 页数
     * @param pageSize 每页记录数
     * @param orderBy 排序
     */
    public static void startPage(int pageNum, int pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
    }
    
    /**
     * 排序
     * 
     * @param orderBy 排序条件
     */
    public static void orderBy(String orderBy) {
        PageHelper.orderBy(orderBy);
    }
}
