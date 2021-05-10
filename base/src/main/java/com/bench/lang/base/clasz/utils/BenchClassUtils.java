package com.bench.lang.base.clasz.utils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bench.lang.base.accept.Acceptor;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.error.enums.ErrorEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Bench的Class工具类，快捷访问com.bench开头的所有类
 * 
 * @author cold
 *
 * @version $Id: BenchClassUtils.java, v 0.1 2018年9月20日 下午3:43:37 cold Exp $
 */
public class BenchClassUtils {

	public static final String BENCH_PACKAGE_BASE_NAME = "com.bench";

	public static final String BENCH_APP_PACKAGE_BASE_NAME = "com.bench.app";

	private static List<Class<?>> benchClasses = null;
	private static Object benchClassesLock = new Object();

	/**
	 * 获取所有的BenchClass
	 * 
	 * @return
	 */
	public static List<Class<?>> getClasses() {
		if (benchClasses != null) {
			return benchClasses;
		}
		synchronized (benchClassesLock) {
			if (benchClasses != null) {
				return benchClasses;
			}
			benchClasses = Collections.unmodifiableList(ClassUtils.getChildClasses(null, BENCH_PACKAGE_BASE_NAME));
		}
		return benchClasses;
	}

	/**
	 * 获取所有的Bench子类
	 * 
	 * @param parentClass
	 * @return
	 */
	public static List<Class<?>> getChildClasses(Class<?> parentClass) {
		if (!isBenchClass(parentClass.getName())) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "非Bench Class,无法获取子类");
		}
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> clasz : getClasses()) {
			if (parentClass.isAssignableFrom(clasz)) {
				classes.add(clasz);
			}
		}
		return classes;
	}

	/**
	 * 得到bench的包名
	 * 
	 * @return
	 */
	public static String getBenchPackage() {
		return BENCH_PACKAGE_BASE_NAME;
	}

	/**
	 * 得到bench的包名起始值，含有.
	 * 
	 * @return
	 */
	public static String getBenchPackageStart() {
		return getBenchPackage() + StringUtils.DOT_SIGN;
	}

	/**
	 * 得到应用的包名
	 * 
	 * @param appCode
	 * @return
	 */
	public static String getAppPackage(String appCode) {
		return BENCH_APP_PACKAGE_BASE_NAME + StringUtils.DOT_SIGN + appCode;
	}

	/**
	 * 得到应用的包名
	 * 
	 * @param appCode
	 * @return
	 */
	public static String getAppPackage() {
		return BENCH_APP_PACKAGE_BASE_NAME;
	}

	/**
	 * 得到应用的包名
	 * 
	 * @param appCode
	 * @return
	 */
	public static String getAppPackageStart() {
		return getAppPackage() + StringUtils.DOT_SIGN;
	}

	/**
	 * 得到应用包名的起始值
	 * 
	 * @param appCode
	 * @return
	 */
	public static String getAppPackageStart(String appCode) {
		return getAppPackage(appCode) + StringUtils.DOT_SIGN;
	}

	/**
	 * 是否bench平台的类，非app类
	 * 
	 * @param clasz
	 * @return
	 */
	public static boolean isPlatformClass(Class<?> clasz) {
		return isPlatformClass(clasz.getName());
	}

	/**
	 * 是否bench的class，即报名是com.bench开头的
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isBenchClass(String className) {
		return StringUtils.startsWith(className, getBenchPackageStart());
	}

	/**
	 * 是否bench平台的类，非app类
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isPlatformClass(String className) {
		return StringUtils.startsWith(className, getBenchPackageStart()) && !StringUtils.startsWith(className, getAppPackageStart());
	}

	/**
	 * 是否bench应用的类，非平台
	 * 
	 * @param clasz
	 * @return
	 */
	public static boolean isAppClass(Class<?> clasz) {
		return isAppClass(clasz.getName());
	}

	/**
	 * 是否bench应用的类，非平台
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isAppClass(String className) {
		return StringUtils.startsWith(className, getAppPackageStart());
	}

	/**
	 * 是否bench应用的类，非平台
	 * 
	 * @param appCode
	 * @return
	 */
	public static boolean isAppClass(String appCode, String className) {
		return StringUtils.startsWith(className, getAppPackageStart(appCode));
	}

	/**
	 * 是否bench应用的类，非平台
	 * 
	 * @param appCode
	 * @return
	 */
	public static boolean isAppClass(String appCode, Class<?> clasz) {
		return isAppClass(appCode, clasz.getName());
	}

	public static <T> List<T> getInstances(Acceptor<String> classNameAcceptor, Class<T> clasz) {
		return getInstances(classNameAcceptor, clasz, false);
	}

	/**
	 * 获取实例清单
	 * 
	 * @param basePackages
	 *            基础包名
	 * @param classNameFilter
	 *            类名过滤器
	 * @param clasz
	 *            目的类（当前类、父类、或者接口）
	 * @param instanceFirst
	 *            是否查找instance或者INSTANCE
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getInstances(Acceptor<String> classNameAcceptor, Class<T> clasz, boolean instanceFirst) {
		final List<T> returnList = new ArrayList<T>();
		for (Class<?> benchClass : getClasses()) {
			if (Modifier.isAbstract(benchClass.getModifiers())) {
				continue;
			}
			if (benchClass.isInterface()) {
				continue;
			}
			if (classNameAcceptor != null && !classNameAcceptor.accept(benchClass.getName())) {
				continue;
			}
			if (clasz.isAssignableFrom(benchClass)) {
				returnList.add((T) ClassUtils.getInstance(benchClass, instanceFirst));
			}
		}
		return returnList;
	}

	/**
	 * 获取平台的所有错误枚举
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E> & ErrorEnum> List<Class<E>> getPlatformErrorEnums() {
		List<Class<E>> enumClasses = new ArrayList<Class<E>>();
		for (Class<?> clasz : BenchClassUtils.getClasses()) {
			if (!clasz.isEnum()) {
				continue;
			}
			// 不是ErrorEnum的子类
			if (!ErrorEnum.class.isAssignableFrom(clasz)) {
				continue;
			}
			// 不是平台class
			if (!isPlatformClass(clasz)) {
				continue;
			}
			enumClasses.add((Class<E>) clasz);
		}
		return enumClasses;
	}
}
