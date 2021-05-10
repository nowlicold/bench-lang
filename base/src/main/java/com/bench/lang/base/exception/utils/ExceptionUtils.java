package com.bench.lang.base.exception.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * 异常工具类
 * 
 * @author cold
 *
 * @version $Id: ExceptionUtils.java, v 0.1 2016年3月11日 下午3:56:20 cold Exp $
 */
public final class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {

	/**
	 * 得到当前异常的根异常
	 * 
	 * @param throwable
	 * @return
	 */
	public static Throwable getRootCause(Throwable throwable) {
		Throwable cause = throwable;
		if (throwable != null) {
			cause = throwable.getCause();
			while ((throwable = cause.getCause()) != null) {
				cause = throwable;
			}
		}

		return cause;
	}

	/**
	 * 得到异常堆栈
	 * 
	 * @param throwable
	 * @return
	 */
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw);) {
			throwable.printStackTrace(pw);
			return sw.toString();
		}
	}

}
