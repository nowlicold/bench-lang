package com.bench.lang.base.json.jackson.annotations.serializer;

import com.bench.lang.base.order.Ordered;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.lang.annotation.Annotation;

/**
 * 注解处理器
 * 
 * @author chenbug
 *
 * @version $Id: AnnotationHandler.java, v 0.1 2016年3月2日 下午2:25:03 chenbug Exp $
 */
public interface ValueAnnotationSerializer<T extends Annotation> extends Ordered {

	/**
	 * 处理Object
	 * 
	 * @param value
	 * @param beanProperty
	 * @return
	 */
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty);

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return 0;
	}

}
