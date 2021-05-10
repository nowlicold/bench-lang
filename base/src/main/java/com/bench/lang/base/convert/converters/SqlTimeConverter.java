package com.bench.lang.base.convert.converters;

import java.sql.Time;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成<code>java.sql.Time</code>.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>java.sql.Time</code>了, 直接返回.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成<code>java.sql.Time</code>, 格式必须为"hh:mm:ss". 如果不成功, 则抛出<code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: SqlTimeConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class SqlTimeConverter implements Converter<Time> {
	protected static final Time DEFAULT_VALUE = null;

	public Time convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Time) {
			return (Time) value;
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return Time.valueOf(strValue);
			} catch (IllegalArgumentException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (Time) chain.convert(value);
	}
}
