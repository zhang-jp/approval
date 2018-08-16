package com.tenkent.infrastructure.paging;

import java.util.ArrayList;
import java.util.Collection;

import com.github.pagehelper.Page;

/**
 * 分页组件
 * @author  qinzhengliang
 * @version  [版本号, 2017年5月16日]
 */
public class PageInfo<E>
{
    //当前页
    private int pageNum;
    
    //每页的数量
    private int pageSize;
    
    //当前页的数量
    private int size;
    
    //当前页面第一个元素在数据库中的行号
    private int startRow;
    
    //当前页面最后一个元素在数据库中的行号
    private int endRow;
    
    //总记录数
    private long total;
    
    //总页数
    private int pages;
    
    //第一页
    private int firstPage;
    
    //前一页
    private int prePage;
    
    //下一页
    private int nextPage;
    
    //最后一页
    private int lastPage;
    
    //是否为第一页
    private boolean isFirstPage = false;
    
    //是否为最后一页
    private boolean isLastPage = false;
    
    //是否有前一页
    private boolean hasPreviousPage = false;
    
    //是否有下一页
    private boolean hasNextPage = false;
    
    //导航页码数
    private int navigatePages;
    
    //所有导航页号
    private int[] navigatepageNums;
    
    //集合数据
    private Collection<E> dataList;
    
    /**
     * 构造行数
     */
    public PageInfo()
    {
    }
    
    /**
     * 构造行数
     */
    public PageInfo(Object o)
    {
        init(o);
    }
    
    /**
     * 包装Page对象
     * @param o
     */
    public void init(Object o)
    {
        if (o instanceof Page)
        {
            Page<?> page = (Page<?>)o;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            
            // 数据库行号
            this.startRow = page.getStartRow();
            this.endRow = page.getEndRow();
            
            //this.orderBy = page.getOrderBy();
            //this.list = page;
            //this.size = page.size();
            dataList = new ArrayList<>();
            
            this.pages = page.getPages();
            this.total = page.getTotal();
        }
        
        // 导航页默认8
        this.navigatePages = 8;
        //计算导航页
        calcNavigatepageNums();
        //计算前后页，第一页，最后一页
        calcPage();
        //判断页面边界
        judgePageBoudary();
    }
    
    /**
     * 计算导航页
     */
    private void calcNavigatepageNums()
    {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages)
        {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++)
            {
                navigatepageNums[i] = i + 1;
            }
        }
        else
        { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;
            
            if (startNum < 1)
            {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++)
                {
                    navigatepageNums[i] = startNum++;
                }
            }
            else if (endNum > pages)
            {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--)
                {
                    navigatepageNums[i] = endNum--;
                }
            }
            else
            {
                //所有中间页
                for (int i = 0; i < navigatePages; i++)
                {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }
    
    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage()
    {
        if (navigatepageNums != null && navigatepageNums.length > 0)
        {
            firstPage = navigatepageNums[0];
            lastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1)
            {
                prePage = pageNum - 1;
            }
            if (pageNum < pages)
            {
                nextPage = pageNum + 1;
            }
        }
    }
    
    /**
     * 判定页面边界
     */
    private void judgePageBoudary()
    {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }
    
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
    
    public int getSize()
    {
        return size;
    }
    
    public void setSize(int size)
    {
        this.size = size;
    }
    
    public int getStartRow()
    {
        return startRow;
    }
    
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }
    
    public int getEndRow()
    {
        return endRow;
    }
    
    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }
    
    public long getTotal()
    {
        return total;
    }
    
    public void setTotal(long total)
    {
        this.total = total;
    }
    
    public int getPages()
    {
        return pages;
    }
    
    public void setPages(int pages)
    {
        this.pages = pages;
    }
    
    public int getFirstPage()
    {
        return firstPage;
    }
    
    public void setFirstPage(int firstPage)
    {
        this.firstPage = firstPage;
    }
    
    public int getPrePage()
    {
        return prePage;
    }
    
    public void setPrePage(int prePage)
    {
        this.prePage = prePage;
    }
    
    public int getNextPage()
    {
        return nextPage;
    }
    
    public void setNextPage(int nextPage)
    {
        this.nextPage = nextPage;
    }
    
    public int getLastPage()
    {
        return lastPage;
    }
    
    public void setLastPage(int lastPage)
    {
        this.lastPage = lastPage;
    }
    
    public boolean isFirstPage()
    {
        return isFirstPage;
    }
    
    public void setFirstPage(boolean isFirstPage)
    {
        this.isFirstPage = isFirstPage;
    }
    
    public boolean isLastPage()
    {
        return isLastPage;
    }
    
    public void setLastPage(boolean isLastPage)
    {
        this.isLastPage = isLastPage;
    }
    
    public boolean isHasPreviousPage()
    {
        return hasPreviousPage;
    }
    
    public void setHasPreviousPage(boolean hasPreviousPage)
    {
        this.hasPreviousPage = hasPreviousPage;
    }
    
    public boolean isHasNextPage()
    {
        return hasNextPage;
    }
    
    public void setHasNextPage(boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }
    
    public int getNavigatePages()
    {
        return navigatePages;
    }
    
    public void setNavigatePages(int navigatePages)
    {
        this.navigatePages = navigatePages;
    }
    
    public int[] getNavigatepageNums()
    {
        return navigatepageNums;
    }
    
    public void setNavigatepageNums(int[] navigatepageNums)
    {
        this.navigatepageNums = navigatepageNums;
    }
    
    public Collection<E> getDataList()
    {
        return dataList;
    }
    
    public void setDataList(Collection<E> dataList)
    {
        this.dataList = dataList;
    }
}
