package com.bench.lang.base.json.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 输出金额LevelString
 * 
 * @author cold
 *
 * @version $Id: MoneyLevelString.java, v 0.1 2015年1月28日 下午3:50:45 cold Exp $
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MoneyLevelString {

}
