package com.bench.lang.base;

import java.lang.annotation.*;

/**
 * 可以为null的注解
 * 
 * @author cold
 *
 * @version $Id: Nullable.java, v 0.1 2020年5月29日 上午11:53:20 cold Exp $
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nullable {
}
