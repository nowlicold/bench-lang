/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.annotations.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

/**
 * 多Value注解处理
 * 
 * @author chenbug
 *
 * @version $Id: MultiValueAnnotationHandleJsonSerializer.java, v 0.1 2016年3月2日
 *          下午2:46:53 chenbug Exp $
 */
public class MultiValueAnnotationJsonSerializer extends JsonSerializer<Object> {

	private List<ValueAnnotationSerializer<?>> handlers;

	private BeanProperty beanProperty;

	public MultiValueAnnotationJsonSerializer(BeanProperty beanProperty, List<ValueAnnotationSerializer<?>> handlers) {
		super();
		this.handlers = handlers;
		this.beanProperty = beanProperty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		for (ValueAnnotationSerializer<?> handler : handlers) {
			value = handler.handle(value, gen, serializers, beanProperty);
		}
		gen.writeObject(value);
	}

	/**
	 * @return Returns the handlers.
	 */
	public List<ValueAnnotationSerializer<?>> getHandlers() {
		return handlers;
	}

	/**
	 * @return Returns the beanProperty.
	 */
	public BeanProperty getBeanProperty() {
		return beanProperty;
	}

}
