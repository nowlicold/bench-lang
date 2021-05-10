/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.utils;

/**
 * 
 * @author cold
 *
 * @version $Id: SizeUtils.java, v 0.1 2018年10月17日 下午4:24:45 cold Exp $
 */
public class SizeUtils {

	/**
	 * 解析k m g t p等容量为bytes
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static long parseBytesSize(String value) throws Exception {
		if (value.endsWith("k")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 1)) * 1024);
		} else if (value.endsWith("kb")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 2)) * 1024);
		} else if (value.endsWith("m")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 1)) * Math.pow(1024 * 1024, 2));
		} else if (value.endsWith("mb")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 2)) * Math.pow(1024 * 1024, 2));
		} else if (value.endsWith("g")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 1)) * Math.pow(1024 * 1024, 3));
		} else if (value.endsWith("gb")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 2)) * Math.pow(1024 * 1024, 3));
		} else if (value.endsWith("t")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 1)) * Math.pow(1024 * 1024, 4));
		} else if (value.endsWith("tb")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 2)) * Math.pow(1024 * 1024, 4));
		} else if (value.endsWith("p")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 1)) * Math.pow(1024 * 1024, 25));
		} else if (value.endsWith("pb")) {
			return (long) (Double.parseDouble(value.substring(0, value.length() - 2)) * Math.pow(1024 * 1024, 5));
		} else if (value.endsWith("b")) {
			return Long.parseLong(value.substring(0, value.length() - 1));
		} else {
			return Long.parseLong(value);
		}
	}

}
