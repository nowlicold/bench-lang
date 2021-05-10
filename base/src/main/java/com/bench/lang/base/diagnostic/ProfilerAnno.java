package com.bench.lang.base.diagnostic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfilerAnno {

	/**
	 * 所有需要执行Profiler的方法名，多个用逗号间隔
	 * 
	 * @return
	 */
	public String methodNames() default "";
}
