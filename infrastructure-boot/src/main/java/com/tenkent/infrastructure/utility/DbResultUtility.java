package com.tenkent.infrastructure.utility;

/**
 * Db结果检查工具
 * @author  qinzhengliang
 * @version  [版本号, 2017年5月2日]
 */
public class DbResultUtility
{
    private DbResultUtility()
    {
    }
    
    /**
     * 数据库操作是否失败
     * @param result
     * @return
     */
    public static boolean ifDbOperateFail(int result)
    {
        return result < 1;
    }
    
    /**
     * 数据库操作是否成功
     * @param result
     * @return
     */
    public static boolean ifDbOperateSuccess(int result)
    {
        return result > 0;
    }
}
