package com.bench.lang.base.convert.converters;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.Converter;
import com.bench.lang.base.object.utils.ObjectUtils;

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
 * @version $Id: EnumConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class EnumConverter implements Converter<Enum<?>> {

	@SuppressWarnings("unchecked")
	@Override
	public Enum<?> convert(Object value, ConvertChain chain) {
		// TODO Auto-generated method stub
		String stringValue = ObjectUtils.toString(value);
		if (stringValue == null) {
			return null;
		}
		return (Enum<?>) Enum.valueOf((Class<Enum>)chain.getTargetType(), stringValue);
	}

}
