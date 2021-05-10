package com.bench.lang.base.convert.converters;

import java.sql.Date;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成<code>java.sql.Date</code>.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>java.sql.Date</code>了, 直接返回.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成<code>java.sql.Date</code>, 格式必须为"yyyy-mm-dd". 如果不成功, 则抛出<code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: SqlDateConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class SqlDateConverter implements Converter<Date> {

	protected static final Date DEFAULT_VALUE = null;

	public Date convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Date) {
			return (Date) value;
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return Date.valueOf(strValue);
			} catch (IllegalArgumentException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (Date) chain.convert(value);
	}
}
