package com.bench.lang.base.clasz.visit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 类访问
 * 
 * @author cold
 *
 * @version $Id: ClassVisitor.java, v 0.1 2017年4月19日 下午10:10:29 cold Exp $
 */
public interface ClassVisitor extends SimpleClassVisitor {

	/**
	 * 访问到一个方法
	 * 
	 * @param method
	 */
	public void visitMethod(Method method);

	/**
	 * 访问到一个属性
	 * 
	 * @param field
	 */
	public void visitField(Field field);
}
