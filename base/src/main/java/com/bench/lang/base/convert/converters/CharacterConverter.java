package com.bench.lang.base.convert.converters;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;

/**
 * 将对象转换成字符.
 * 
 * <ul>
 * <li>如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象已经是<code>Character</code>了, 直接返回.</li>
 * <li>如果对象是<code>Number</code>类型, 则以此值为Unicode, 返回对应的字符值.</li>
 * <li>如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.</li>
 * <li>如果对象是字符串, 则试着把它转换成整数代表的字符. 如果不成功, 则抛出<code>ConvertFailedException</code>.</li>
 * <li>否则, 把对象传递给下一个<code>Converter</code>处理.</li>
 * </ul>
 * 
 *
 * @author cold
 * @version $Id: CharacterConverter.java 1334 2005-05-27 00:52:23Z cold $
 */
public class CharacterConverter implements Converter<Character> {
	protected static final Character DEFAULT_VALUE = Character.valueOf('\0');

	public Character convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Character) {
			return (Character) value;
		}

		if (value instanceof Number) {
			return Character.valueOf((char) ((Number) value).intValue());
		}

		if (value instanceof String) {
			String strValue = ((String) value).trim();

			try {
				return Character.valueOf((char) Integer.parseInt(strValue));
			} catch (NumberFormatException e) {
				if (strValue.length() > 0) {
					throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
				}

				throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
			}
		}

		return (Character) chain.convert(value);
	}
}
