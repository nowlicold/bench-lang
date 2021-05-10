package com.bench.lang.base.pull.tools.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.lang.base.string.StringConstants;

/**
 * 
 * 表明这是一个可以Pulll的工具类
 * 
 * @author cold
 *
 * @version $Id: PullToolSingleton.java, v 0.1 2018年2月24日 下午4:16:54 cold Exp $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PullTool {

	/**
	 * key名称，如果为空，则使用类名首字母小写<br>
	 * 
	 * @return
	 */
	String value() default StringConstants.EMPTY_STRING;
}
