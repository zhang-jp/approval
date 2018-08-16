package com.tenkent.infrastructure.paging;

import java.util.Collection;

import com.github.pagehelper.Page;
import com.tenkent.infrastructure.exception.InfrastructureException;

/**
 * 分页数据建造器
 * 
 * @author  qinzhengliang
 * @version  [版本号, 2017年5月15日]
 */
public class PageBuilder<E>
{
    private PageInfo<E> info = null;
    
    /**
     * 构造方法
     */
    public PageBuilder()
    {
        info = new PageInfo<>();
    }
    
    /**
     * 构造分页属性
     * @param <F>
     * @param page
     * @return
     */
    public PageBuilder<E> buildAttribute(Object page)
    {
        if (!(page instanceof Page))
        {
            throw new InfrastructureException("非分页数据类型");
        }
        info.init(page);
        return this;
    }
    
    /**
     * 构造数据
     * @param collection
     * @return
     */
    public PageBuilder<E> buildData(Collection<E> collection)
    {
        // 设置数据
        info.setSize(collection.size());
        info.getDataList().addAll(collection);
        
        // 计算数据库中行数
        calcRow();
        return this;
    }
    
    /**
     * 取回构建分页数据信息
     * @return
     */
    public PageInfo<E> retrieve()
    {
        return this.getInfo();
    }
    
    private PageInfo<E> getInfo()
    {
        return info;
    }
    
    /**
     * 计算行号
     */
    private void calcRow()
    {
        /*
         * 计算在数据库中的行号
         */
        if (info.getSize() == 0)
        {
            info.setStartRow(0);
            info.setEndRow(0);
        }
        else
        {
            //由于结果是>startRow的，所以实际的需要+1
            info.setStartRow(info.getStartRow() + 1);
            //计算实际的endRow（最后一页的时候特殊）
            info.setEndRow(info.getStartRow() - 1 + info.getSize());
        }
    }
    
    /**
     * 拷贝分页属性
     * @param info
     * @return
     */
    public static <F> PageInfo<F> buildPageInfo(PageInfo<?> info)
    {
        PageInfo<F> copy = new PageInfo<>();
        copy.setEndRow(info.getEndRow());
        //copy.setFirstPage(info.getf);
        copy.setFirstPage(info.getFirstPage());
        //copy.setHasNextPage(hasNextPage);
        copy.setHasPreviousPage(info.isHasNextPage());
        copy.setLastPage(info.getLastPage());
        //copy.setLastPage(lastPage);
        //copy.setNavigatepageNums(navigatepageNums);
        //copy.setNavigatePages(navigatePages);
        copy.setNextPage(info.getNextPage());
        copy.setPageNum(info.getPageNum());
        copy.setPageSize(info.getPageSize());
        copy.setPages(info.getPages());
        copy.setPrePage(info.getPrePage());
        copy.setSize(info.getSize());
        copy.setStartRow(info.getStartRow());
        copy.setTotal(info.getTotal());
        return copy;
    }
}
