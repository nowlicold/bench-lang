/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bench.lang.base.json.utils;


import com.bench.lang.base.json.user.LowerCaseName;
import com.bench.lang.base.json.user.ScriptName;
import com.bench.lang.base.string.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Provides useful methods on java objects and JSON values.
 * 
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 * @version 7
 */
public final class JSONUtils {

	public static String getScriptName(Field field, String defaultName) {
		if (field == null)
			return defaultName;
		ScriptName scriptName = field.getAnnotation(ScriptName.class);
		LowerCaseName lowerCase = field.getAnnotation(LowerCaseName.class);
		if (lowerCase == null) {
			lowerCase = field.getDeclaringClass().getAnnotation(LowerCaseName.class);
		}
		String name = null;
		if (scriptName == null) {
			name = field.getName();
		} else {
			name = scriptName.value();
		}
		return lowerCase == null ? name : StringUtils.toLowerCase(name);
	}

	public static String getScriptName(Method method, String defaultName) {
		if (method == null)
			return defaultName;
		ScriptName scriptName = (ScriptName) method.getAnnotation(ScriptName.class);
		LowerCaseName lowerCase = method.getAnnotation(LowerCaseName.class);
		if (lowerCase == null) {
			lowerCase = method.getDeclaringClass().getAnnotation(LowerCaseName.class);
		}
		String name = null;
		if (scriptName == null) {
			name = method.getName();
		} else {
			name = scriptName.value();
		}
		return lowerCase == null ? name : StringUtils.toLowerCase(name);
	}

}