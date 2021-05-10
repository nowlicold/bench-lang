package com.bench.lang.base.money.converter;

import java.util.Date;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;
import com.bench.lang.base.money.Money;
import com.bench.lang.base.object.utils.ObjectUtils;

/**
 * 注意，只能转换以元为单位的金额<br>
 * 如1.2，1.0,0.21<br>
 * 
 * @author cold
 *
 * @version $Id: MoneyConverter.java, v 0.1 2019年7月23日 下午12:07:23 cold Exp $
 */
public class MoneyConverter implements Converter<Money> {

	protected static final Date DEFAULT_VALUE = null;

	public Money convert(Object value, ConvertChain chain) {
		if (value == null) {
			throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
		}

		if (value instanceof Money) {
			return (Money) value;
		}

		return new Money(ObjectUtils.toString(value));
	}
}
