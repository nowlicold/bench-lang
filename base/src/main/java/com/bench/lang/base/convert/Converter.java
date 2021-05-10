package com.bench.lang.base.convert;

import com.bench.lang.base.instance.annotations.Singleton;

/**
 * 将对象转换成另一种形式的转换器.
 *
 * @author cold
 * @version $Id: Converter.java 509 2004-02-16 05:42:07Z cold $
 */
@Singleton
public interface Converter<T> {

	/**
	 * 转换指定的值到指定的类型.
	 *
	 * @param value
	 *            要转换的值
	 * @param chain
	 *            转换链, 如果一个转换器不能转换某种类型, 则把它交给链中的下一个转换器
	 *
	 * @return 转换后的值
	 */
	T convert(Object value, ConvertChain chain);
}
