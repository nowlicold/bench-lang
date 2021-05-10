package com.bench.lang.base.model;

/**
 * 含有Id的对象
 * 
 * @author cold
 * 
 * @version $Id: IdObject.java, v 0.1 2014-3-4 下午3:51:25 cold Exp $
 */
public interface IdObject<IDT> {

	public IDT getId();

	public void setId(IDT id);
}
