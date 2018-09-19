package com.tenkent.infrastructure.utility;

/**
 * 公共正则表达式检测
 * 
 * @author  zhangjiaping
 * @version  [版本号, 2018年9月19日]
 */
public class CommonPatternUtil {
    private CommonPatternUtil() {
        super();
    }
    
    /**
     * 手机号码正则表达式
     */
    private static final String PHONE_PATTERN = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9]|9[0-9])\\d{8}$";
    
    /**
     * 邮箱验证规则
     */
    private static final String MAIL_PATTERN =
        "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    
    /**
     * 性别规则
     */
    private static final String SEX_PATTERN = "^(0|1)$";
    
    /**
     * 日志规则
     */
    private static final String LOG_PATTERN = "^(0|1|2|3)$";
    
    /**
     * 年份
     */
    private static final String YEAR_PATTERN = "^[12]\\d{3}$";
    
    /**
     * 简单密码，8-20位修改为6-20位，包含任意英文数字符号!#$%^&*-_ 
     */
    private static final String SIMPLE_PASSWORD_PATTERN = "^[\\w!#$%^&*-]{6,20}$";
    
    /**
     * 密码
     */
    private static final String PASSWORD_PATTERN = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\\da-zA-Z!#$%^&*]{6,20}$";
    
    /**
     * 必须含有英文，数字，可以含!@#$%^&_*这些符号，8-16位
     */
    private static final String PASSWORD_PATTERN_NUM_ENG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z!@#$%^&_*]{8,16}$";
    
    /**
     * 支付密码复杂度
     */
    private static final String PAY_PASSWORD_PATTERN = "^\\d{6}$";
    
    /**
     * 英文，数字
     */
    private static final String ENG_NUM_PATTERN = "^[A-Za-z0-9]+$";
    
    /**
     * 中文，英文，数字
     */
    private static final String CHN_ENG_NUM_PATTERN = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$";
    
    /**
     * 0-100整数
     */
    private static final String NUM_PATTERN = "^([0-9]{1,3}|100)$";
    
    /**
     * 0-100000整数
     */
    private static final String NUM_HUNDRED_PATTERN = "^([0-9]{1,6}|100000)$";
    
    /**
     * 金额100.01
     */
    private static final String AMOUNT_PATTERN = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    
    /**
     * 重量100.01
     */
    private static final String WEIGHT_PATTERN = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    
    /**
     * 手机号码校验
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPhone(String phone) {
        return RegexPatternUtility.isMatch(PHONE_PATTERN, phone);
    }
    
    /**
     * 邮箱校验
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMail(String mail) {
        return RegexPatternUtility.isMatch(MAIL_PATTERN, mail);
    }
    
    /**
     * 性别校验
     * <功能详细描述>
     * @param sex
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isSex(String sex) {
        return RegexPatternUtility.isMatch(SEX_PATTERN, sex);
    }
    
    /**
     * 性别校验
     * <功能详细描述>
     * @param sex
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLog(String logging) {
        return RegexPatternUtility.isMatch(LOG_PATTERN, logging);
    }
    
    /**
     * 年校验
     * <功能详细描述>
     * @param year
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isYear(String year) {
        return RegexPatternUtility.isMatch(YEAR_PATTERN, year);
    }
    
    /**
     * 简单密码校验，8-20位，包含任意英文数字符号!#$%^&*-_
     * 
     * @param password 密码内容
     * @return 是否符合密码要求，true表示符合
     */
    public static boolean isProperSimplePwd(String password) {
        return RegexPatternUtility.isMatch(SIMPLE_PASSWORD_PATTERN, password);
    }
    
    /**
     * 密码复杂度
     * 
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isProperPwdComplexity(String password) {
        return RegexPatternUtility.isMatch(PASSWORD_PATTERN, password);
    }
    
    /**
     * 密码复杂度
     * 
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isProperPwdComplexityNumEng(String password) {
        return RegexPatternUtility.isMatch(PASSWORD_PATTERN_NUM_ENG, password);
    }
    
    /**
     * 密码复杂度
     * <功能详细描述>
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isProperPayPwdComplexity(String password) {
        return RegexPatternUtility.isMatch(PAY_PASSWORD_PATTERN, password);
    }
    
    /**
     * 符合中文、英文、数字
     * 
     * @param str 待校验的字符串
     * @return
     */
    public static boolean isMatchChnEngNum(String str) {
        return RegexPatternUtility.isMatch(CHN_ENG_NUM_PATTERN, str);
    }
    
    /**
     * 符合英文、数字
     * 
     * @param str 待校验的字符串
     * @return
     */
    public static boolean isMatchEngNum(String str) {
        return RegexPatternUtility.isMatch(ENG_NUM_PATTERN, str);
    }
    
    /**
     * 0-100整数校验
     * @param rage
     * @return
     */
    public static boolean isNum(String rage) {
        return RegexPatternUtility.isMatch(NUM_PATTERN, rage);
    }
    
    /**
     * 1-100000整数校验
     * @param rage
     * @return
     */
    public static boolean isHUNDREDNum(String rage) {
        return RegexPatternUtility.isMatch(NUM_HUNDRED_PATTERN, rage);
    }
    
    /**
     * 金额校验
     * @param amount
     * @return
     */
    public static boolean isAmount(String amount) {
        return RegexPatternUtility.isMatch(AMOUNT_PATTERN, amount);
    }
    
    /**
     * 体积校验
     * @param weight
     * @return
     */
    public static boolean isWeight(String weight) {
        return RegexPatternUtility.isMatch(WEIGHT_PATTERN, weight);
    }
}
