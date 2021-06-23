package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.user.UnderscoreSignToSubSign;
import com.bench.lang.base.order.Ordered;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 下划线转中杠处理
 * 
 * @author chenbug
 *
 * @version $Id: UnderscoreSignToSubSignValueValueAnnotationHandler.java, v 0.1 2016年3月2日 下午2:29:37 chenbug Exp $
 */
public class UnderscoreSignToSubSignValueValueAnnotationHandler implements ValueAnnotationSerializer<UnderscoreSignToSubSign> {

	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		if (value instanceof String) {
			return StringUtils.replace(((String) value), StringUtils.UNDERSCORE_SIGN, StringUtils.SUB_SIGN);
		}
		// 如果是集合
		if (value instanceof List) {
			List<Object> valueList = (List<Object>) value;
			for (int i = 0; i < valueList.size(); i++) {
				Object singleValue = valueList.get(i);
				if (singleValue != null && singleValue instanceof String) {
					valueList.set(i, StringUtils.replace((String) singleValue, StringUtils.UNDERSCORE_SIGN, StringUtils.SUB_SIGN));
				}
			}
		}
		// 如果是数组
		if (value.getClass().isArray()) {
			for (int i = 0; i < ArrayUtils.getLength(value); i++) {
				Object singleValue = Array.get(value, i);
				if (singleValue != null && singleValue instanceof String) {
					Array.set(value, i, StringUtils.replace((String) singleValue, StringUtils.UNDERSCORE_SIGN, StringUtils.SUB_SIGN));
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
