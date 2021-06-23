/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.annotations.serializer;


import com.bench.lang.base.json.jackson.annotations.serializer.impl.*;
import com.bench.lang.base.order.comparator.OrderedComparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author chenbug
 *
 * @version $Id: ValueAnnotationSerializerFactory.java, v 0.1 2016年3月2日 下午2:30:02 chenbug Exp $
 */
public class ValueAnnotationSerializerFactory {

	private static Map<Class<?>, ValueAnnotationSerializer<?>> handlerMap = new HashMap<Class<?>, ValueAnnotationSerializer<? extends Annotation>>();

	static {
		registerHandler(new ScriptDateFormatValueAnnotationHandler());
		registerHandler(new JSONSerializerAnnotationHandler());
		registerHandler(new LongExtraDateFormatValueAnnotationHandler());
		registerHandler(new MatchExpressionValueAnnotationHandler());
		registerHandler(new LowerCaseValueValueAnnotationHandler());
		registerHandler(new UnderscoreSignToSubSignValueValueAnnotationHandler());
	}

	public static void registerHandler(ValueAnnotationSerializer<? extends Annotation> handler) {
		handlerMap.put((Class<?>) ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0], handler);
	}

	public static List<ValueAnnotationSerializer<?>> getHandlers(AnnotatedElement annotatedElement) {
		List<ValueAnnotationSerializer<?>> returnList = new ArrayList<ValueAnnotationSerializer<?>>();
		for (Annotation anno : annotatedElement.getAnnotations()) {
			ValueAnnotationSerializer<?> handler = handlerMap.get(anno.annotationType());
			if (handler != null) {
				returnList.add(handler);
			}
		}
		// 排序
		returnList.sort(OrderedComparator.INSTANCE);
		return returnList;
	}

}
