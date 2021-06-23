package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.user.ScriptDateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.util.Date;

/**
 * 脚本日期处理
 * 
 * @author chenbug
 *
 * @version $Id: ScriptDateFormatValueAnnotationHandler.java, v 0.1 2016年3月2日
 *          下午2:29:37 chenbug Exp $
 */
public class ScriptDateFormatValueAnnotationHandler implements ValueAnnotationSerializer<ScriptDateFormat> {

	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		if (value instanceof Date) {
			return DateUtils.format((Date) value, ((ScriptDateFormat) (beanProperty.getAnnotation(ScriptDateFormat.class))).value());
		}
		return value;
	}
}
