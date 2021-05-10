package com.bench.lang.base.string.build.construct;

import java.lang.reflect.Field;

import com.bench.lang.base.order.Ordered;

/**
 * 转字符串的构造器
 * 
 * @author cold
 *
 * @version $Id: ToStringConstructor.java, v 0.1 2020年3月31日 下午12:25:35 cold Exp $
 */
public interface ToStringConstructor extends Ordered {

	/**
	 * 是否支持
	 * 
	 * @param field
	 * @return
	 */
	boolean isSupport(Field field);

	/**
	 * Field的value转字符串
	 * 
	 * @param object
	 * @param field
	 * @param fieldValue
	 * @return
	 */
	String buildString(Object object, Field field, Object fieldValue);

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return Ordered.DEFAULT_ORDER;
	}
}
