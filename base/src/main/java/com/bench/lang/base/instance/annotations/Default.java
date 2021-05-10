package com.bench.lang.base.instance.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.lang.base.order.Ordered;

/**
 * 默认实例，当有多个类实现了接口时，如果某个实现类上有这个注解<br>
 * ，则通过BenchInstanceFactory的getDefault方法获取实例时，返回包含这个注解的类实例<br>
 * 这是为了从框架层面控制默认实现类，防止扩展包中的实现类影响。<br>
 * 如果要获取扩展包中的实现类实例，可以通过BenchInstanceFactory的getByName方法<br>
 * 
 * @author cold
 *
 * @version $Id: Ordered.java, v 0.1 2018年3月15日 上午11:54:16 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Default {

	int value() default Ordered.DEFAULT_ORDER;
}
