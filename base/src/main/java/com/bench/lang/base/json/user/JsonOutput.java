package com.bench.lang.base.json.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 以JSON格式输出
 * 
 * @author cold
 * 
 * @version $Id: JsonFormat.java, v 0.1 2012-11-20 下午3:16:45 cold Exp $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonOutput {
	String contentType() default "application/json; charset=GBK";

}
