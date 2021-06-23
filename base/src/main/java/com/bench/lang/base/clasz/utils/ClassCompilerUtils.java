package com.bench.lang.base.clasz.utils;

import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 类编译的工具类。
 * 
 * @author cold
 *
 * @version $Id: ClassCompilerUtils.java, v 0.1 2018年10月25日 下午7:21:57 cold Exp $
 */
public class ClassCompilerUtils {

	/**
	 * 获取编译目录
	 * 
	 * @return
	 */
	public static String getCompilerDir() {
		try {
			File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
			return classPath.getAbsolutePath() + File.separator;
		} catch (URISyntaxException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "获取编译目录异常", e);
		}
	}

	/**
	 * @param className
	 * @param sourceCode
	 * @return
	 */
	public static Class<?> compile(String className, String sourceCode) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		StringSourceJavaObject srcObject = new StringSourceJavaObject(className, sourceCode);
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);
		String flag = "-d";
		String outDir = getCompilerDir();
		Iterable<String> options = Arrays.asList(flag, outDir);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
		boolean result = task.call();

		if (result == true) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "动态编译类异常,className" + className + ",sourceCode=" + sourceCode, e);
			}
		} else {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "动态编译类异常,className" + className + ",sourceCode=" + sourceCode);
		}
	}

	private static class StringSourceJavaObject extends SimpleJavaFileObject {
		private String content;

		public StringSourceJavaObject(String name, String content) {
			super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.content = content;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return content;
		}
	}

}
