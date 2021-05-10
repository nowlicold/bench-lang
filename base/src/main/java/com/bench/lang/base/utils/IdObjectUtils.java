package com.bench.lang.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.bench.lang.base.model.IdObject;

/**
 * 还有ID属性的对象工具类
 * 
 * @author cold
 * 
 * @version $Id: IdObjectUtils.java, v 0.1 2014-3-4 下午3:52:36 cold Exp $
 */
public class IdObjectUtils {
	/**
	 * 返回品牌的id列表
	 * 
	 * @param itemList
	 * @return
	 */
	public static <IDT> List<IDT> getIds(List<IdObject<IDT>> idObjectList) {
		if (idObjectList == null)
			return null;
		List<IDT> returnList = new ArrayList<IDT>();
		for (IdObject<IDT> idObject : idObjectList) {
			returnList.add(idObject.getId());
		}
		return returnList;
	}
}
