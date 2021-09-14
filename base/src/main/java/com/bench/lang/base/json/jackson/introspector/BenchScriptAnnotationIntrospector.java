/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.introspector;

import com.bench.lang.base.json.enums.ScriptNameModeEnum;
import com.bench.lang.base.json.user.LowerCaseName;
import com.bench.lang.base.json.user.ScriptName;
import com.bench.lang.base.json.user.ScriptNameMode;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

import java.lang.annotation.Annotation;

/**
 * 
 * @author cold
 *
 * @version $Id: BenchScriptAnnotationIntrospector.java, v 0.1 2016年3月1日 下午4:57:29 cold Exp $
 */
public class BenchScriptAnnotationIntrospector extends AnnotationIntrospector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3939528620311813649L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.AnnotationIntrospector#version()
	 */
	@Override
	public Version version() {
		// TODO Auto-generated method stub
		return Version.unknownVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.AnnotationIntrospector# findNameForSerialization (com.fasterxml.jackson.databind.introspect.Annotated)
	 */
	@Override
	public PropertyName findNameForSerialization(Annotated a) {
		// TODO Auto-generated method stub
		PropertyName propertyName = getNameFromScriptNameAnnotation(a);
		if (propertyName == null) {
			propertyName = super.findNameForSerialization(a);
		}
		return getWrappedPropertyName(a, propertyName);
	}

	/**
	 * 查找类中的枚举，而非单单Field或者Method
	 * 
	 * @param annotated
	 * @param annoClass
	 * @return
	 */
	protected <A extends Annotation> A _findClassAnnotation(Annotated annotated, Class<A> annoClass) {
		if (annotated instanceof AnnotatedField) {
			AnnotatedField annotatedField = ((AnnotatedField) annotated);
			return annotatedField.getAnnotated().getDeclaringClass().getAnnotation(annoClass);
		} else if (annotated instanceof AnnotatedMethod) {
			AnnotatedMethod annotatedMethod = ((AnnotatedMethod) annotated);
			return annotatedMethod.getAnnotated().getDeclaringClass().getAnnotation(annoClass);
		}
		return null;
	}

	/**
	 * 从ScriptName中获取变量名
	 * 
	 * @param a
	 * @return
	 */
	private PropertyName getNameFromScriptNameAnnotation(Annotated a) {
		// 根据变量名获取
		ScriptName scriptName = _findAnnotation(a, ScriptName.class);
		if (scriptName != null) {
			return PropertyName.construct(scriptName.value());
		}
		// 根据变量名模式获取
		ScriptNameMode scriptNameMode = _findAnnotation(a, ScriptNameMode.class);
		if (a instanceof AnnotatedField && scriptNameMode == null) {
			scriptNameMode = _findClassAnnotation(a, ScriptNameMode.class);
		}
		if (scriptNameMode != null) {
			if (scriptNameMode.value() == ScriptNameModeEnum.LOWER_CASE_WITH_UNDERSCORES) {
				return PropertyName.construct(StringUtils.toLowerCaseWithUnderscores(a.getName()));
			}
		}
		return null;
	}

	private PropertyName getWrappedPropertyName(Annotated a, PropertyName propertyName) {
		if (propertyName == null) {
			return null;
		}
		String simpleName = propertyName.getSimpleName();
		LowerCaseName lowerCase = _findAnnotation(a, LowerCaseName.class);
		if (lowerCase != null) {
			simpleName = StringUtils.toLowerCase(propertyName.getSimpleName());
		}
		propertyName = PropertyName.construct(simpleName, propertyName.getNamespace());
		return propertyName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.AnnotationIntrospector# findNameForDeserialization (com.fasterxml.jackson.databind.introspect.Annotated)
	 */
	@Override
	public PropertyName findNameForDeserialization(Annotated a) {
		// TODO Auto-generated method stub
		PropertyName propertyName = getNameFromScriptNameAnnotation(a);
		if (propertyName == null) {
			propertyName = super.findNameForSerialization(a);
		}
		return getWrappedPropertyName(a, propertyName);
	}
}
