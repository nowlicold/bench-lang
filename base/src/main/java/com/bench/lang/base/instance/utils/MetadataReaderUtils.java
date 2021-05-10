package com.bench.lang.base.instance.utils;

import java.lang.annotation.Annotation;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.instance.visitor.MetadataReaderVisitor;
import com.bench.lang.base.object.FinalObject;

/**
 * 类源数据工具类
 * 
 * @author cold
 *
 * @version $Id: MetadataReaderUtils.java, v 0.1 2020年4月9日 下午12:27:19 cold Exp $
 */
public class MetadataReaderUtils {

	/**
	 * 访问这个metaData，它的父类，接口等 获取 annotationClass注解
	 * 
	 * @param reader
	 */
	public static <T extends Annotation> MergedAnnotation<T> getAnnotation(MetadataReader reader, Class<T> annotationClass) {
		FinalObject<MergedAnnotation<T>> finalObject = new FinalObject<MergedAnnotation<T>>();
		MetadataReaderVisitUtils.visit(reader, new MetadataReaderVisitor() {
			@Override
			public boolean visit(MetadataReader metadataReader) {
				// TODO Auto-generated method stub
				MergedAnnotation<T> annotation = metadataReader.getAnnotationMetadata().getAnnotations().get(annotationClass);
				if (annotation.isPresent()) {
					finalObject.setObject(annotation);
					return false;
				}
				return true;

			}

		});
		return finalObject.getObject();
	}

}
