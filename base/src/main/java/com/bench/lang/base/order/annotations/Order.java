package com.bench.lang.base.order.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.lang.base.order.Ordered;

/**
 * 排序的注解
 * 
 * @author cold
 *
 * @version $Id: Ordered.java, v 0.1 2018年3月15日 上午11:54:16 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Order {

	int value() default Ordered.DEFAULT_ORDER;
}
