/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.deserializer;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * 
 * 
 * @author cold
 *
 * @version $Id: AbstractJacksonDeserializer.java, v 0.1 2019年12月25日 下午3:52:31 cold Exp $
 */
public abstract class AbstractJacksonDeserializer<T> extends JsonDeserializer<T> implements ContextualDeserializer {

	protected BeanDescription beanDescription;

	public AbstractJacksonDeserializer(BeanDescription beanDescription) {
		super();
		this.beanDescription = beanDescription;
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
		// TODO Auto-generated method stub
		return this;
	}

}
