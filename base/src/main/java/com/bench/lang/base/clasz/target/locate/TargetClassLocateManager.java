package com.bench.lang.base.clasz.target.locate;

import java.util.List;

import com.bench.lang.base.clasz.target.locate.locator.TargetClassLocator;
import com.bench.lang.base.instance.BenchInstanceFactory;
import com.bench.lang.base.instance.annotations.Singleton;
import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;

/**
 * 目标class定位管理器，定位真正的class ，因为class可能被AOP等代理或者封装
 * 
 * @author cold
 *
 * @version $Id: TargetClassLocateManager.java, v 0.1 2020年6月12日 上午9:27:03 cold Exp $
 */
@Singleton
public class TargetClassLocateManager {

	private List<TargetClassLocator> locators;

	/**
	 * @return
	 */
	public static final TargetClassLocateManager getInstance() {
		return BenchInstanceFactory.getInstance(TargetClassLocateManager.class);
	}

	/**
	 */
	private TargetClassLocateManager() {
		super();
		this.locators = BenchInstanceFactory.getAll(TargetClassLocator.class);
	}

	/**
	 * 定位claz
	 * 
	 * @return
	 */
	public TargetClassLocateResult locate(Object object) {
		// 如果没有定位器，直接返回
		if (locators.size() == 0) {
			TargetClassLocateResult result = new TargetClassLocateResult();
			result.setTargetClass(object.getClass());
			result.setTargetObject(object);
			return result;
		}
		Class<?> currentClass = object.getClass();
		Object currentObject = object;
		// 为了防止死循环，最多解码10次
		for (int i = 0; i < 10; i++) {
			// 本轮循环的结果
			TargetClassLocateResult locatedResult = null;
			// 本轮解码的起始类
			Class<?> beginClass = currentClass;
			for (TargetClassLocator locator : locators) {
				// 尝试解码
				locatedResult = locator.locate(currentObject, currentClass);
				// 解码成功,将当前类设置为解码后的类
				if (locatedResult != null && locatedResult.getTargetClass() != null && locatedResult.getTargetClass() != currentClass) {
					currentClass = locatedResult.getTargetClass();
					currentObject = locatedResult.getTargetObject();
				}
			}
			// 如果解码后，类没有变更，本轮没有解码，说明已经全部解码成功了
			if (currentClass == beginClass) {
				if (locatedResult == null) {
					locatedResult = new TargetClassLocateResult();
					locatedResult.setTargetClass(currentClass);
					locatedResult.setTargetObject(currentObject);
				}
				return locatedResult;
			}
		}
		throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"定位targetClass失败，超过最大循环次数");
	}
}
