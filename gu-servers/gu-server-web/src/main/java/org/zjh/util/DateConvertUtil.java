package org.zjh.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateUtil;

public class DateConvertUtil {
  
  /**
   * 获取昨天日期
   * @return
   */
  public static String getYesterdayDate(Integer day, String pattern) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, day);
    return DateUtil.format(calendar.getTime(), pattern);
  }
  
  /**
   * 获取昨天日期加时间
   * @return
   */
  public static Date getYesterdayDateTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -1);
    
    return calendar.getTime();
  }
  
  /**
   * 获取当天时间
   * @return
   */
  public static String getDateTimeNow(String pattern) {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(new Date());
  }
  
  /**
   * 获取昨天日期加时间
   * @return
   */
  public static Date addDateTime(int d) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, d);
    
    return calendar.getTime();
  }
  
}
