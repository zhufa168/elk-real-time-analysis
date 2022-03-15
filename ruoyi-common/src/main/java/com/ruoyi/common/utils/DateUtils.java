package com.ruoyi.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getDate(String ts) {
        return parseDateToStr(YYYY_MM_DD,parseDate(ts));
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String getTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     *  当前日期减去多少天
     * @param day
     * @return
     */
    public static String dateCalcTime(int day){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
        return  simpleDateFormat.format(calendar.getTime());
    }
    /**
     *  设置的日期减去多少天
     * @param day
     * @return
     */
    public static String dateCalcTime(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
        return  simpleDateFormat.format(calendar.getTime());
    }

    /**
     *  设置的日期减去多少天
     * @param date,minute
     * @return
     */
    public static String dateCalcTimeMil(Date date,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,minute);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
        return  simpleDateFormat.format(calendar.getTime());
    }

    /**
     *  设置的日期减去多少天
     * @param date
     * @return
     */
    public static String dateCalcTimeMil(String date,int minute){
        Date date1 = parseDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.MINUTE,minute);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
        return  simpleDateFormat.format(calendar.getTime());
    }

    /**
     *  设置的日期减去多少天
     * @param date
     * @return
     */
    public static long dateCalcTimeStamp(Date date,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,minute);
        return  calendar.getTimeInMillis();
    }

    /**
     *  设置的日期减去多少天
     * @param date
     * @return
     */
    public static String getdateCalcTimeMil(String date,int minute){
        Date date1 = parseDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.MINUTE,minute);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return  simpleDateFormat.format(calendar.getTime());
    }

    /**
     *  设置的日期减去多少天
     * @param date
     * @return
     */
    public static String stringTimetoUTC(String date){
        Date date1 = parseDate(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
        return  simpleDateFormat.format(date1);
    }
    
    
    public static boolean differentDays(Date startDate,Date endDate){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        //不是同一天
        if(day1 != day2) return true;
        return false;
    }
    

    
}
