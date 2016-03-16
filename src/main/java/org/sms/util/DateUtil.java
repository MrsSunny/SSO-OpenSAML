package org.sms.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.GregorianCalendar;

/**
 * @author Sunny
 */
public class DateUtil {

  public static long getEnumtime(int systemKey) {
    switch (systemKey) {
    case 1:
      return DateUtil.getFirstDayOfMonth();
    case 2:
      return DateUtil.getDefaultDay();
    case 3:
      return DateUtil.getNextMonthFirst();
    case 4:
      return DateUtil.getNextMonthEnd();
    case 5:
      return DateUtil.getCurrentQuarterStartTime();
    case 6:
      return DateUtil.getCurrentQuarterEndTime();
    case 7:
      return DateUtil.getNextCurrentQuarterStartTime();
    case 8:
      return DateUtil.getNextCurrentQuarterEndTime();
    case 9:
      return DateUtil.getCurrentYearFirst();
    case 10:
      return DateUtil.getCurrYearLast();
    default:
      throw new RuntimeException("cant't find the systemKey");
    }
  }

  /**
   * 得到二个日期间的间隔天数
   */
  public static String getTwoDay(String sj1, String sj2) {
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    long day = 0;
    try {
      java.util.Date date = myFormatter.parse(sj1);
      java.util.Date mydate = myFormatter.parse(sj2);
      day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    } catch (Exception e) {
      return "";
    }
    return day + "";
  }

  /**
   * 将短时间格式字符串转换为时间 yyyy-MM-dd
   * 
   * @param strDate
   * @return
   */
  public static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  /**
   * 两个时间之间的天数
   *
   * @param date1
   * @param date2
   * @return
   */
  public static long getDays(String date1, String date2) {
    if (date1 == null || date1.equals(""))
      return 0;
    if (date2 == null || date2.equals(""))
      return 0;
    // 转换为标准时间
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = null;
    java.util.Date mydate = null;
    try {
      date = myFormatter.parse(date1);
      mydate = myFormatter.parse(date2);
    } catch (Exception e) {
    }
    long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    return day;
  }

  // 计算当月最后一天,返回字符串
  public static long getDefaultDay() {
    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
    lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
    return lastDate.getTimeInMillis();
  }

  // 上月第一天
  public String getPreviousMonthFirst() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
    // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获取当月第一天
  public static long getFirstDayOfMonth() {
    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    return lastDate.getTimeInMillis();
  }

  // 获取当天时间
  public String getNowTime(String dateformat) {
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
    String hehe = dateFormat.format(now);
    return hehe;
  }

  // 获得当前日期与本周日相差的天数
  private int getMondayPlus() {
    Calendar cd = Calendar.getInstance();
    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
    if (dayOfWeek == 1) {
      return 0;
    } else {
      return 1 - dayOfWeek;
    }
  }

  // 获得下周星期日的日期
  public String getNextSunday() {
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得上月最后一天的日期
  public String getPreviousMonthEnd() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, -1);// 减一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得下个月第一天的日期
  public static long getNextMonthFirst() {
    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, 1);// 减一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    return lastDate.getTimeInMillis();
  }

  // 获得下个月最后一天的日期
  public static long getNextMonthEnd() {
    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, 1);// 加一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
    return lastDate.getTimeInMillis();
  }

  // 获得明年最后一天的日期
  public String getNextYearEnd() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.YEAR, 1);// 加一个年
    lastDate.set(Calendar.DAY_OF_YEAR, 1);
    lastDate.roll(Calendar.DAY_OF_YEAR, -1);
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得明年第一天的日期
  public String getNextYearFirst() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.YEAR, 1);// 加一个年
    lastDate.set(Calendar.DAY_OF_YEAR, 1);
    str = sdf.format(lastDate.getTime());
    return str;

  }

  private static int getYearPlus() {
    Calendar cd = Calendar.getInstance();
    int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
    cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
    cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
    int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
    if (yearOfNumber == 1) {
      return -MaxYear;
    } else {
      return 1 - yearOfNumber;
    }
  }

  // 获得本年第一天的日期
  public static long getCurrentYearFirst() {
    int yearPlus = getYearPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, yearPlus);
    return currentDate.getTimeInMillis();
  }

  // 获得本年最后一天的日期 *
  public String getCurrentYearEnd() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
    String years = dateFormat.format(date);
    return years + "-12-31";
  }

  /**
   * 获取某年最后一天日期
   *
   * @param year
   *          年份
   * @return Date
   */
  public static long getCurrYearLast() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
    String years = dateFormat.format(date);
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, Integer.parseInt(years));
    calendar.roll(Calendar.DAY_OF_YEAR, -1);
    return calendar.getTimeInMillis();
  }

  public static long getCurrentQuarterStartTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 1;
    try {
      if (currentMonth >= 1 && currentMonth <= 3)
        c.set(Calendar.MONTH, 0);
      else if (currentMonth >= 4 && currentMonth <= 6)
        c.set(Calendar.MONTH, 3);
      else if (currentMonth >= 7 && currentMonth <= 9)
        c.set(Calendar.MONTH, 6);
      else if (currentMonth >= 10 && currentMonth <= 12)
        c.set(Calendar.MONTH, 9);
      c.set(Calendar.DATE, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c.getTimeInMillis();
  }

  public static long getNextCurrentQuarterStartTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 3;
    try {
      if (currentMonth >= 1 && currentMonth <= 3)
        c.set(Calendar.MONTH, 0);
      else if (currentMonth >= 4 && currentMonth <= 6)
        c.set(Calendar.MONTH, 3);
      else if (currentMonth >= 7 && currentMonth <= 9)
        c.set(Calendar.MONTH, 6);
      else if (currentMonth >= 10 && currentMonth <= 12)
        c.set(Calendar.MONTH, 9);
      c.set(Calendar.DATE, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c.getTimeInMillis();
  }

  /**
   * 当前季度的结束时间，即2012-03-31 23:59:59
   *
   * @return
   */
  public static long getCurrentQuarterEndTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 1;
    try {
      if (currentMonth >= 1 && currentMonth <= 3) {
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 31);
      } else if (currentMonth >= 4 && currentMonth <= 6) {
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 7 && currentMonth <= 9) {
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 10 && currentMonth <= 12) {
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DATE, 31);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c.getTimeInMillis();
  }

  public static long getNextCurrentQuarterEndTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 3;
    try {
      if (currentMonth >= 1 && currentMonth <= 3) {
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 31);
      } else if (currentMonth >= 4 && currentMonth <= 6) {
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 7 && currentMonth <= 9) {
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 10 && currentMonth <= 12) {
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DATE, 31);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c.getTimeInMillis();
  }

  // Date or Time calculate use Java8 api.
  /**
   * Get the Month for specify timestamp.
   * 
   * @param timestamp
   *          A timestamp need to calculate.
   * @return
   */
  public static int getMonthOfMills(long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    return dateTime.getMonthValue();
  }

  /**
   * Get the first day timestamp in the same month for specify timestamp. That
   * mean the result is the first Mills second in the specify month.
   * 
   * @param timestamp
   *          A timestamp need to calculate.
   * @return
   */
  public static long getMonthHeadMills(long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    // Get the first day of this month.
    ZonedDateTime firstDateTime = ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), 1, 0, 0, 0, 0, ZoneId.systemDefault());
    return firstDateTime.toInstant().toEpochMilli();
  }

  /**
   * Get the last day timestamp in the same month for specify timestamp. That
   * mean the result is the last Mills second in the specify month.
   * 
   * @param timestamp
   *          A timestamp need to calcute.
   * @return
   */
  public static long getMonthTailMills(long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    // Get the last day of this month. Not use Math.pow(10, 9) to calculate the
    // max nano second
    // in consideration of performance.
    ZonedDateTime lastDateTime = ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(),
        dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear()), 23, 59, 59, 1000000000 - 1, ZoneId.systemDefault());
    return lastDateTime.toInstant().toEpochMilli();
  }

  /**
   * Get the first timestamp in this day.
   * 
   * @param timestamp
   * @return
   */
  public static long getDayHeadMills(long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());

    ZonedDateTime dateHead = ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), 0, 0, 0, 0, ZoneId.systemDefault());
    return dateHead.toInstant().toEpochMilli();
  }

  /**
   * Get the last timestamp in this day.
   * 
   * @param timestamp
   * @return
   */
  public static long getDayTailMills(long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    ZonedDateTime dateHead = ZonedDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), 23, 59, 59, 1000000000 - 1,
        ZoneId.systemDefault());
    return dateHead.toInstant().toEpochMilli();
  }

  public static long getSpecifiedDayAfter(int afterDay) {
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DATE);
    c.set(Calendar.DATE, day + afterDay);
    return c.getTimeInMillis();
  }
}