package com.bench.lang.base.clasz.visit;

/**
 * 类访问
 * 
 * @author cold
 *
 * @version $Id: ClassVisitor.java, v 0.1 2017年4月19日 下午10:10:29 cold Exp $
 */
public interface SimpleClassVisitor {

	/**
	 * 访问到一个类
	 * 
	 * @param clasz
	 */
	public void visitClass(Class<?> clasz);

}
