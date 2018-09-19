package com.tenkent.infrastructure.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验工具
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class RegexPatternUtility {
    /**
     * 匹配方法
     * @param patternStr
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMatch(String patternStr, String value) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
