/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.string.build.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表明当前Field在ToString时，计算MD5值作为ToString的结果
 * 
 * @author cold
 *
 * @version $Id: ToStringMD5.java, v 0.1 2018年10月17日 下午3:05:00 cold Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface ToStringMD5 {

}
