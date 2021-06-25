/*
 * This file is created by shilei on 2004-11-25.
 * Everyone modified at please keep the history right.
 *

 */
package com.bench.lang.base.date.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import org.apache.commons.lang3.math.NumberUtils;

import com.bench.lang.base.string.utils.StringUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final DateUtils INSTANCE = new DateUtils();

	public final static long ONE_DAY_SECONDS = 86400;

	/*
	 * private static DateFormat dateFormat = null; private static DateFormat longDateFormat = null; private static DateFormat dateWebFormat = null;
	 */
	public final static String TIME_ZONE = "GMT";
	public final static String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static String shortFormat = "yyyyMMdd";
	public final static String shortFormat3 = "yyyy/MM/dd";
	public final static String yyyyMMddHH = "yyyyMMddHH";
	public final static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public final static String yearOnlyFormat = "yyyy";
	public final static String monthOnlyFormat = "MM";
	public final static String dayOnlyFormat = "dd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String longFormatExtra = "yyyyMMddHHmmssSSS";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String webFormat2 = "yyyy.MM.dd";
	public final static String timeFormat = "HHmmss";
	public final static String shortTimeFormat = "MM-dd HH:mm";
	public final static String shortDateFormat = "MM-dd";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseShortTimeFormat = "MM月dd日 HH:mm";
	public final static String chineseDtFormat = "yyyy年MM月dd日";
	public final static String chineseDtFormat2 = "yyyy年MM月dd日 HH:mm";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";

	public final static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	public final static String newFormat2 = "yyyy-M-d H:m:s";
	public final static String newFormat3 = "yyyy/MM/dd HH:mm:ss";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_WEEK_HH_MM = "yyyy-MM-dd E HH:mm";

	public static final String YYYY_MM_DD_WEEK_HH_MM_SS = "yyyy-MM-dd E HH:mm:ss";

	public final static String HH_MM = "HH:mm";

	public final static long ONE_DAY_MILL_SECONDS = 86400000;

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * 是否今天日期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Date now = new Date();
		return now.getYear() == date.getYear() && now.getMonth() == date.getMonth() && now.getDate() == date.getDate();
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static String getYear(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(yearOnlyFormat);
		return getDateString(date, dateFormat);
	}

	/**
	 * 获取当前小时
	 * 
	 * @param date
	 * @return
	 */
	public static String getHour() {
		return getHour(new Date());
	}

	/**
	 * 获取小时
	 * 
	 * @param date
	 * @return
	 */
	public static String getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour < 10 ? "0" + hour : Integer.toString(hour);
	}

	/**
	 * 获取当前分钟
	 * 
	 * @param date
	 * @return
	 */
	public static String getMinute() {
		return getMinute(new Date());
	}

	/**
	 * 获取分钟
	 * 
	 * @param date
	 * @return
	 */
	public static String getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.MINUTE);
		return hour < 10 ? "0" + hour : Integer.toString(hour);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat(monthOnlyFormat);
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(monthOnlyFormat);
		return getDateString(date, dateFormat);
	}

	/**
	 * 获取当前几号
	 * 
	 * @return
	 */
	public static String getDay() {
		DateFormat dateFormat = new SimpleDateFormat(dayOnlyFormat);
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * 获取几号
	 * 
	 * @param date
	 * @return
	 */
	public static String getDay(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(dayOnlyFormat);
		return getDateString(date, dateFormat);
	}

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		df.setLenient(false);
		return df;
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDateNoTime3(String sDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(shortFormat3);

			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,str=" + sDate, e);
		}
	}

	public static Date parseDateNoTime(String sDate) {
		if (sDate == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,str=" + sDate, e);
		}
	}

	public static Date parseChineseDtFormat2(String sDate) {
		if (sDate == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(chineseDtFormat);
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,str=" + sDate, e);
		}
	}

	public static Date parseDate(String sDate, String dateFormatString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		if ((sDate == null) || (sDate.length() < dateFormatString.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * 根据某一天是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDayByDate(Date date) {
		SimpleDateFormat formatD = new SimpleDateFormat("E");
		String weekDay = formatD.format(date);
		return weekDay;
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
		sDate = sDate.replaceAll(delimit, "");

		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	public static Date parseDateNewFormat3(String sDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(newFormat3);
			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,date=" + sDate + ",format=" + newFormat3, e);
		}
	}

	public static Date parseDateNewFormat(String sDate) {
		Date d = null;
		if (sDate != null) {
			if (sDate.length() == newFormat.length()) {
				try {
					DateFormat dateFormat = new SimpleDateFormat(newFormat);
					d = dateFormat.parse(sDate);
				} catch (ParseException ex) {
					return null;
				}
			} else {
				try {
					DateFormat dateFormat = new SimpleDateFormat(newFormat2);
					d = dateFormat.parse(sDate);
				} catch (ParseException ex) {
					return null;
				}
			}
		}
		return d;
	}

	/**
	 * 计算当前时间几小时之后的时间
	 * 
	 * @param date
	 * @param hours
	 * 
	 * @return
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * 计算当前时间几分钟之后的时间
	 * 
	 * @param date
	 * @param minutes
	 * 
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * @param date1
	 * @param secs
	 * 
	 * @return
	 */

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * 判断输入的字符串是否为合法的小时
	 * 
	 * @param hourStr
	 * 
	 * @return true/false
	 */
	public static boolean isValidHour(String hourStr) {
		if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
			int hour = new Integer(hourStr).intValue();

			if ((hour >= 0) && (hour <= 23)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断输入的字符串是否为合法的分或秒
	 * 
	 * @param minuteStr
	 * 
	 * @return true/false
	 */
	public static boolean isValidMinuteOrSecond(String str) {
		if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
			int hour = new Integer(str).intValue();

			if ((hour >= 0) && (hour <= 59)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 取得新的日期
	 * 
	 * @param date1
	 *            日期
	 * @param days
	 *            天数
	 * 
	 * @return 新的日期
	 */
	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getLongDateExtraString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormatExtra);

		return getDateString(date, dateFormat);
	}

	/**
	 * 获取GMT时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getGMTStringE(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
		df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
		return df.format(date);
	}

	public static String getGMTString(Date date) {
		DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(date);
	}

	/**
	 * 返回 年-月-日 时:分:秒:毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateExtraString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
		return getDateString(date, dateFormat);
	}

	/**
	 * 返回 年-月-日 时:分:秒:毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDateExtraString(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,date=" + date + ",format=" + YYYY_MM_DD_HH_MM_SS_SSS, e);
		}
	}

	public static Date parseLongDateExtraString(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(longFormatExtra);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析日期错误,date=" + date + ",format=" + longFormatExtra, e);
		}
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getYearMonthDayWeekHourMinute(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_WEEK_HH_MM);
		return getDateString(date, dateFormat);
	}

	public static String getYearMonthDayWeekHourMinuteSecond(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_WEEK_HH_MM_SS);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * @return 当天的时间格式化为"yyyyMMdd"
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMMddHHString(Date date) {
		DateFormat df = getNewDateFormat(yyyyMMddHH);

		return df.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMMddHHmmString(Date date) {
		DateFormat df = getNewDateFormat(yyyyMMddHHmm);

		return df.format(date);
	}

	/**
	 * 当天的时间格式化为MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDateString(Date date) {
		DateFormat df = getNewDateFormat(shortDateFormat);

		return df.format(date);
	}

	/**
	 * 返回日期格式的秒
	 * 
	 * @param date
	 * @return
	 */
	public static String getHourMinute(Date date) {
		DateFormat df = getNewDateFormat(HH_MM);
		return df.format(date);
	}

	/**
	 * 得到日期的描述<br>
	 * 一分钟内的显示XX秒前<br>
	 * 
	 * 一小时内的显示XX分钟前<br>
	 * 
	 * 近两天内和一小时前的，若是今天显示：今天xx:xx<br>
	 * 若是昨天显示：昨天xx:xx<br>
	 * 
	 * 两天前，显示x月x日 xx:xx<br>
	 * 
	 * 一年前，显示xxxx年x月x日 xx:xx<br>
	 * 
	 * @param date
	 * @return
	 */
	public static String getNewDateDesc(Date date) {
		Date now = new Date();
		long seconds = getDiffSeconds(now, date);
		if (seconds < 0) {
			return "0秒前";
		}
		if (seconds < 60) {
			return seconds + "秒前";
		}
		if (seconds < 3600) {
			long minute = seconds / 60;
			seconds = seconds - minute * 60;
			if (seconds == 0) {
				return minute + "分钟前";
			} else {
				return (minute + 1) + "分钟前";
			}
		}
		int mouths = NumberUtils.toInt(getMonth(now)) - NumberUtils.toInt(getMonth(date));
		int years = NumberUtils.toInt(getYear(now)) - NumberUtils.toInt(getYear(date));
		if (mouths == 0 && years == 0) {
			long days = NumberUtils.toInt(getDay(now)) - NumberUtils.toInt(getDay(date));
			if (days == 0) {
				return "今天 " + format(date, HH_MM);
			}
			if (NumberUtils.toInt(getDay(now)) == NumberUtils.toInt(getDay(addDays(date, 1)))) {
				return "昨天 " + format(date, HH_MM);
			}
		}
		if (years < 1) {
			return format(date, chineseShortTimeFormat);
		}
		return format(date, chineseDtFormat) + " " + format(date, HH_MM);
	}

	/**
	 * 得到日期的描述
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateDesc(Date date) {
		Date now = new Date();
		long seconds = DateUtils.getDiffSeconds(now, date);
		if (seconds < 60) {
			return seconds + "秒";
		}
		if (seconds < 3600) {
			long minute = seconds / 60;
			seconds = seconds - minute * 60;
			if (seconds == 0) {
				return minute + "分";
			} else {
				return minute + "分" + seconds + "秒";
			}

		}
		if (seconds < 3600 * 12) {
			long hours = seconds / 60 / 60;
			long minutes = seconds / 60 - hours * 60;
			if (minutes == 0) {
				return hours + "小时";
			} else {
				return hours + "小时" + minutes + "分";
			}
		}
		if (DateUtils.isToday(date)) {
			return "今天" + getHourMinute(date);
		}
		return getShortTimeString(date);
	}

	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	public static String getWebDateString2(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat2);

		return getDateString(date, dateFormat);
	}

	public static String getNoSecond(Date date) {
		DateFormat dateFormat = getNewDateFormat(noSecondFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得“X年X月X日”的日期格式
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得“X年X月X日 XX:YY”的日期格式
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String getChineseDateString2(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat2);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getShortTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(shortTimeFormat);
		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得两个日期间隔秒数（日期1-日期2）
	 * 
	 * @param one
	 *            日期1
	 * @param two
	 *            日期2
	 * 
	 * @return 间隔秒数
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static long getDiffMinutes(Date one, Date two) {
		return getDiffMinutes(one, two, false);
	}

	/**
	 * 计算2个时间的差异分钟数，如果leftSecondsToMinutes为ture，且日期相差的秒数数不能被60整除(表示2个日期差，有X分Y秒) ， 则额外增加1分返回
	 * 
	 * @param one
	 * @param two
	 * @param leftSecondsToMinutes
	 * @return
	 */
	public static long getDiffMinutes(Date one, Date two, boolean leftSecondsToMinute) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long diffMinutes = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
		if (leftSecondsToMinute && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (60 * 1000) > 0) {
			diffMinutes += 1;
		}
		return diffMinutes;
	}

	/**
	 * 取得两个日期的间隔天数
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return 间隔天数
	 */
	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 得到差异的天数 如果leftHoursToDay为ture，且日期相差的小时数不能被24整除(表示2个日期差，有X天Y小时)，则额外增加1天返回
	 * 
	 * @param one
	 * @param two
	 * @param leftHoursToDay
	 * @return
	 */
	public static long getDiffDays(Date one, Date two, boolean leftHoursToDay) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long differDays = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		if (leftHoursToDay && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (24 * 60 * 60 * 1000) > 0) {
			differDays += 1;
		}
		return differDays;
	}

	/**
	 * 取得两个日期的间隔小时数
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return 间隔小时数
	 */
	public static long getDiffHours(Date one, Date two) {
		return getDiffHours(one, two, false);
	}

	/**
	 * 得到差异的小时数，<br>
	 * 如果leftMiniutesToHour为ture，且日期相差的分钟数不能被60整除(表示2个日期差，有X小时Y分钟)，则额外增加1小时返回（ 返回的是X+1）
	 * 
	 * @param one
	 * @param two
	 * @param leftMiniutesToHour
	 * @return
	 */
	public static long getDiffHours(Date one, Date two, boolean leftMiniutesToHour) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long differHours = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 60 * 1000);
		if (leftMiniutesToHour && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (60 * 60 * 1000) != 0) {
			differHours += 1;
		}
		return differHours;
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			Integer.parseInt(strDate); // ---- 避免日期中输入非数字 ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			Long.parseLong(strDate); // ---- 避免日期中输入非数字 ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @param delimiter
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortDateString(String strDate) {
		return getShortDateString(strDate, "-|/");
	}

	public static String getShortDateString(String strDate, String delimiter) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		String temp = strDate.replaceAll(delimiter, "");

		if (isValidShortDateFormat(temp)) {
			return temp;
		}

		return null;
	}

	public static String getShortFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	public static String getWebFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(webFormat);

		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);
			return formatOut.format(date);
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "转换日期错误,date=" + dateString + ",formatIn=" + formatIn + ",formatOut=" + formatOut, e);
		}

	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	/**
	 * @param date1
	 * @param date2
	 * @param dateWebFormat2
	 * 
	 * @return
	 */
	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);
			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "日期比较异常,date1=" + date1 + ",date2" + date2 + ",format=" + format, e);
		}
	}

	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatTimeRange(Date startDate, Date endDate, String format) {
		if ((endDate == null) || (startDate == null)) {
			return null;
		}

		String rt = null;
		long range = endDate.getTime() - startDate.getTime();
		long day = range / MILLIS_PER_DAY;
		long hour = (range % MILLIS_PER_DAY) / MILLIS_PER_HOUR;
		long minute = (range % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;

		if (range < 0) {
			day = 0;
			hour = 0;
			minute = 0;
		}

		rt = format.replaceAll("dd", String.valueOf(day));
		rt = rt.replaceAll("hh", String.valueOf(hour));
		rt = rt.replaceAll("mm", String.valueOf(minute));

		return rt;
	}

	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	/**
	 * 获取系统日期的前一天日期，返回Date
	 * 
	 * @return
	 */
	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	/**
	 * 获得指定时间当天起点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * 判断参date上min分钟后，是否小于当前时间
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	public static boolean isBeforeNow(Date date) {
		if (date == null)
			return false;
		return date.compareTo(new Date()) < 0;
	}

	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Date parseWebFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(webFormat);
		if ((sDate == null) || (sDate.length() < webFormat.length())) {
			return null;
		}
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException ex) {
			return null;
		}

	}

	/**
	 * 获得指定日期所在周的星期week日期
	 * 
	 * @param date
	 * @param week
	 *            1-7
	 * @return
	 */
	public static String getWeekDate(Date date, int week) {

		String strTemp = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + week);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}

	/***
	 * 获得指定时间的当月的第一天日期
	 * 
	 * @param sourceDate
	 *            指定时间
	 * @return
	 */
	public static Date getMonthFirstDay(Date sourceDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/***
	 * 获得指定时间的当月的最后一天日期
	 * 
	 * @param sourceDate
	 *            指定时间
	 * @return
	 */
	public static Date getMonthLastDay(Date sourceDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获得当前时间的当月第一天日期
	 * 
	 * @return Date
	 */
	public static Date getNowMonthFirstDay() {
		return getMonthFirstDay(DateUtils.now());
	}

	/**
	 * 获得当前时间的当月的最后一天日期
	 * 
	 * @return
	 */
	public static Date getNowMonthLastDay() {
		return getMonthLastDay(DateUtils.now());
	}

	/**
	 * 获得指定时间的星期一日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date firstDay = DateUtils.addDays(date, c.get(Calendar.DAY_OF_WEEK) == 1 ? -6 : -1 * c.get(Calendar.DAY_OF_WEEK) + 2);

		return firstDay;
	}

	/**
	 * 获得指定时间的星期日日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekLastDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date lastDay = DateUtils.addDays(date, c.get(Calendar.DAY_OF_WEEK) == 1 ? 0 : 8 - c.get(Calendar.DAY_OF_WEEK));
		return lastDay;
	}

	/**
	 * 获得当前日期的星期一日期
	 * 
	 * @return
	 */

	public static Date getNowWeekFirstDay() {
		return getWeekFirstDay(DateUtils.now());
	}

	/**
	 * 获得某个日期的所属的星期一日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWeekFirstDay(Date date) {
		return getWeekFirstDay(date);
	}

	/**
	 * 获得当前日期的星期天日期
	 * 
	 * @return
	 */
	public static Date getNowWeekLastDay() {
		return getWeekLastDay(DateUtils.now());
	}

	/**
	 * 获得某个日期的所属的星期天日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWeekLastDay(Date date) {
		return getWeekLastDay(date);
	}

	/**
	 * 
	 * 返回当前日期的星期几
	 * 
	 * @param dt
	 *            日期
	 * @return "日", "一", "二", "三", "四", "五", "六"
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 返回当前日期的星级几
	 * 
	 * @param dt
	 *            日期
	 * @return 1..7,对应:"7=周日,1=周一...6=周六"
	 */
	public static int getIntWeekOfDate(Date dt) {
		Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 设置日期的时分秒为0
	 * 
	 * @param date
	 */
	public static Date setDateStart(Date date) {
		if (date == null) {
			return null;
		}
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	/**
	 * 设置日期的时分秒为23:59:59
	 * 
	 * @param date
	 */
	public static Date setDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
	}

	/**
	 * 将秒数转换为A天B时C分D秒
	 * 
	 * @param seconds
	 * @return
	 */
	public static String toDescString(int seconds) {
		StringBuffer buf = new StringBuffer();
		int days = seconds / (24 * 3600);
		if (days > 0) {
			buf.append(days).append("天");
		}
		seconds = seconds % (24 * 3600);
		int hours = seconds / (3600);
		if (hours > 0) {
			buf.append(hours).append("小时");
		}

		seconds = seconds % (3600);
		int minutes = seconds / 60;
		if (minutes > 0) {
			buf.append(minutes).append("分钟");
		}

		seconds = seconds % (60);
		if (seconds > 0) {
			buf.append(seconds).append("秒");
		}
		return buf.toString();
	}

	public static Date max(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.compareTo(d2) > 0 ? d1 : d2;
	}

	public static Date min(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.compareTo(d2) > 0 ? d2 : d1;
	}

	/**
	 * 获取时间戳 必须符合ISO8601规范，并需要使用UTC时间，时区为+0
	 */
	public static String getISO8601Time(Date date) {
		Date nowDate = date;
		if (null == date) {
			nowDate = new Date();
		}
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_ISO8601);
		df.setTimeZone(new SimpleTimeZone(0, TIME_ZONE));
		return df.format(nowDate);
	}

	/**
	 * 解析UTC时间<br>
	 * 如果是其他格式，直接调用parseDate函数，自己传递format
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseUTC(String date) {
		date = StringUtils.trim(date);
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat df = null;
		if (StringUtils.indexOf(date, StringUtils.BLANK_STRING) > 0) {
			df = new SimpleDateFormat("yyyy-MM-dd 'T'HH:mm:ss'Z'");
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		}

		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return df.parse(date);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "不正确的UTC时间,date=" + date);
		}
	}

	/**
	 * 获取时间戳 必须符合ISO8601规范，并需要使用UTC时间，时区为+0
	 * 
	 * @return
	 */
	public static String getISO8601Time() {
		return getISO8601Time(new Date());
	}

}
