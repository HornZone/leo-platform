package com.leo.platform.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    // 默认显示日期的格式
    public static final String DATAFORMAT_STR_YYYY_MM_SLASH = "yyyy/MM";
    // 默认显示日期的格式 yyyy/MM/dd
    public static final String DATAFORMAT_STR_YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    // 默认显示日期时间的格式
    public static final String DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";

    // 默认显示日期的格式
    public static final String DATAFORMAT_STR_YYYY_MM_BAR = "yyyy-MM";
    // 默认显示日期的格式 yyyy-MM-dd
    public static final String DATAFORMAT_STR_YYYY_MM_DD_BAR = "yyyy-MM-dd";
    // 默认显示日期时间的格式
    public static final String DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS_BAR = "yyyy-MM-dd HH:mm:ss";

    // 默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR_YYYY_MM = "yyyy年MM月";
    // 默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR_YYYY_MM_DD = "yyyy年MM月dd日";
    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_YYYY_MM_DD_HHMMSS = "yyyy年MM月dd日 HH:mm:ss";
    // 默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 日期格式 yyyyMMdd
     */
    public static final String DAY_FORMAT_yyyyMMdd = "yyyyMMdd";
    /**
     * 时间格式 HHmmss
     */
    public static final String TIME_FORMAT_HHmmss = "HHmmss";

    @SuppressWarnings("deprecation")
    public static int getMonthDiff(Date startDate, Date endDate) {
        int result = 0;

        int yeardiff = endDate.getYear() - startDate.getYear();
        int nonthdiff = endDate.getMonth() - startDate.getMonth();
        result = yeardiff * 12 + nonthdiff;
        return result;
    }

    /**
     * 获取本周一0点的日期
     * 
     * @return
     */
    public static Date getThisWeekFirstDayDate() {
        Date result;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        result = c.getTime();
        return result;
    }

    /**
     * 日期 -> 字符串（格式化日期）
     * 
     * @author bl00252
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (date == null)
            return "";
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ymdhmsFormat.format(date);
    }

    /**
     * 日期 -> 字符串（格式化日期）
     * 
     * @author bl00252
     * @param date
     * @return
     */
    public static String dateToStr(Date date, String format) {
        if (date == null)
            return "";
        DateFormat ymdhmsFormat = new SimpleDateFormat(format);
        return ymdhmsFormat.format(date);
    }

    /**
     * 日期 -> 字符串（格式化日期） for JXL JXL解析出来的日期时区是GMT，需要转化为本地时区，不然差8个小时
     * 
     * @author bl00252
     * @param date
     * @return
     */
    public static String dateToStrForJxl(Date date, String format) {
        if (date == null)
            return "";
        try {
            date = convertDate4JXL(date);
        } catch (ParseException e) {
            logger.warn("转换为日期字符串出错", e);
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(format);
        ymdhmsFormat.setTimeZone(TimeZone.getDefault());
        return ymdhmsFormat.format(date);
    }

    /**
     * JXL中通过DateCell.getDate()获取单元格中的时间为（实际填写日期+8小时），原因是JXL是按照GMT时区来解析XML。本方法用于获取单元格中实际填写的日期！
     * 例如单元格中日期为“2009-9-10”，getDate得到的日期便是“Thu Sep 10 08:00:00 CST 2009”；单元格中日期为“2009-9-10 16:00:00”，getDate得到的日期便是“Fri
     * Sep 11 00:00:00 CST 2009”
     * 
     * @param jxlDate 通过DateCell.getDate()获取的时间
     * @return
     * @throws ParseException
     */

    public static Date convertDate4JXL(Date jxlDate) throws ParseException {

        if (jxlDate == null)
            return null;

        TimeZone gmt = TimeZone.getTimeZone("GMT");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        dateFormat.setTimeZone(gmt);

        String str = dateFormat.format(jxlDate);

        TimeZone local = TimeZone.getDefault();

        dateFormat.setTimeZone(local);

        return dateFormat.parse(str);

    }

    /**
     * 字符串 -> 日期
     * 
     * @author bl00252
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String str) {
        try {
            DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return ymdhmsFormat.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转换为日期
     * 
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String str, String format) {
        try {
            DateFormat ymdhmsFormat = new SimpleDateFormat(format);
            return ymdhmsFormat.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date autoFormatDate(String dateString, String format) {
        Date result = null;
        try {
            if (isDateFormatOk(dateString, format)) {
                result = strToDate(dateString, format);
            } else {
                format = getFormateStr(dateString);
                result = strToDate(dateString, format);
            }
        } catch (Exception e) {
            logger.warn("转换为日期出错", e);
        }
        return result;
    }

    /**
     * 判断字符日期格式是否正确
     * 
     * @param str
     * @param format
     * @return
     */
    public static boolean isDateFormatOk(String str, String format) {
        try {
            DateFormat ymdhmsFormat = new SimpleDateFormat(format);
            ymdhmsFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 设置date的时间为当天的结束时间, 即23:59:59
     * 
     * @param date
     * @return
     */
    public static Date setTimeToTheEndOfTheDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 设置date的时间为当天的开始时间, 即00:00:00
     * 
     * @param date
     * @return
     */
    public static Date setTimeToTheStartOfTheDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 设置date的时间为当月的开始时间
     * 
     * @param date
     * @return
     */
    public static Date setTimeToTheStartOfTheMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 设置date的时间为当月的结束时间
     * 
     * @param date
     * @return
     */
    public static Date setTimeToTheEndOfTheMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));

        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 根据一个日期字符串，返回日期格式，目前支持4种 如果都不是，则返回null
     * 
     * @param DateString
     * @return
     */
    public static String getFormateStr(String DateString) {
        // 默认显示日期的格式
        String pattern_yyyy_mm_slash = "\\d{4}\\/\\d{1,2}"; // yyyy/MM
        // 默认显示日期的格式
        String pattern_yyyy_mm_dd_slash = "\\d{4}\\/\\d{1,2}\\/\\d{1,2}"; // yyyy/MM/dd
        // 默认显示日期时间的格式
        String pattern_4y_mm_dd_hhmmss_slash = "\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}"; // yyyy/MM/dd
                                                                                                            // HH:mm:ss;

        // 默认显示日期的格式
        String pattern_yyyy_mm_bar = "\\d{4}-\\d{1,2}"; // yyyy-MM
        // 默认显示日期的格式
        String pattern_yyyy_mm_dd_bar = "\\d{4}-\\d{1,2}-\\d{1,2}"; // yyyy-MM-dd
        // 默认显示日期时间的格式
        String pattern_4y_mm_dd_hhmmss_bar = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}"; // yyyy-MM-dd
                                                                                                      // HH:mm:ss;

        // 默认显示简体中文日期的格式
        String zhcn_pattern_4y_mm = "\\d{4}年\\d{1,2}月";
        // 默认显示简体中文日期的格式
        String zhcn_pattern_4y_mm_dd = "\\d{4}年\\d{1,2}月\\d{1,2}日";// yyyy年MM月dd日
        // 默认显示简体中文日期的格式
        String zhcn_pattern_4y_mm_dd_hhmmss = "\\d{4}年\\d{1,2}月\\d{1,2}日\\s\\d{1,2}:\\d{1,2}:\\d{1,2}";// yyyy年MM月dd日
        // 默认显示简体中文日期时间的格式
        String zhcn_pattern_4y_mm_dd_hh_mm_ss = "\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒";// yyyy年MM月dd日HH时mm分ss秒

        // yyyy/MM
        Pattern p = Pattern.compile(pattern_yyyy_mm_slash);
        Matcher m = p.matcher(DateString);
        boolean b = m.matches();
        if (b)
            return DATAFORMAT_STR_YYYY_MM_SLASH;
        // yyyy/MM/dd
        p = Pattern.compile(pattern_yyyy_mm_dd_slash);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATAFORMAT_STR_YYYY_MM_DD_SLASH;
        // yyyy/MM/dd HH:mm:ss
        p = Pattern.compile(pattern_4y_mm_dd_hhmmss_slash);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS_SLASH;

        // yyyy-MM
        p = Pattern.compile(pattern_yyyy_mm_bar);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATAFORMAT_STR_YYYY_MM_BAR;
        // yyyy-MM-dd
        p = Pattern.compile(pattern_yyyy_mm_dd_bar);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATAFORMAT_STR_YYYY_MM_DD_BAR;
        // yyyy-MM-dd HH:mm:ss
        p = Pattern.compile(pattern_4y_mm_dd_hhmmss_bar);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS_BAR;

        // yyyy年MM月
        p = Pattern.compile(zhcn_pattern_4y_mm);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATAFORMAT_STR_YYYY_MM;
        // yyyy年MM月dd日
        p = Pattern.compile(zhcn_pattern_4y_mm_dd);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATAFORMAT_STR_YYYY_MM;
        // yyyy年MM月dd日 HH:mm:ss
        p = Pattern.compile(zhcn_pattern_4y_mm_dd_hhmmss);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATATIMEF_STR_YYYY_MM_DD_HHMMSS;
        // yyyy年MM月dd日HH时mm分ss秒
        p = Pattern.compile(zhcn_pattern_4y_mm_dd_hh_mm_ss);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATATIMEF_STR_YYYY_MM_DD_HH_MM_SS;

        return DATAFORMAT_STR_YYYY_MM_DD_SLASH;
    }

    /**
     * 解析格式 hh:mm 的时间字符串为 分钟数
     */
    public static int parseHHMM2Minutes(String hhmmStr) {
        Pair<Integer, Integer> hhmm = parseHHMM(hhmmStr);
        return hhmm.getLeft().intValue() * 60 + hhmm.getRight().intValue();
    }

    /**
     * 解析格式hh:mm 时间串为两个数字
     * 
     * @param hhmmStr
     * @return
     */
    public static Pair<Integer, Integer> parseHHMM(String hhmmStr) {
        String[] parts = StringUtils.split(hhmmStr, ":");
        Preconditions.checkArgument(parts.length == 2, "时间格式不规范，不能解析");
        Preconditions.checkArgument(NumberUtils.isDigits(parts[0]), "时间格式不规范，存在非数值字符");
        Preconditions.checkArgument(NumberUtils.isDigits(parts[1]), "时间格式不规范，存在非数值字符");
        Pair<Integer, Integer> hhmm = Pair.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        Preconditions.checkState(hhmm.getLeft() >= 0 && hhmm.getLeft() <= 23, "时间格式不规范，小时数值超过访问");
        Preconditions.checkState(hhmm.getRight() >= 0 && hhmm.getRight() <= 59, "时间格式不规范，分钟数值超过访问");
        return hhmm;
    }

    /**
     * 获取当天hh:mm时间
     * 
     * @param hhmmStr
     * @param accordingTo
     * @return
     */
    public static Calendar getCalendarByHHMM(String hhmmStr, Date accordingTo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(accordingTo);
        calendar.set(Calendar.SECOND, 0);
        Pair<Integer, Integer> hhmm = DateUtil.parseHHMM(hhmmStr);
        calendar.set(Calendar.HOUR_OF_DAY, hhmm.getLeft());
        calendar.set(Calendar.MINUTE, hhmm.getRight());
        return calendar;
    }

    /**
     * 比较两个时间
     */
    public static int compare_date(Date Date1, Date Date2) {
        if (Date1.getTime() > Date2.getTime()) {
            return 1;
        } else if (Date1.getTime() < Date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int compareDateToMinute(Date date1, Date date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dt1 = df.format(date1);
        String dt2 = df.format(date2);
        return dt1.compareTo(dt2);
    }

    /**
     * 输入一个日期值，一个格式化规则，格式化为一个日期字符串
     */
    public static String dateFormate(Date dateTime, String formate) {
        java.text.DateFormat DateFormate = new java.text.SimpleDateFormat(formate);
        return DateFormate.format(dateTime);
    }

    public static void main(String[] args) {
        String DATE1 = "1995-11-12 15:21";
        String DATE2 = "1999-12-11 09:59";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);

            System.out.println(DateUtil.compare_date(dt1, dt2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
