/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson;

import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.json.user.ScriptExclude;
import com.bench.lang.base.json.user.ScriptFieldMode;
import com.bench.lang.base.json.user.ScriptInclude;
import com.bench.lang.base.json.user.enums.ScriptFieldModeEnum;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author cold
 *
 * @version $Id: BenchJacksonVisibilityChecker.java, v 0.1 2016年3月2日 下午12:13:12
 *          cold Exp $
 */
public class BenchJacksonVisibilityChecker implements VisibilityChecker<BenchJacksonVisibilityChecker> {

	private Map<Method, Boolean> visibilityCheckCacheMap = new ConcurrentHashMap<Method, Boolean>();

	private Map<Field, Boolean> visibilityFieldCheckCacheMap = new ConcurrentHashMap<Field, Boolean>();

	/**
	 * This is the canonical base instance, configured with default visibility
	 * values
	 */
	/**
	 * 1、public的set和get，包括is<br>
	 * 2、不处理public的Field <br>
	 * 3、处理public构造函数
	 */
	protected final static BenchJacksonVisibilityChecker DEFAULT = new BenchJacksonVisibilityChecker(Visibility.PUBLIC_ONLY, Visibility.PUBLIC_ONLY,
			Visibility.PUBLIC_ONLY, Visibility.PUBLIC_ONLY, Visibility.NONE);

	protected final Visibility _getterMinLevel;
	protected final Visibility _isGetterMinLevel;
	protected final Visibility _setterMinLevel;
	protected final Visibility _creatorMinLevel;
	protected final Visibility _fieldMinLevel;

	public static BenchJacksonVisibilityChecker defaultInstance() {
		return DEFAULT;
	}

	/**
	 * Constructor used for building instance that has minumum visibility levels
	 * as indicated by given annotation instance
	 * 
	 * @param ann
	 *            Annotations to use for determining minimum visibility levels
	 */
	public BenchJacksonVisibilityChecker(JsonAutoDetect ann) {
		// let's combine checks for enabled/disabled, with minimimum level
		// checks:
		_getterMinLevel = ann.getterVisibility();
		_isGetterMinLevel = ann.isGetterVisibility();
		_setterMinLevel = ann.setterVisibility();
		_creatorMinLevel = ann.creatorVisibility();
		_fieldMinLevel = ann.fieldVisibility();
	}

	/**
	 * Constructor that allows directly specifying minimum visibility levels to
	 * use
	 */
	public BenchJacksonVisibilityChecker(Visibility getter, Visibility isGetter, Visibility setter, Visibility creator, Visibility field) {
		_getterMinLevel = getter;
		_isGetterMinLevel = isGetter;
		_setterMinLevel = setter;
		_creatorMinLevel = creator;
		_fieldMinLevel = field;
	}

	/**
	 * Constructor that will assign given visibility value for all properties.
	 * 
	 * @param v
	 *            level to use for all property types
	 */
	public BenchJacksonVisibilityChecker(Visibility v) {
		// typically we shouldn't get this value; but let's handle it if we do:
		if (v == Visibility.DEFAULT) {
			_getterMinLevel = DEFAULT._getterMinLevel;
			_isGetterMinLevel = DEFAULT._isGetterMinLevel;
			_setterMinLevel = DEFAULT._setterMinLevel;
			_creatorMinLevel = DEFAULT._creatorMinLevel;
			_fieldMinLevel = DEFAULT._fieldMinLevel;
		} else {
			_getterMinLevel = v;
			_isGetterMinLevel = v;
			_setterMinLevel = v;
			_creatorMinLevel = v;
			_fieldMinLevel = v;
		}
	}

	/*
	 * /******************************************************** /*
	 * Builder/fluent methods for instantiating configured /* instances
	 * /********************************************************
	 */

	@Override
	public BenchJacksonVisibilityChecker with(JsonAutoDetect ann) {
		BenchJacksonVisibilityChecker curr = this;
		if (ann != null) {
			curr = curr.withGetterVisibility(ann.getterVisibility());
			curr = curr.withIsGetterVisibility(ann.isGetterVisibility());
			curr = curr.withSetterVisibility(ann.setterVisibility());
			curr = curr.withCreatorVisibility(ann.creatorVisibility());
			curr = curr.withFieldVisibility(ann.fieldVisibility());
		}
		return curr;
	}

	@Override
	public BenchJacksonVisibilityChecker with(Visibility v) {
		if (v == Visibility.DEFAULT) {
			return DEFAULT;
		}
		return new BenchJacksonVisibilityChecker(v);
	}

	@Override
	public BenchJacksonVisibilityChecker withVisibility(PropertyAccessor method, Visibility v) {
		switch (method) {
		case GETTER:
			return withGetterVisibility(v);
		case SETTER:
			return withSetterVisibility(v);
		case CREATOR:
			return withCreatorVisibility(v);
		case FIELD:
			return withFieldVisibility(v);
		case IS_GETTER:
			return withIsGetterVisibility(v);
		case ALL:
			return with(v);
		// case NONE:
		default:
			// break;
			return this;
		}
	}

	@Override
	public BenchJacksonVisibilityChecker withGetterVisibility(Visibility v) {
		if (v == Visibility.DEFAULT)
			v = DEFAULT._getterMinLevel;
		if (_getterMinLevel == v)
			return this;
		return new BenchJacksonVisibilityChecker(v, _isGetterMinLevel, _setterMinLevel, _creatorMinLevel, _fieldMinLevel);
	}

	@Override
	public BenchJacksonVisibilityChecker withIsGetterVisibility(Visibility v) {
		if (v == Visibility.DEFAULT)
			v = DEFAULT._isGetterMinLevel;
		if (_isGetterMinLevel == v)
			return this;
		return new BenchJacksonVisibilityChecker(_getterMinLevel, v, _setterMinLevel, _creatorMinLevel, _fieldMinLevel);
	}

	@Override
	public BenchJacksonVisibilityChecker withSetterVisibility(Visibility v) {
		if (v == Visibility.DEFAULT)
			v = DEFAULT._setterMinLevel;
		if (_setterMinLevel == v)
			return this;
		return new BenchJacksonVisibilityChecker(_getterMinLevel, _isGetterMinLevel, v, _creatorMinLevel, _fieldMinLevel);
	}

	@Override
	public BenchJacksonVisibilityChecker withCreatorVisibility(Visibility v) {
		if (v == Visibility.DEFAULT)
			v = DEFAULT._creatorMinLevel;
		if (_creatorMinLevel == v)
			return this;
		return new BenchJacksonVisibilityChecker(_getterMinLevel, _isGetterMinLevel, _setterMinLevel, v, _fieldMinLevel);
	}

	@Override
	public BenchJacksonVisibilityChecker withFieldVisibility(Visibility v) {
		if (v == Visibility.DEFAULT)
			v = DEFAULT._fieldMinLevel;
		if (_fieldMinLevel == v)
			return this;
		return new BenchJacksonVisibilityChecker(_getterMinLevel, _isGetterMinLevel, _setterMinLevel, _creatorMinLevel, v);
	}

	/*
	 * /******************************************************** /* Public API
	 * impl /********************************************************
	 */

	@Override
	public boolean isCreatorVisible(Member m) {
		return _creatorMinLevel.isVisible(m);
	}

	@Override
	public boolean isCreatorVisible(AnnotatedMember m) {
		return isCreatorVisible(m.getMember());
	}

	@Override
	public boolean isFieldVisible(Field f) {
		// 如果之前已经判断过，则直接返回
		Boolean visible = this.visibilityFieldCheckCacheMap.get(f);
		if (visible != null) {
			return visible;
		}

		// 尝试获取方法所在类的脚本字段模式注解ScriptFieldMode
		ScriptFieldMode fieldModeAnno = f.getDeclaringClass().getAnnotation(ScriptFieldMode.class);
		if (fieldModeAnno == null) {
			return _fieldMinLevel.isVisible(f);
		}
		ScriptFieldModeEnum fieldMode = fieldModeAnno.value();
		// 如果属性Field不为空，则根据字段上的标记ScriptFieldModeEnum来判断
		visible = fieldMode == ScriptFieldModeEnum.INCLUDE && f.getAnnotation(ScriptInclude.class) != null
				|| fieldMode == ScriptFieldModeEnum.EXCLUDE && f.getAnnotation(ScriptExclude.class) == null;
		// 放入缓存map
		visibilityFieldCheckCacheMap.put(f, visible);
		return visible;

	}

	@Override
	public boolean isFieldVisible(AnnotatedField f) {
		return isFieldVisible(f.getAnnotated());
	}

	@Override
	public boolean isGetterVisible(Method m) {
		Boolean visible = isFieldAcessMethodVisiable(m);
		return visible != null ? visible : _getterMinLevel.isVisible(m);
	}

	@Override
	public boolean isGetterVisible(AnnotatedMethod m) {
		return isGetterVisible(m.getAnnotated());
	}

	@Override
	public boolean isIsGetterVisible(Method m) {
		// 如果之前已经判断过，则直接返回
		Boolean visible = isFieldAcessMethodVisiable(m);
		return visible != null ? visible : _isGetterMinLevel.isVisible(m);
	}

	private Boolean isFieldAcessMethodVisiable(Method m) {
		Boolean visible = visibilityCheckCacheMap.get(m);
		if (visible != null) {
			return visible;
		}
		// 尝试获取属性名
		String fieldName = FieldUtils.getFieldNameByGetterMethod(m.getName());
		// 如果属性名不为空
		if (!StringUtils.isEmpty(fieldName)) {
			try {
				// 尝试获取方法所在类的脚本字段模式注解ScriptFieldMode
				ScriptFieldMode fieldModeAnno = m.getDeclaringClass().getAnnotation(ScriptFieldMode.class);
				// 默认是不包含
				ScriptFieldModeEnum fieldMode = ScriptFieldModeEnum.EXCLUDE;
				// 如果不为空，说明字段上表明了注解
				if (fieldModeAnno != null) {
					fieldMode = fieldModeAnno.value();
				}
				// 尝试获取属性Field
				Field field = FieldUtils.getField(m.getDeclaringClass(), fieldName);
				// 如果属性Field不为空，则根据字段上的标记ScriptFieldModeEnum来判断
				if (field != null) {
					visible = fieldMode == ScriptFieldModeEnum.INCLUDE && field.getAnnotation(ScriptInclude.class) != null
							|| fieldMode == ScriptFieldModeEnum.EXCLUDE && field.getAnnotation(ScriptExclude.class) == null;
					// 放入缓存map
					visibilityCheckCacheMap.put(m, visible);
					return visible;
				}

			} catch (NoSuchFieldException e) {

			}
		}
		return null;
	}

	@Override
	public boolean isIsGetterVisible(AnnotatedMethod m) {
		return isIsGetterVisible(m.getAnnotated());
	}

	@Override
	public boolean isSetterVisible(Method m) {
		return _setterMinLevel.isVisible(m);
	}

	@Override
	public boolean isSetterVisible(AnnotatedMethod m) {
		return isSetterVisible(m.getAnnotated());
	}

	/*
	 * /******************************************************** /* Standard
	 * methods /********************************************************
	 */

	@Override
	public String toString() {
		return new StringBuilder("[Visibility:").append(" getter: ").append(_getterMinLevel).append(", isGetter: ").append(_isGetterMinLevel).append(", setter: ")
				.append(_setterMinLevel).append(", creator: ").append(_creatorMinLevel).append(", field: ").append(_fieldMinLevel).append("]").toString();
	}

	@Override
	public BenchJacksonVisibilityChecker withOverrides(Value vis) {
		// TODO Auto-generated method stub
		return null;
	}
}
