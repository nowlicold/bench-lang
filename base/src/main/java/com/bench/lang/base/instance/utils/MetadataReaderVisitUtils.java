package com.bench.lang.base.instance.utils;

import java.io.IOException;

import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.clasz.scanner.ClassScanner;
import com.bench.lang.base.instance.visitor.MetadataReaderVisitor;

public class MetadataReaderVisitUtils {

	/**
	 * 访问这个metaData，它的父类，接口等
	 * 
	 * @param reader
	 */
	public static void visit(MetadataReader reader, MetadataReaderVisitor visitor) {
		visitInternal(reader, visitor);
	}

	/**
	 * 内部访问
	 * 
	 * @param reader
	 * @param visitor
	 * @return
	 */
	private static boolean visitInternal(MetadataReader reader, MetadataReaderVisitor visitor) {
		if (!visitor.visit(reader)) {
			return false;
		}
		// 父类
		String superClassName = reader.getClassMetadata().getSuperClassName();
		if (superClassName != null) {
			try {
				MetadataReader superClassReader = ClassScanner.getMetadataReaderFactory().getMetadataReader(superClassName);
				if (!visitInternal(superClassReader, visitor)) {
					return false;
				}
			} catch (IOException e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"访问父类metadata异常,superClassName=" + superClassName, e);
			}
		}

		// 接口
		for (String ifc : reader.getClassMetadata().getInterfaceNames()) {
			try {
				MetadataReader interfaceReader = ClassScanner.getMetadataReaderFactory().getMetadataReader(ifc);
				if (!visitInternal(interfaceReader, visitor)) {
					return false;
				}
			} catch (IOException e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"访问接口metadata异常,interfaceClassName=" + ifc, e);
			}
		}
		return true;
	}
}
