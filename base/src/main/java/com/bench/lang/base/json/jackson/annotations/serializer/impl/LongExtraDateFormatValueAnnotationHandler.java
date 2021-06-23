package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.user.LongExtraDateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.util.Date;

/**
 * 日期转换成yyyyMMddHHmmssSSS
 * 
 * @author chenbug
 *
 * @version $Id: LongExtraDateFormatValueAnnotationHandler.java, v 0.1 2016年3月2日
 *          下午2:29:37 chenbug Exp $
 */
public class LongExtraDateFormatValueAnnotationHandler implements ValueAnnotationSerializer<LongExtraDateFormat> {

	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		if (value instanceof Date) {
			return DateUtils.getLongDateExtraString((Date) value);
		}
		return value;
	}
}
