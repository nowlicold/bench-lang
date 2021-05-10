package com.bench.lang.base.log;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.log.enums.LogLevelEnum;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Bench控制台日志
 * 
 * @author cold
 *
 * @version $Id: BenchConsoleLogger.java, v 0.1 2015年6月9日 下午5:53:17 cold Exp $
 */
public class BenchConsoleLogger {

	public static final BenchConsoleLogger INSTANCE = new BenchConsoleLogger();

	/**
	 * 当前日志级别
	 */
	private static LogLevelEnum level = LogLevelEnum.INFO;

	/**
	 * 日志级别是否有效
	 * 
	 * @param level
	 * @return
	 */
	public static boolean isLevelEnabled(LogLevelEnum level) {
		return level.value().intValue() <= BenchConsoleLogger.level.value().intValue();
	}

	/**
	 * trace是否有效
	 * 
	 * @return
	 */
	public static boolean isTraceEnabled() {
		return isLevelEnabled(LogLevelEnum.TRACE);
	}

	/**
	 * debug是否有效
	 * 
	 * @return
	 */
	public static boolean isDebugEnabled() {
		return isLevelEnabled(LogLevelEnum.DEBUG);
	}

	/**
	 * info是否有效
	 * 
	 * @return
	 */
	public static boolean isInfoEnabled() {
		return isLevelEnabled(LogLevelEnum.INFO);
	}

	/**
	 * error是否有效
	 * 
	 * @return
	 */
	public static boolean isErrorEnabled() {
		return isLevelEnabled(LogLevelEnum.ERROR);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void trace(String message) {
		trace(message, null, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void trace(String message, int indentCount) {
		trace(message, null, indentCount);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void trace(String message, Throwable throwable) {
		trace(message, throwable, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 * @param time
	 * @param line
	 */
	public static void trace(String message, Throwable throwable, int indentCount) {
		log(LogLevelEnum.TRACE, message, throwable, indentCount, false, true);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		debug(message, null, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void debug(String message, int indentCount) {
		debug(message, null, indentCount);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void debug(String message, Throwable throwable) {
		debug(message, throwable, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 * @param time
	 * @param line
	 */
	public static void debug(String message, Throwable throwable, int indentCount) {
		log(LogLevelEnum.DEBUG, message, throwable, indentCount, false, true);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void info(String message) {
		info(message, null, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void info(String message, int indentCount) {
		info(message, null, indentCount);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void info(String message, Throwable throwable) {
		info(message, throwable, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 * @param time
	 * @param line
	 */
	public static void info(String message, Throwable throwable, int indentCount) {
		log(LogLevelEnum.INFO, message, throwable, indentCount, false, true);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void error(String message) {
		error(message, null, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void error(String message, int indentCount) {
		error(message, null, indentCount);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void error(String message, Throwable throwable) {
		error(message, throwable, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 * @param time
	 * @param line
	 */
	public static void error(String message, Throwable throwable, int indentCount) {
		log(LogLevelEnum.ERROR, message, throwable, indentCount, false, true);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		warn(message, null, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 */
	public static void warn(String message, int indentCount) {
		warn(message, null, indentCount);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void warn(String message, Throwable throwable) {
		warn(message, throwable, 0);
	}

	/**
	 * 打印日志到控制台
	 * 
	 * @param message
	 * @param throwable
	 * @param time
	 * @param line
	 */
	public static void warn(String message, Throwable throwable, int indentCount) {
		log(LogLevelEnum.WARN, message, throwable, indentCount, false, true);
	}

	public static void log(LogLevelEnum level, String message, Throwable throwable, int indentCount, boolean lineStart, boolean lineEnd) {
		if (!isLevelEnabled(level)) {
			return;
		}
		StringBuffer logMessage = new StringBuffer();
		logMessage.append(DateUtils.getNewFormatDateString(new Date())).append(StringUtils.BLANK_STRING);
		logMessage.append(StringUtils.fill(' ', indentCount));
		logMessage.append(message);
		if (throwable != null) {
			logMessage.append("\r\n");
			logMessage.append(ExceptionUtils.getStackTrace(throwable));
		}

		if (lineStart) {
			logMessage.insert(0, "\r\n");
		}
		if (lineEnd) {
			System.out.println(logMessage);
		} else {
			System.out.print(logMessage);
		}
	}

	public static LogLevelEnum getLevel() {
		return level;
	}

	public static void setLevel(LogLevelEnum level) {
		BenchConsoleLogger.level = level;
	}

}
