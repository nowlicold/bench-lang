package com.bench.lang.base.instance.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示当前类可以作为单例使用
 * 
 * @author cold
 *
 * @version $Id: Singleton.java, v 0.1 2020年4月8日 上午10:53:11 cold Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface Singleton {

}
