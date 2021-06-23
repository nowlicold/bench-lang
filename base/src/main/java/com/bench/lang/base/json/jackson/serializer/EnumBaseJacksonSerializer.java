/**
 * benchcode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.serializer;

import com.bench.lang.base.clasz.method.utils.MethodUtils;
import com.bench.lang.base.enums.EnumBase;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.json.user.ScriptMethod;
import com.bench.lang.base.json.utils.JSONUtils;
import com.bench.lang.base.object.ToStringBuilder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: EnumBaseJsonValueProcessor.java, v 0.1 2010-8-25 下午10:20:05 chenbug Exp $
 */
public class EnumBaseJacksonSerializer extends JsonSerializer<EnumBase> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(EnumBase value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		jgen.writeStartObject();
		jgen.writeStringField("name", value.name());
		// 如果当前类定义了message方法
		Method messageMethod = MethodUtils.getDeclaredMethod(value.getClass(), "message", false);
		if (messageMethod != null) {
			jgen.writeStringField("message", value.message());
		}
		if (value.value() != null) {
			Number number = value.value();
			if (number instanceof Integer) {
				jgen.writeNumberField("value", (Integer) number);
			} else if (number instanceof Double) {
				jgen.writeNumberField("value", (Double) number);
			} else if (number instanceof Float) {
				jgen.writeNumberField("value", (Float) number);
			} else if (number instanceof Long) {
				jgen.writeNumberField("value", (Long) number);
			} else if (number instanceof BigDecimal) {
				jgen.writeNumberField("value", (BigDecimal) number);
			} else {
				// 默认为double
				jgen.writeNumberField("value", number.doubleValue());
			}
		}

		/*******************************
		 * ScriptMethod方法
		 ********************************/
		for (Method method : value.getClass().getDeclaredMethods()) {
			ScriptMethod scriptMethodAnno = (ScriptMethod) method.getAnnotation(ScriptMethod.class);
			if (scriptMethodAnno == null)
				continue;
			try {
				Object methodValue = method.invoke(value, (Object[]) null);
				jgen.writeObjectField(JSONUtils.getScriptName(method, method.getName()), methodValue);
			} catch (Exception e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "EnumBase转换成JSON异常:" + ToStringBuilder.reflectionToString(value), e);
			}

		}
		jgen.writeEndObject();
	}

}
