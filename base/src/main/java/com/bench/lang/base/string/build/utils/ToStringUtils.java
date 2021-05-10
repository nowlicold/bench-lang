package com.bench.lang.base.string.build.utils;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ReflectionToStringBuilder;
import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 将对象描述成String
 * 
 * @author cold
 * 
 * @version $Id: ToStringUtils.java, v 0.1 2013-12-16 下午4:27:14 cold Exp $
 */
public class ToStringUtils {

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String reflectionToString(Object object) {
		return ToStringBuilder.reflectionToString(object);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param excludeFieldNames
	 * @return
	 */
	public static String reflectionToString(Object object, String[] excludeFieldNames) {
		return ToStringBuilder.reflectionToString(object, excludeFieldNames);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param excludeFieldNames
	 * @return
	 */
	public static String reflectionToString(Object object, String[] excludeFieldNames, String[] maskFieldNames) {
		return ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE, excludeFieldNames, maskFieldNames);
	}

	/**
	 * 
	 * 
	 * @param object
	 * @param style
	 * @return
	 */
	public static String reflectionToString(Object object, ToStringStyle style) {
		return ToStringBuilder.reflectionToString(object, style);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param excludeFieldNames
	 * @return
	 */
	public static String reflectionToString(Object object, ToStringStyle style, String[] excludeFieldNames) {
		return ToStringBuilder.reflectionToString(object, style, excludeFieldNames);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param excludeFieldNames
	 * @return
	 */
	public static String reflectionToString(Object object, ToStringStyle style, String[] excludeFieldNames, String[] maskFieldNames) {
		return ToStringBuilder.reflectionToString(object, style, excludeFieldNames, maskFieldNames);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param outputTransients
	 * @return
	 */
	public static String reflectionToString(Object object, ToStringStyle style, boolean outputTransients) {
		return ToStringBuilder.reflectionToString(object, style, outputTransients);
	}

	/**
	 * 
	 * @param object
	 * @param style
	 * @param excludeFieldNames
	 * @param outputTransients
	 * @return
	 */
	public static String reflectionToString(Object object, ToStringStyle style, String[] excludeFieldNames, String[] maskFieldNames, boolean outputTransients) {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(object, style);
		builder.setExcludeFieldNames(excludeFieldNames);
		builder.setAppendTransients(outputTransients);
		return builder.toString();
	}

}
