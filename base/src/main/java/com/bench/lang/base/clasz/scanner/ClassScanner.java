package com.bench.lang.base.clasz.scanner;

import java.util.Set;

import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.bench.lang.base.accept.Acceptor;
import com.bench.lang.base.clasz.visit.ClassMetadataVisitor;
import com.bench.lang.base.clasz.visit.SimpleClassVisitor;

/**
 * 类扫描器
 * 
 * @author cold
 *
 * @version $Id: ClassScanner.java, v 0.1 2018年10月12日 上午10:19:08 cold Exp $
 */
public class ClassScanner {

	private static ClassScannerInner innerInstance = new ClassScannerInner();

	public static Set<Class<?>> scan(String[] basePackages, Acceptor<MetadataReader> classAcceptor) {
		return innerInstance.scan(basePackages, classAcceptor);
	}

	public static Set<Class<?>> scan(String basePackage, Acceptor<MetadataReader> classAcceptor) {
		return innerInstance.scan(basePackage, classAcceptor);
	}

	public static void scan(String basePackage, Acceptor<MetadataReader> classAcceptor, SimpleClassVisitor visitor) {
		innerInstance.scan(basePackage, classAcceptor, visitor);
	}

	public static void scan(String[] basePackages, Acceptor<MetadataReader> classAcceptor, SimpleClassVisitor visitor) {
		innerInstance.scan(basePackages, classAcceptor, visitor);
	}

	public static void scan(String basePackage, SimpleClassVisitor visitor) {
		innerInstance.scan(basePackage, visitor);
	}

	public static void scan(String[] basePackages, SimpleClassVisitor visitor) {
		innerInstance.scan(basePackages, visitor);
	}

	public static void scan(String basePackage, ClassMetadataVisitor visitor) {
		innerInstance.scan(basePackage, visitor);
	}

	public static ResourcePatternResolver getResourcePatternResolver() {
		return innerInstance.getResourcePatternResolver();
	}

	public static MetadataReaderFactory getMetadataReaderFactory() {
		return innerInstance.getMetadataReaderFactory();
	}
}