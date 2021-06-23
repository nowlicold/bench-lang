package com.bench.lang.base.json.value.serializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 转换类
 * 
 * @author chenbug
 * 
 * @version $Id: Converter.java, v 0.1 2012-6-25 下午12:30:46 chenbug Exp $
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

	/**
	 * 返回转换类
	 * 
	 * @return
	 */
	public Class<? extends JSONSerializer> value();
}
