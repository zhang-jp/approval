package com.tenkent.infrastructure.paging.condition;

import java.util.Map;

/**
 * 分页查询定制条件
 * @author  qinzhengliang
 * @version  [版本号, 2018年5月30日]
 */
public interface CustomInfo
{
    /**
     * 构建条件方法
     * @return
     */
    Map<String, Object> getCustomMap();
}
