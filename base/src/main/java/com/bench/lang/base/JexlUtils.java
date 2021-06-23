package com.bench.lang.base;

import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.velocity.VelocityHelper;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JexlUtils {
	private static final Logger log = LoggerFactory.getLogger(JexlUtils.class);
	private static final JexlEngine jexl = new JexlEngine();
	static {
		jexl.setCache(512);
		jexl.setLenient(false);
		jexl.setSilent(false);
	}

	public static Expression createExpression(String expression) {
		try {
			return jexl.createExpression(expression);
		} catch (Exception e) {
			log.error("解析表达式异常：" + expression, e);
			return null;
		}
	}

	public static JexlContext getDefaultJexlContext() {
		JexlContext jexlContext = new MapContext();
		// 放入公用辅助参数
		for (Map.Entry<String, Object> commonEntry : VelocityHelper.getCommonContextMap().entrySet()) {
			jexlContext.set(commonEntry.getKey(), commonEntry.getValue());
		}
		return jexlContext;
	}

	/**
	 * @param expression
	 * @param jexlContext
	 * @return
	 */
	public static Object evaluate(String expression, JexlContext jexlContext) {
		Expression exp = jexl.createExpression(expression);
		try {
			return exp.evaluate(jexlContext);
		} catch (Exception e) {
			throw new RuntimeException("执行表达式异常" + e.getMessage() + "]", e);
		}
	}

	public static Object evaluate(String expression, Map<? extends Object, ? extends Object> parameters) {
		JexlContext jexlContext = getDefaultJexlContext();
		for (Map.Entry<? extends Object, ? extends Object> entry : parameters.entrySet()) {
			jexlContext.set(ObjectUtils.toString(entry.getKey()), entry.getValue());
		}

		Expression exp = jexl.createExpression(expression);
		try {
			return exp.evaluate(jexlContext);
		} catch (Exception e) {
			throw new RuntimeException("执行表达式异常" + e.getMessage() + "]", e);
		}

	}

	public static void main(String[] args) {
			System.out.println("test");
	}
}
