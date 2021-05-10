package com.bench.lang.base.context;

import java.util.HashMap;
import java.util.Map;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 抽象的上下文
 * 
 * @author cold
 * @version $Id: AbstractContext.java,v 0.1 2009-7-15 上午09:11:18 cold Exp $
 */
public abstract class AbstractContext<P extends AbstractContext<P>> extends ToStringObject implements Context<P> {

	/**
	 * 父上下文
	 */
	private P parentContext;

	/**
	 * 属性Map
	 */
	private Map<String, Object> attributeMap = new HashMap<String, Object>();

	public AbstractContext() {
		super();
	}

	public AbstractContext(P parentContext) {
		super();
		this.parentContext = parentContext;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return attributeMap.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		attributeMap.put(name, value);
	}

	@Override
	public Object removeAttribute(String name) {
		// TODO Auto-generated method stub
		return attributeMap.remove(name);
	}

	public P getParentContext() {
		return parentContext;
	}

}
