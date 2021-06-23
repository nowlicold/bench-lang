package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.JexlUtils;
import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.user.MatchExpression;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.jexl2.JexlContext;

import java.lang.reflect.Field;

/**
 * 表达式匹配处理
 * 
 * @author chenbug
 *
 * @version $Id: MatchExpressionValueAnnotationHandler.java, v 0.1 2016年3月2日
 *          下午2:29:37 chenbug Exp $
 */
public class MatchExpressionValueAnnotationHandler implements ValueAnnotationSerializer<MatchExpression> {

	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		Field field = FieldUtils.getFieldSafe(beanProperty.getMember().getDeclaringClass(), beanProperty.getName());
		if (field != null) {
			MatchExpression matchExpression = field.getAnnotation(MatchExpression.class);
			if (matchExpression != null) {
				JexlUtils.getDefaultJexlContext();
				JexlContext jexlContext = JexlUtils.getDefaultJexlContext();
				jexlContext.set("value", value);
				jexlContext.set("bean", gen.getCurrentValue());
				jexlContext.set("beanProperty", beanProperty);
				if (!BooleanUtils.toBoolean(JexlUtils.evaluate(matchExpression.value(), jexlContext))) {
					try {
					} catch (Exception e) {

					} 
					return StringUtils.EMPTY_STRING;
				}
			}
		}
		return value;

	}
}
