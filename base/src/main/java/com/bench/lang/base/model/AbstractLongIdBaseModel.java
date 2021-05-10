package com.bench.lang.base.model;

/**
 * 抽象的基本模型
 * 
 * @author cold
 * 
 * @version $Id: AbstractLongIdBaseModel.java, v 0.1 2013-10-22 下午3:32:50 cold Exp $
 */
public abstract class AbstractLongIdBaseModel extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2354799813059603726L;

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
