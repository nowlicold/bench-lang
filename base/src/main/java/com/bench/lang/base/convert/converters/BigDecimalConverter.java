package com.bench.lang.base.convert.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成<code>BigDecimal</code>.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>BigDecimal</code>了, 直接返回.</li>
 * <li>如果对象是<code>BigInteger</code>, 直接转换成<code>BigDecimal</code>.</li>
 * <li>如果对象是<code>Number</code>类型, 则返回它的<code>BigDecimal</code>值.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成<code>BigDecimal</code>. 如果不成功, 则抛出 <code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 * 
 * @author cold
 * @version $Id: BigDecimalConverter.java 509 2004-02-16 05:42:07Z cold $
 */
public class BigDecimalConverter implements Converter<BigDecimal> {
	protected static final BigDecimal DEFAULT_VALUE = new BigDecimal(BigInteger.ZERO);

	public BigDecimal convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}

		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}

		if (value instanceof Number) {
			return new BigDecimal(value.toString());
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return new BigDecimal(strValue);
			} catch (NumberFormatException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (BigDecimal) chain.convert(value);
	}
}
