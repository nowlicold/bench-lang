package com.bench.lang.base.convert.converters;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.bench.lang.base.convert.ConvertChain;
import com.bench.lang.base.convert.ConvertFailedException;
import com.bench.lang.base.convert.Converter;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.number.utils.NumberUtils;

public class DateConverter implements Converter<Date> {

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
			if (!StringUtils.isEmpty(strValue)) {
				try {
					// yyyyMMddHHmmss
					if (NumberUtils.isDigits(strValue) && strValue.length() == 14) {
						return DateUtils.parseLongDateExtraString(strValue);
					}
					// yyyy-MM-dd HH:mm:ss
					else if (strValue.length() == 19) {
						return DateUtils.parseDateNewFormat(strValue);
					}
				} catch (Exception e) {
					if (strValue.length() > 0) {
						throw new ConvertFailedException(e).setDefaultValue(DEFAULT_VALUE);
					}
					throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
				}
			}

		}
		return (Date) chain.convert(value);
	}
}
