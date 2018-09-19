package com.tenkent.infrastructure.paging.condition;

import java.util.Map;

/**
 * 无参数条件
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class NoParamCustomInfo implements CustomInfo {
    @Override
    public Map<String, Object> getCustomMap() {
        return null;
    }
}
