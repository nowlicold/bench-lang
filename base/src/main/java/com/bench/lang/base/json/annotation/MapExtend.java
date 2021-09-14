package com.bench.lang.base.json.annotation;

import java.lang.annotation.*;

/**
 * 当定义一个类,继承自Map,但是又添加了额外的属性,<br>
 * 如果需要在JSON中输出这些属性, 可以在类上加这个注解,<br>
 * 否则,JSON将当做普通Map处理，忽略自定义的属性
 * 
 * @author cold
 * 
 * @version $Id: MapExtend.java, v 0.1 2013-11-6 下午4:12:31 cold Exp $
 */
@Target({ ElementType.TYPE })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MapExtend {

}
