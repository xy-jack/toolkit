package com.yd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *  日期工具类
 */
public class DateUtil {

	/**
	 * HHmmss日期格式化样式
	 */
	public static final String HHmmss = "HHmmss";
	/**
	 * yyyyMMdd日期格式化工具
	 */
	public static final SimpleDateFormat DF_HHmmss = new SimpleDateFormat(
			HHmmss);
	/**
	 * yyyyMMdd日期格式化样式
	 */
	public static final String yyyyMMdd = "yyyyMMdd";

	/**
	 * yyyy-MM-dd日期格式化样式
	 */
	public static final String yyyy_MM_dd = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd日期格式化样式
	 */
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * yyyyMMddHHmmss日期格式化样式
	 */
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	/**
	 * yyyyMMddHHmmssSSS日期格式化样式
	 */
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	
	/**
	 * yyyyMMdd日期格式化工具
	 */
	public static final SimpleDateFormat DF_yyyyMMdd = new SimpleDateFormat(
			yyyyMMdd);

	public static final SimpleDateFormat DF_LONG_yyyyMMddHHmmss = new SimpleDateFormat(
			yyyy_MM_dd_HH_mm_ss);

	public static final SimpleDateFormat DF_SHORT_yyyyMMddHHmmss = new SimpleDateFormat(
			yyyyMMddHHmmss);
	
	public static final SimpleDateFormat DF_SHORT_yyyyMMddHHmmssSSS = new SimpleDateFormat(
			yyyyMMddHHmmssSSS);
	/**
	 * 计算指定日期加减天数后的日期
	 * 
	 * @param inputDate
	 *            '20080301'
	 * @param dateFormat
	 *            日期格式，'yyyyMMdd' 参考SimpleDateFormat
	 * @param amount
	 *            数量
	 */
	public static String addDay(String inputDate, String dateFormat, int amount) {

		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = sdf.parse(inputDate);
			calendar.setTime(date);
			// calendar.add(field, amount);
			calendar.add(Calendar.DAY_OF_MONTH, amount);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			System.out.println("DateUtils.addDay异常：" + e);
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date addDay(Date inputDate, int amount) {

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);
			calendar.add(Calendar.DAY_OF_MONTH, amount);
			return calendar.getTime();
		} catch (Exception e) {
			System.out.println("DateUtils.addDay异常：" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算指定日期加减月数后的日期
	 * 
	 * @param inputDate
	 *            '20080301'
	 * @param dateFormat
	 *            日期格式，'yyyyMMdd' 参考SimpleDateFormat
	 * @param amount
	 *            数量
	 */
	public String addMonth(String inputDate, String dateFormat, int amount) {

		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = sdf.parse(inputDate);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, amount);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			System.out.println("DateUtils.addMonth异常：" + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算指定日期加减年数后的日期
	 * 
	 * @param inputDate
	 *            '20080301'
	 * @param dateFormat
	 *            日期格式，'yyyyMMdd' 参考SimpleDateFormat
	 * @param amount
	 *            数量
	 */
	public String addYear(String inputDate, String dateFormat, int amount) {

		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = sdf.parse(inputDate);
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, amount);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			System.out.println("DateUtils.addMonth异常：" + e);
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isExpired(String timeStamp, long diffSeconds) {
		long curTime = System.currentTimeMillis();
		long lastTime = 0L;
		try {
			lastTime = Long.parseLong(timeStamp);
		} catch (Exception e) {
		}
		long diff = (curTime - lastTime) / 1000; // 转换为秒
		return diff <= diffSeconds;
	}

	/**
	 * 把Date转换为缺省的日期格式字串，缺省的字转换格式为yyyy-MM-dd HH:mm:ss 如：2004-10-10 20:12:10
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 根据特定的Pattern,把Date转换为相应的日期格式字符串 如果日期参数为空，返回无日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 以缺省的yyyy-MM-dd HH:mm:ss格式转换字符串为Date，若转换成功，则返回相应的Date对象 若转换失败，则返回null。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseString(String dateStr) {
		return parseString(dateStr, yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 以特定的格式转换字符串为Date，若转换成功，则返回相应的Date对象 若转换失败，则返回null。
	 * 
	 * @param dateStr
	 * @return
	 */
	/**
	 * 提取当前时间的星期数，7是星期天，1是星期一，如此类推
	 * 
	 * @return 序数
	 */
	public static int getCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		week = week - 1;
		if (week == 0) {
			week = 7;
		}
		return week;
	}

	/**
	 * 提取当前的星期数的中文字串，星期一...星期日
	 * 
	 * @return
	 */
	public static String getCurrentWeekStr() {
		return getWeekStr(new Date());
	}

	/**
	 * 提取当前时间的年份
	 * 
	 * @return 年份
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 提取当前时间的月份,1:一月份 12：12月份
	 * 
	 * @return 月份
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 提取当前时间的日期
	 * 
	 * @return 日期
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	public static String getWeekStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		week = week - 1;
		String weekStr = "";
		switch (week) {
		case 0:
			weekStr = "星期日";
			break;
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
		}
		return weekStr;
	}

	public static Date parseString(String dateStr, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			if (!"".equals(dateStr)) {
				return dateFormat.parse(dateStr);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getBeforeDay(int day, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		Date date = calendar.getTime();
		return formatDate(date, pattern);
	}

	/**
	 * 判断当前是否是工作时间，周六，周天，工作日9:00-11:30 13:00-17:30 以外为工作时间
	 * 
	 * @return flag
	 */
	public static boolean isWorkTime() {
		boolean flag = true;
		Calendar calendar = Calendar.getInstance();
		long nowTime = calendar.getTimeInMillis();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 7 || dayOfWeek == 1) {
			flag = false;
			return flag;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long monitorBeginTime = calendar.getTimeInMillis();
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		long monitorEndTime = calendar.getTimeInMillis();
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long afertnoonBeginTime = calendar.getTimeInMillis();
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		long afertnoonEndTime = calendar.getTimeInMillis();
		if ((nowTime > monitorBeginTime && nowTime < monitorEndTime)
				|| (nowTime > afertnoonBeginTime && nowTime < afertnoonEndTime)) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public static String formatDate(String date) {
		if (date == null || date.length() != 8) {
			return date;
		} else {
			String yyyy = date.substring(0, 4);
			String mm = date.substring(4, 6);
			String dd = date.substring(6, 8);
			return yyyy + "-" + mm + "-" + dd;
		}
	}

	public static String formatDateTime2(String dateTime) {
		if (dateTime != null && dateTime.length() >= 8) {
			String formatDateTime = dateTime.replaceAll("-", "");
			formatDateTime = formatDateTime.replaceAll(":", "");
			String date = formatDateTime.substring(0, 8);
			String time = formatDateTime.substring(8).trim();// 从第8位开始截取，直到遇到空格为止

			return date + time;
		} else {
			return "";
		}
	}
	/*
	 * 获取当前工作日
	 */
//	public static String getWorkDayNow(){
//		try {
//			QueryManager manager = (QueryManager)SpringContextUtil.getBean("queryManager");
//			String workDateNow = manager.getWorkDateNow("022");
//			return workDateNow;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("查询当前工作日异常",e);
//		}
//		 return null;
//	}
	
	public static Date getNowDate() {
        Date date = new Date();
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            date1 = sdf.parse(dateStr);
        } catch (Exception e) {

        }
        return date1;
    }
	
	 public String getStrNowDate() {
	        Date date = new Date();
	        String dateStr = null;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            dateStr = sdf.format(date);
	            // date1 = sdf.parse(dateStr);
	        } catch (Exception e) {

	        }
	        return dateStr;
	    }
	
	public static void main(String[] args) {
		System.out.println(isWorkTime());
	}
}
