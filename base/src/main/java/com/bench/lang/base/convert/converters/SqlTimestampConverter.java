package com.bench.lang.base.convert.converters;

import java.sql.Timestamp;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成<code>java.sql.Timestamp</code>.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>java.sql.Timestamp</code>了, 直接返回.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成<code>java.sql.Timestamp</code>, 格式必须为"yyyy-mm-dd hh:mm:ss.fffffffff". 如果不成功, 则抛出<code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: SqlTimestampConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class SqlTimestampConverter implements Converter<Timestamp> {
	protected static final Timestamp DEFAULT_VALUE = null;

	public Timestamp convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Timestamp) {
			return (Timestamp) value;
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return Timestamp.valueOf(strValue);
			} catch (IllegalArgumentException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (Timestamp) chain.convert(value);
	}
}
