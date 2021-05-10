package com.bench.lang.base.pattern.utils;

import java.util.List;

/**
 * 字符串匹配
 * 
 * @author cold
 *
 * @version $Id: PatternMatchUtils.java, v 0.1 2014年12月16日 下午5:58:37 cold Exp $
 */
public class PatternMatchUtils {

	/**
	 * 是否匹配
	 * 
	 * @param pattern
	 * @param str
	 * @return
	 */
	public static boolean match(String pattern, String str) {
		if (pattern == null || str == null) {
			return false;
		}
		int firstIndex = pattern.indexOf('*');
		if (firstIndex == -1) {
			return pattern.equals(str);
		}
		if (firstIndex == 0) {
			if (pattern.length() == 1) {
				return true;
			}
			int nextIndex = pattern.indexOf('*', firstIndex + 1);
			if (nextIndex == -1) {
				return str.endsWith(pattern.substring(1));
			}
			String part = pattern.substring(1, nextIndex);
			if (part.isEmpty()) {
				return match(pattern.substring(nextIndex), str);
			}
			int partIndex = str.indexOf(part);
			while (partIndex != -1) {
				if (match(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
					return true;
				}
				partIndex = str.indexOf(part, partIndex + 1);
			}
			return false;
		}
		return (str.length() >= firstIndex && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex))
				&& match(pattern.substring(firstIndex), str.substring(firstIndex)));
	}

	/**
	 * 匹配任意一个
	 * 
	 * @param patterns
	 * @param str
	 * @return
	 */
	public static boolean matchAny(String[] patterns, String str) {
		if (patterns != null) {
			for (String pattern : patterns) {
				if (match(pattern, str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 全部匹配
	 * 
	 * @param patterns
	 * @param str
	 * @return
	 */
	public static boolean matchAll(String[] patterns, String str) {
		if (patterns != null) {
			for (String pattern : patterns) {
				if (!match(pattern, str)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 匹配任意一个
	 * 
	 * @param patterns
	 * @param str
	 * @return
	 */
	public static boolean matchAny(List<String> patterns, String str) {
		if (patterns == null) {
			return false;
		}
		return matchAny(patterns.toArray(new String[patterns.size()]), str);
	}

	/**
	 * 全部匹配
	 * 
	 * @param patterns
	 * @param str
	 * @return
	 */
	public static boolean matchAll(List<String> patterns, String str) {
		if (patterns == null) {
			return false;
		}
		return matchAll(patterns.toArray(new String[patterns.size()]), str);
	}
}
