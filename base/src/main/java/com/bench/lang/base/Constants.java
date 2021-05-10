package com.bench.lang.base;

import java.nio.charset.Charset;

/**
 * 常量
 * 
 * @author cold
 *
 * @version $Id: Constants.java, v 0.1 2018年4月7日 下午5:32:11 cold Exp $
 */
public interface Constants {

	/**
	 * 默认字符集
	 */
	String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 默认字符集
	 */
	Charset DEFAULT_CHARSET_OBJECT = Charset.forName(DEFAULT_CHARSET);

	/**
	 * bench的版本
	 */
	String VERSION = "1.3";

	/**
	 * 默认
	 */
	String DEFAULT_STRING = "DEFAULT";

	/**
	 * 全匹配
	 */
	String ALL_MATCH = "*";

	/**
	 * bench java包前缀
	 */
	String BENCH_JAVA_PACKAGE_PREFIX = "com.bench";

	/**
	 * bench平台代码
	 */
	String BENCH_PLATFORM_CODE = "bench";

	/**
	 * 应用的包开头
	 */
	String APP_PACKAGE_START_WITH = "com.bench.app.";

	/**
	 * 脚本中表示接受的变量名
	 */
	String SCRIPT_ACCPET_VAR_NAME = "accept";

	/**
	 * 未知
	 */
	String UNKNOWN = "UNKNOWN";

	/** Resource-Filter 属性，用于是否使用velocity作为资源的过滤器 */
	String MANIFEST_RESOURCE_FILTER = "Resource-Filter";

	/**
	 * bench组件名称
	 */
	String MANIFEST_COMPONENT_NAME = "Bench-Component-Name";

	/**
	 * bench的web路径，如果有值，则表明当前bundle是一个webbundle
	 */
	String MANITFEST_WEB_PATH = "Bench-Web-Path";
}
