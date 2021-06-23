package com.bench.lang.base.json.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期转成成 "yyyyMMddHHmmssSSS"
 * 
 * @author chenbug
 *
 * @version $Id: LongExtraDateFormat.java, v 0.1 2016年12月14日 上午11:16:46 chenbug
 *          Exp $
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LongExtraDateFormat {

}
