package com.bench.lang.base.convert.enums;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串切割注解
 * 
 * @author cold
 *
 * @version $Id: StringSpliter.java, v 0.1 2018年6月22日 下午12:56:00 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StringSplitter {
	/**
	 * joinner集合，如逗号，冒号，换行回车等，默认是逗号和换行回车
	 * 
	 * @return
	 */
	String[] values() default { ",", "\r\n", "\r", "\n" };
}
