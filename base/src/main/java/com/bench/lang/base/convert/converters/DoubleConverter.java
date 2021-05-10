package com.bench.lang.base.convert.converters;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成双精度浮点数.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>Double</code>了, 直接返回.</li>
 * <li>如果对象是<code>Number</code>类型, 则返回它的双精度浮点数值.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成双精度浮点数. 如果不成功, 则抛出<code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: DoubleConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class DoubleConverter implements Converter<Double> {
	protected static final Double DEFAULT_VALUE = Double.valueOf(0.0);

	public Double convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Double) {
			return (Double) value;
		}

		if (value instanceof Number) {
			return Double.valueOf(((Number) value).doubleValue());
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return Double.valueOf(strValue);
			} catch (NumberFormatException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (Double) chain.convert(value);
	}
}
