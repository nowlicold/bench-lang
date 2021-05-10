package com.bench.lang.base.dependon.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 依赖注解
 * 
 * @author cold
 *
 * @version $Id: DependsOned.java, v 0.1 2020年6月9日 上午11:42:32 cold Exp $
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface DependsOned {

	Class<?>[] dependsOn();
}
