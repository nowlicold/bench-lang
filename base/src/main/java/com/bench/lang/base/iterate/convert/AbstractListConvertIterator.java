/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.iterate.convert;

import java.util.List;

/**
 * List方式的转换迭代
 * 
 * @author cold
 *
 * @version $Id: AbstractListConvertIterator.java, v 0.1 2019年8月27日 下午6:33:14 cold Exp $
 */
public abstract class AbstractListConvertIterator<FROM, TO> implements ConvertIterator<FROM, TO> {

	/**
	 * 源数据列表
	 */
	private List<FROM> fromList;

	/**
	 * 索引
	 */
	private int index = 0;

	public AbstractListConvertIterator(List<FROM> fromList) {
		super();
		this.fromList = fromList;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return index < fromList.size();
	}

	@Override
	public TO next() {
		// TODO Auto-generated method stub
		FROM from = fromList.get(index);
		index++;
		TO to = convert(from);
		return to;
	}

}
