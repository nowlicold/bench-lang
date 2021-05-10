package com.bench.lang.base.refelect.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * @author Uri Boness
 */
public class ReflectionUtils {

	/**
	 * Attempts to find a {@link Method} on the supplied type with the supplied
	 * name and parameter types. Searches all superclasses up to
	 * <code>Object</code>. Returns '<code>null</code>' if no {@link Method} can
	 * be found. supports all visibiliy levels (public, private, protected and
	 * default).
	 */
	public static Method findMethod(Class type, String name, Class[] paramTypes) {
		Assert.notNull(type, "'type' cannot be null.");
		Assert.notNull(name, "'name' cannot be null.");
		Class searchType = type;
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] methods = (type.isInterface() ? searchType.getMethods() : searchType
					.getDeclaredMethods());
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (name.equals(method.getName()) && Arrays.equals(paramTypes, method.getParameterTypes())) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Attempts to find a {@link Method} on the supplied type assuming it
	 * accepts no arguments. Searches all superclasses up to <code>Object</code>
	 * . Returns '<code>null</code>' if no {@link Method} can be found. supports
	 * all visibiliy levels (public, private, protected and default).
	 */
	public static Method findMethod(Class type, String name) {
		return findMethod(type, name, new Class[0]);
	}

	/**
	 * Returns a list of all methods (of all access level - public, protected,
	 * private, default) in the hierarchy of the given class.
	 * 
	 * @param type
	 *            The given class
	 */
	public static Method[] getAllMethods(Class type) {
		Assert.notNull(type, "'type' cannot be null.");
		Map methodBySigniture = new HashMap();
		Class searchType = type;
		while (!Object.class.equals(searchType) && searchType != null) {
			Method[] typeMethods = (type.isInterface() ? searchType.getMethods() : searchType
					.getDeclaredMethods());
			for (int i = 0; i < typeMethods.length; i++) {
				Method method = typeMethods[i];
				String sig = getSignature(method);
				if (!methodBySigniture.containsKey(sig)) {
					methodBySigniture.put(sig, method);
				}
			}
			searchType = searchType.getSuperclass();
		}
		return (Method[]) methodBySigniture.values().toArray(new Method[methodBySigniture.size()]);
	}

	public static String getSignature(Method method) {
		String name = method.getName();
		Class returnType = method.getReturnType();
		Class[] paramTypes = method.getParameterTypes();
		StringBuffer buffer = new StringBuffer();
		buffer.append(name).append("(");
		for (int i = 0; i < paramTypes.length; i++) {
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append(paramTypes[i].getName());
		}
		buffer.append(")").append(returnType.getName());
		return buffer.toString();
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object
	 * with no arguments. The target object can be <code>null</code> when
	 * invoking a static {@link Method}.
	 * <p>
	 * Thrown exceptions are handled via a call to
	 * {@link #handleReflectionException}.
	 * 
	 * @param method
	 *            the method to invoke
	 * @param target
	 *            the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, null);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object
	 * with the supplied arguments. The target object can be <code>null</code>
	 * when invoking a static {@link Method}.
	 * <p>
	 * Thrown exceptions are handled via a call to
	 * {@link #handleReflectionException}.
	 * 
	 * @param method
	 *            the method to invoke
	 * @param target
	 *            the target object to invoke the method on
	 * @param args
	 *            the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object[] args) {
		try {
			return method.invoke(target, args);
		} catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * 
	 * @param ex
	 *            the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an IllegalStateException else.
	 * 
	 * @param ex
	 *            the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Throws an IllegalStateException with the given exception as root cause.
	 * 
	 * @param ex
	 *            the unexpected exception
	 */
	private static void handleUnexpectedException(Throwable ex) {
		// Needs to avoid the chained constructor for JDK 1.4 compatibility.
		IllegalStateException isex = new IllegalStateException("Unexpected exception thrown");
		isex.initCause(ex);
		throw isex;
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>
	 * Rethrows the underlying exception cast to an {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * 
	 * @param ex
	 *            the exception to rethrow
	 * @throws RuntimeException
	 *             the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}
}
