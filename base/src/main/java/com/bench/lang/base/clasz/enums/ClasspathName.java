package com.bench.lang.base.clasz.enums;

/**
 * Claspath名称<Br>
 * 所有的classpath都要实现这个接口，并定义为枚举，方便后续维护
 * 
 * @author cold
 *
 * @version $Id: ClasspathName.java, v 0.1 2020年5月19日 下午6:32:05 cold Exp $
 */
public interface ClasspathName {

	/**
	 * 名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 路径
	 * 
	 * @return
	 */
	String[] paths();
}
