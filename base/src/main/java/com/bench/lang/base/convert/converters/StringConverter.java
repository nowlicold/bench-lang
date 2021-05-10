package com.bench.lang.base.convert.converters;

import java.util.Date;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;
import com.bench.lang.base.date.utils.DateUtils;

/**
 * 将对象转换成字符串.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是字符串, 直接返回.</li>
 * <li>如果对象是字符数组, 则将它组合成字符串.</li>
 * <li>否则返回<code>toString()</code>.</li>
 * </ul>
 * 
 * 
 * @author cold
 * @version $Id: StringConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class StringConverter implements Converter<String> {
	protected static final String DEFAULT_VALUE = "";

	public String convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof String) {
			return (String) value;
		}

		if (value.getClass().isArray()) {
			if (value.getClass().getComponentType().equals(Character.TYPE)) {
				return new String((char[]) value);
			}
		}
		if (value.getClass().equals(Date.class)) {
			return DateUtils.getLongDateExtraString((Date) value);
		}
		return value.toString();
	}
}
