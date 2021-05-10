package com.bench.lang.base.model;

import java.util.Date;

/**
 * 抽象的基本模型
 * 
 * @author cold
 * 
 * @version $Id: AbstractBaseModel.java, v 0.1 2013-10-22 下午3:32:50 cold Exp $
 */
public class AbstractBaseModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2354799813059603726L;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
