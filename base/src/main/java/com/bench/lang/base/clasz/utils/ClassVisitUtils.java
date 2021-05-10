package com.bench.lang.base.clasz.utils;

import com.bench.lang.base.clasz.visit.SimpleClassBreakableVisitor;
import com.bench.lang.base.instance.annotations.Singleton;

/**
 * 类访问工具类
 * 
 * @author cold
 *
 * @version $Id: ClassVisitUtils.java, v 0.1 2018年10月25日 下午7:21:57 cold Exp $
 */
@Singleton
public class ClassVisitUtils {

	/**
	 * 访问当前类，父类和接口
	 * 
	 * @param clasz
	 * @param visitor
	 */
	public static void visitCurrentWithSuperclassAndInterface(Class<?> clasz, SimpleClassBreakableVisitor visitor) {
		visitClass(clasz, visitor, true, true);
	}

	/**
	 * 访问当前类，当前类的父类和接口
	 * 
	 * @param clasz
	 * @param visitor
	 * @param allowPackages
	 */
	public static void visitClass(Class<?> clasz, SimpleClassBreakableVisitor visitor, boolean visitSuperclass, boolean visitInterface) {
		// 向上查找自己和父亲
		Class<?> currentClass = clasz;
		while (currentClass != null) {
			// 如果访问结束是break，则结束了
			if (!visitor.visitClass(currentClass)) {
				return;
			}
			// 如果不访问父类，则结束
			if (!visitSuperclass) {
				break;
			}
			currentClass = currentClass.getSuperclass();
		}
		// 访问所有接口
		if (visitInterface) {
			for (Class<?> interfaceClasz : ClassUtils.getInterfaces(clasz)) {
				// 如果访问结束是break，则结束了
				if (!visitor.visitClass(interfaceClasz)) {
					return;
				}
			}
		}
	}

}
