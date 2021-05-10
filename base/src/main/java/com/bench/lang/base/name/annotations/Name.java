package com.bench.lang.base.name.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命名注解
 * 
 * @author cold
 *
 * @version $Id: Name.java, v 0.1 2018年3月15日 上午11:54:16 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface Name {

	String[] value();
}
