package com.bench.lang.base.context;

/**
 * 上下文
 * 
 * @author cold
 * @version $Id: ResultBase.java,v 0.1 2009-7-15 上午09:11:18 cold Exp $
 */
public interface Context<P extends Context<P>> {

	/**
	 * 得到父上下文
	 * 
	 * @return
	 */
	P getParent();

	/**
	 * 根据name获取属性
	 * 
	 * @param id
	 * @return
	 */
	Object getAttribute(String name);

	/**
	 * 放入属性
	 * 
	 * @param name
	 * @param value
	 */
	void setAttribute(String name, Object value);

	/**
	 * 移除属性
	 * 
	 * @param name
	 * @return
	 */
	Object removeAttribute(String name);

}
