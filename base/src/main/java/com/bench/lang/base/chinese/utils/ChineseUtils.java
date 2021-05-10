package com.bench.lang.base.chinese.utils;

/**
 * 中文工具类
 * 
 * @author cold
 *
 * @version $Id: ChineseUtils.java, v 0.1 2018年11月13日 下午7:53:19 cold Exp $
 */
public class ChineseUtils {

	/**
	 * 判断某个字符是否为汉字
	 * 
	 * @param c
	 *            需要判断的字符
	 * @return 是汉字返回true，否则返回false
	 */
	public static boolean isChinese(char c) {
		String regex = "[\\u4e00-\\u9fa5]";
		return String.valueOf(c).matches(regex);
	}
}
