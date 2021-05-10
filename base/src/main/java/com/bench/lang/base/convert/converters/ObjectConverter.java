package com.bench.lang.base.convert.converters;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.Converter;

/**
 * Object类型的转换器.
 * 
 * <ul>
 * <li>如果<code>targetType</code>是<code>Object</code>类型, 则直接返回.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: ObjectConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class ObjectConverter implements Converter<Object> {
	public Object convert(Object value, ConvertChain chain) {
		Class<?> targetType = chain.getTargetType();

		if (Object.class.equals(targetType)) {
			return value;
		}

		return chain.convert(value);
	}
}
