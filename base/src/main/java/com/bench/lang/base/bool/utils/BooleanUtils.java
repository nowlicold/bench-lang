/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.bool.utils;

import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 
 * @author cold
 * 
 * @version $Id: BooleanUtils.java, v 0.1 2010-10-26 下午04:52:30 cold Exp $
 */
public class BooleanUtils extends org.apache.commons.lang3.BooleanUtils {

	public static final BooleanUtils INSTANCE = new BooleanUtils();

	public static final String[] TRUE_STRING = new String[] { "1", "t", "true", "on", "yes" };

	public static final String[] FALSE_STRING = new String[] { "0", "f", "false", "on", "yes" };

	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(Object value) {
		if (value == null)
			return false;
		else if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue() > 0;
		} else if (value instanceof String) {
			return toBoolean((String) value, false);
		}
		return toBoolean(ObjectUtils.toString(value), false);
	}

	/**
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static boolean toBoolean(String value, boolean defaultValue) {
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		if (StringUtils.equalsIgnoreCase(value, "t")) {
			return true;
		}
		if (StringUtils.equalsIgnoreCase(value, "1")) {
			return true;
		}
		return org.apache.commons.lang3.BooleanUtils.toBoolean(value);
	}

	/**
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Boolean toBooleanObject(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (StringUtils.equalsIgnoreCase(value, "t")) {
			return true;
		}
		if (StringUtils.equalsIgnoreCase(value, "1")) {
			return true;
		}
		return org.apache.commons.lang3.BooleanUtils.toBooleanObject(value);
	}

	/**
	 * 是否可能是布尔值
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isMaybeBooleanValue(String value) {
		return StringUtils.containsAnyIgnoreCase(value, TRUE_STRING) || StringUtils.containsAnyIgnoreCase(value, FALSE_STRING);
	}

}
