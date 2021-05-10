package com.bench.lang.base.string.utils;

/**
 * 字符串格式化工具
 * 
 * @author cold
 *
 * @version $Id: StringFormatUtils.java, v 0.1 2019年2月26日 上午9:48:46 cold Exp $
 */
public class StringFormatUtils {

	/**
	 * 格式化字符串
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static String format(String format, Object... args) {
		return String.format(format, args);
	}

}
