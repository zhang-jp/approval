/*
 * 文 件 名:  DateUtility.java
 * 版    权:  Tenkent Technologies Co., Ltd. Copyright 2005-2016,  All rights reserved
 * 描    述:  <日期工具类>
 * 修 改 人:  shiyajie
 * 修改时间:  2016年11月22日
 */
package com.tenkent.infrastructure.utility;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.tenkent.infrastructure.log.LoggerManager;

/**
 * 日期工具类
 * 
 * @author  shiyajie
 * @version  [2016年11月22日]
 */
public class DateUtility
{
    /**
     * yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    
    /**
     * yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    /**
     * 10分钟的毫秒值
     */
    public static final long TEN_MIN_MS = 10 * 60 * 1000;
    
    /**
     * 私有构造函数
     */
    private DateUtility()
    {
    }
    
    public static Calendar getCurrentCalendar()
    {
        return Calendar.getInstance();
    }
    
    public static Timestamp getCurrentTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    
    public static Date getCurrentDatetime()
    {
        return new Date(System.currentTimeMillis());
    }
    
    public static Date getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }
    
    public static String getYear()
    {
        return formatDate("yyyy");
    }
    
    public static String getMonth()
    {
        return formatDate("MM");
    }
    
    public static String getDay()
    {
        return formatDate("dd");
    }
    
    public static final int getYear(Date date)
    {
        if (date == null)
        {
            return -1;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    public static final int getYear(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }
    
    public static final int getMonth(Date date)
    {
        if (date == null)
        {
            return -1;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    public static final int getMonth(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    public static final int getDay(Date date)
    {
        if (date == null)
        {
            return -1;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    
    public static final int getDay(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);
    }
    
    public static final int getHour(Date date)
    {
        if (date == null)
        {
            return -1;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    
    public static final int getHour(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static String getChinesePatternNow()
    {
        return formatDate(YYYY_MM_DD);
    }
    
    /**
     * YYYYMMDD
     * @return
     */
    public static String getChinesePatternDate()
    {
        return formatDate(YYYYMMDD);
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFullChinesePatternNow()
    {
        return formatDate(YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * YYYYMMDDHHMMSS
     * @return
     */
    public static String getFullChinesePatternTime()
    {
        return formatDate(YYYYMMDDHHMMSS);
    }
    
    public static String getChinesePatternNow(Date date)
    {
        return formatDate(date, YYYY_MM_DD);
    }
    
    public static String getFullCNPatternNow(Date date)
    {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }
    
    public static String formatDate(Date date)
    {
        return formatDate(date, YYYYMMDD);
    }
    
    public static String formatDate(Date date, String format)
    {
        if ((null == date) || (StringUtils.isBlank(format)))
        {
            return null;
        }
        
        return new SimpleDateFormat(format).format(date);
    }
    
    public static String formatDate(String format)
    {
        return formatDate(new Date(), format);
    }
    
    public static Date parseDate(String sDate)
    {
        return parseDate(sDate, YYYYMMDD, null);
    }
    
    public static Date parseDate(String sDate, String format)
    {
        return parseDate(sDate, format, null);
    }
    
    public static Date parseDate(String sDate, String format, Date defaultValue)
    {
        if ((StringUtils.isBlank(sDate)) || (StringUtils.isBlank(format)))
        {
            return defaultValue;
        }
        
        DateFormat formatter = new SimpleDateFormat(format);
        try
        {
            return formatter.parse(sDate);
        }
        catch (ParseException e)
        {
            LoggerManager.error(DateUtility.class,
                e,
                "parseDate catch exception , string date = {}",
                sDate);
        }
        return defaultValue;
    }
    
    public static Date addMonths(Date date, int months)
    {
        if (months == 0)
        {
            return date;
        }
        
        if (date == null)
        {
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, months);
        return cal.getTime();
    }
    
    public static Date addDays(Date date, int days)
    {
        if (days == 0)
        {
            return date;
        }
        
        if (date == null)
        {
            return null;
        }
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        
        return cal.getTime();
    }
    
    public static Date addMins(Date date, int mins)
    {
        if (mins == 0)
        {
            return date;
        }
        
        if (date == null)
        {
            return null;
        }
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, mins);
        
        return cal.getTime();
    }
    
    public static int compareDate(Date first, Date second)
    {
        if ((first == null) && (second == null))
        {
            return 0;
        }
        
        if (first == null)
        {
            return -1;
        }
        
        if (second == null)
        {
            return 1;
        }
        
        if (first.before(second))
        {
            return -1;
        }
        
        if (first.after(second))
        {
            return 1;
        }
        
        return 0;
    }
    
    public static Date getSmaller(Date first, Date second)
    {
        if ((first == null) && (second == null))
        {
            return null;
        }
        
        if (first == null)
        {
            return second;
        }
        
        if (second == null)
        {
            return first;
        }
        
        if (first.before(second))
        {
            return first;
        }
        
        if (first.after(second))
        {
            return second;
        }
        
        return first;
    }
    
    public static Date getLarger(Date first, Date second)
    {
        if ((first == null) && (second == null))
        {
            return null;
        }
        
        if (first == null)
        {
            return second;
        }
        
        if (second == null)
        {
            return first;
        }
        
        if (first.before(second))
        {
            return second;
        }
        
        if (first.after(second))
        {
            return first;
        }
        
        return first;
    }
    
    public static boolean isDateBetween(Date date, Date begin, Date end)
    {
        int c1 = compareDate(begin, date);
        int c2 = compareDate(date, end);
        
        return ((c1 == -1) && (c2 == -1)) || (c1 == 0) || (c2 == 0);
    }
    
    public static boolean isSameMonth(Date date1, Date date2)
    {
        if ((date1 == null) && (date2 == null))
        {
            return true;
        }
        
        if ((date1 == null) || (date2 == null))
        {
            return false;
        }
        
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = GregorianCalendar.getInstance();
        cal2.setTime(date2);
        return isSameMonth(cal1, cal2);
    }
    
    public static boolean isSameDay(Date date1, Date date2)
    {
        if ((date1 == null) && (date2 == null))
        {
            return true;
        }
        
        if ((date1 == null) || (date2 == null))
        {
            return false;
        }
        
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = GregorianCalendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(1) == cal2.get(1)) && (cal1.get(2) == cal2.get(2)) && (cal1.get(5) == cal2.get(5));
    }
    
    public static boolean isSameMonth(Calendar cal1, Calendar cal2)
    {
        if ((cal1 == null) && (cal2 == null))
        {
            return true;
        }
        
        if ((cal1 == null) || (cal2 == null))
        {
            return false;
        }
        
        return (cal1.get(1) == cal2.get(1)) && (cal1.get(2) == cal2.get(2));
    }
    
    public static Date getStartOfDate(Date date)
    {
        if (date == null)
        {
            return null;
        }
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTime();
    }
    
    public static Date getPreviousMonday()
    {
        Calendar cd = Calendar.getInstance();
        
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        Date date;
        if (dayOfWeek == 1)
            date = addDays(cd.getTime(), -7);
        else
        {
            date = addDays(cd.getTime(), -6 - dayOfWeek);
        }
        return getStartOfDate(date);
    }
    
    public static Date getMondayBefore4Week()
    {
        Calendar cd = Calendar.getInstance();
        
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        Date date;
        if (dayOfWeek == 1)
            date = addDays(cd.getTime(), -28);
        else
        {
            date = addDays(cd.getTime(), -27 - dayOfWeek);
        }
        return getStartOfDate(date);
    }
    
    public static Date getCurrentMonday()
    {
        Calendar cd = Calendar.getInstance();
        
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        Date date;
        if (dayOfWeek == 1)
            date = cd.getTime();
        else
        {
            date = addDays(cd.getTime(), 1 - dayOfWeek);
        }
        return getStartOfDate(date);
    }
    
    public static Date getEndOfMonth(Date date)
    {
        if (date == null)
        {
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(2) + 1);
        calendar.set(Calendar.DATE, 0);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 001);
        return new Date(calendar.getTimeInMillis());
    }
    
    public static Date getFirstOfMonth(Date date)
    {
        Date lastMonth = addMonths(date, -1);
        
        lastMonth = getEndOfMonth(lastMonth);
        return addDays(lastMonth, 1);
    }
    
    public static Date getWeekBegin(Date date)
    {
        if (date == null)
        {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        int dw = cal.get(Calendar.DAY_OF_WEEK);
        while (dw != 1)
        {
            cal.add(5, -1);
            dw = cal.get(Calendar.DAY_OF_WEEK);
        }
        return cal.getTime();
    }
    
    public static Date getWeekEnd(Date date)
    {
        if (date == null)
        {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        int dw = cal.get(Calendar.DAY_OF_WEEK);
        while (dw != 1)
        {
            cal.add(5, 1);
            dw = cal.get(Calendar.DAY_OF_WEEK);
        }
        return cal.getTime();
    }
    
    public static boolean inFormat(String sourceDate, String format)
    {
        if ((sourceDate == null) || (StringUtils.isBlank(format)))
        {
            return false;
        }
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        }
        catch (Exception e)
        {
            LoggerManager.error(DateUtility.class,
                e,
                "inFormat catch exception , string date = {}",
                sourceDate);
        }
        return false;
    }
    
    public static int getNumberOfMonthsBetween(Date begin, Date end)
    {
        if ((begin == null) || (end == null))
        {
            return -1;
        }
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(begin);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        return (cal2.get(1) - cal1.get(1)) * 12 + (cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH));
    }
    
    public static long getNumberOfMinuteBetween(Date begin, Date end)
    {
        if ((begin == null) || (end == null))
        {
            return -1L;
        }
        
        long millisec = end.getTime() - begin.getTime();
        return millisec / 60000L;
    }
    
    public static long getNumberOfHoursBetween(Date begin, Date end)
    {
        if ((begin == null) || (end == null))
        {
            return -1L;
        }
        
        long millisec = end.getTime() - begin.getTime() + 1L;
        return millisec / 3600000L;
    }
    
    public static long getNumberOfDaysBetween(Date begin, Date end)
    {
        if ((begin == null) || (end == null))
        {
            return -1L;
        }
        
        long millisec = end.getTime() - begin.getTime();
        return millisec / 86400000L;
    }
    
    public static long getNumberOfDaysBetween(Calendar before, Calendar after)
    {
        if ((before == null) || (after == null))
        {
            return -1L;
        }
        
        before.clear(Calendar.MILLISECOND);
        before.clear(Calendar.SECOND);
        before.clear(Calendar.MINUTE);
        before.clear(Calendar.HOUR_OF_DAY);
        
        after.clear(Calendar.MILLISECOND);
        after.clear(Calendar.SECOND);
        after.clear(Calendar.MINUTE);
        after.clear(Calendar.HOUR_OF_DAY);
        
        long elapsed = after.getTime().getTime() - before.getTime().getTime();
        return elapsed / 86400000L;
    }
    
    public static Date getRemoteDate(String url)
    {
        if (url == null)
        {
            return null;
        }
        
        try
        {
            URLConnection uc = new URL(url).openConnection();
            uc.connect();
            return new Date(uc.getDate());
        }
        catch (IOException e)
        {
            LoggerManager.error(DateUtility.class,
                e,
                "getRemoteDate catch exception , string url = {}",
                url);
        }
        return new Date();
    }
    
    public static Calendar getCurDateCeil()
    {
        Calendar cal = new GregorianCalendar();
        return new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5));
    }
    
    public static Calendar getCurDateFloor()
    {
        Calendar cal = new GregorianCalendar();
        return new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5), 23, 0);
    }
    
    public static Date getWeekBegin(Calendar tmp)
    {
        if (tmp == null)
        {
            return null;
        }
        
        Calendar ctmp = new GregorianCalendar(tmp.get(1), tmp.get(2), tmp.get(5));
        
        int dw = ctmp.get(Calendar.DAY_OF_WEEK);
        while (dw != 2)
        {
            ctmp.add(Calendar.DAY_OF_MONTH, -1);
            dw = ctmp.get(Calendar.DAY_OF_WEEK);
        }
        return ctmp.getTime();
    }
    
    public static Date getWeekEnd(Calendar tmp)
    {
        if (tmp == null)
        {
            return null;
        }
        
        Calendar ctmp = new GregorianCalendar(tmp.get(1), tmp.get(2), tmp.get(5), 23, 0);
        
        int dw = ctmp.get(Calendar.DAY_OF_WEEK);
        while (dw != 1)
        {
            ctmp.add(Calendar.DAY_OF_MONTH, 1);
            dw = ctmp.get(Calendar.DAY_OF_WEEK);
        }
        return ctmp.getTime();
    }
    
    public static Date getYearBegin(Date date, int month)
    {
        if (date == null)
        {
            return null;
        }
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }
    
    public static Date getYearEnd(Date date, int month)
    {
        if (date == null)
        {
            return null;
        }
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 11);
        cal.add(Calendar.MONTH, month);
        return getEndOfMonth(cal.getTime());
    }
    
    public static Date getDate(int year, int month, int day)
    {
        GregorianCalendar d = new GregorianCalendar(year, month - 1, day);
        return d.getTime();
    }
    
    public static Date parseLong(long time)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getTime();
    }
    
    public static Date getYesterday(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //-1今天的时间减一天
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
    
    public static Date getTomorrow(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //+1今天的时间加一天
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTime();
    }
    
    public static Date setTime(Date date, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }
    
    /**
     * 毫秒值转String
     * @param timeMillis
     * @param format 默认为yyyy-MM-dd HH:mm:ss
     * @return 按格式输出的string
     */
    public static String parseLongToString(Long timeMillis, String format)
    {
        if (null == timeMillis)
        {
            return null;
        }
        
        if (StringUtility.isEmpty(format))
        {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        
        return formatDate(new Date(timeMillis), format);
    }
    
    /**
     * 毫秒值转String
     * @param timeMillis
     * @return 按yyyy-MM-dd HH:mm:ss格式输出的string
     */
    public static String parseLongToString(Long timeMillis)
    {
        if (null == timeMillis)
        {
            return null;
        }
        
        String format = YYYY_MM_DD_HH_MM_SS;
        
        return formatDate(new Date(timeMillis), format);
    }
    
    /**
     * 获取本月天数
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getCurrentMonthDate()
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.getActualMaximum(Calendar.DATE);
    }
    
    public static boolean isBeginningOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }
}
