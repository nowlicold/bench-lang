package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.user.LowerCaseValue;
import com.bench.lang.base.order.Ordered;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 字符串串值小写处理
 * 
 * @author cold
 *
 * @version $Id: LowerCaseValueValueAnnotationHandler.java, v 0.1 2016年3月2日 下午2:29:37 cold Exp $
 */
public class LowerCaseValueValueAnnotationHandler implements ValueAnnotationSerializer<LowerCaseValue> {

	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		if (value instanceof String) {
			return ((String) value).toLowerCase();
		}
		// 如果是集合
		if (value instanceof List) {
			List<Object> valueList = (List<Object>) value;
			for (int i = 0; i < valueList.size(); i++) {
				Object singleValue = valueList.get(i);
				if (singleValue != null && singleValue instanceof String) {
					valueList.set(i, ((String) singleValue).toLowerCase());
				}
			}
		}
		// 如果是数组
		if (value.getClass().isArray()) {
			for (int i = 0; i < ArrayUtils.getLength(value); i++) {
				Object singleValue = Array.get(value, i);
				if (singleValue != null && singleValue instanceof String) {
					Array.set(value, i, ((String) singleValue).toLowerCase());
				}
			}
		}
		return value;
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return Ordered.LOWEST_PRECEDENCE;
	}
}
