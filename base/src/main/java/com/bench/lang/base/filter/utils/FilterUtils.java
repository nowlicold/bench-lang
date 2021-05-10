package com.bench.lang.base.filter.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bench.lang.base.filter.Filter;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.map.utils.MapUtils;

/**
 * 过滤工具类
 * 
 * @author cold
 *
 * @version $Id: FilterUtils.java, v 0.1 2019年12月16日 下午3:04:39 cold Exp $
 */
public class FilterUtils {

	/**
	 * 按照过滤表达式，找到第一个符合过滤条件的记录
	 * 
	 * @param objectList
	 * @param filters
	 * @return
	 */
	@SafeVarargs
	public static <OBJ extends Object> OBJ findFirst(List<OBJ> objectList, Filter<OBJ>... filters) {
		Map<Integer, OBJ> matchedObjectMap = new HashMap<Integer, OBJ>();
		Map<Integer, Filter<OBJ>> filterIndexMap = new LinkedHashMap<Integer, Filter<OBJ>>();
		for (int i = 0; i < filters.length; i++) {
			filterIndexMap.put(i, filters[i]);
		}
		List<Integer> matchedIndex = new ArrayList<Integer>();
		for (OBJ object : objectList) {
			// 检测第一个匹配到的，尽快返回
			OBJ firstMatched = matchedObjectMap.get(0);
			if (firstMatched != null) {
				return firstMatched;
			}
			matchedIndex.clear();
			for (Map.Entry<Integer, Filter<OBJ>> entry : filterIndexMap.entrySet()) {
				if (entry.getValue().accept(object)) {
					matchedIndex.add(entry.getKey());
					matchedObjectMap.put(entry.getKey(), object);
				}
			}
			// 移除匹配到的
			MapUtils.removeKeys(filterIndexMap, matchedIndex);
			// 如果全部匹配完
			if (filterIndexMap.size() == 0) {
				break;
			}
		}

		// 按顺序返回第一个匹配到的
		for (int i = 0; i < filters.length; i++) {
			OBJ matchedObject = matchedObjectMap.get(i);
			if (matchedObject != null) {
				return matchedObject;
			}
		}
		return null;
	}

	/**
	 * 按照优先级返回所有匹配到的内容
	 * 
	 * @param objectList
	 * @param filters
	 * @return
	 */
	@SafeVarargs
	public static <OBJ extends Object> List<OBJ> findAll(List<OBJ> objectList, Filter<OBJ>... filters) {
		Map<Integer, List<OBJ>> matchedObjectsMap = new HashMap<Integer, List<OBJ>>();
		Map<Integer, Filter<OBJ>> filterIndexMap = new LinkedHashMap<Integer, Filter<OBJ>>();
		for (int i = 0; i < filters.length; i++) {
			filterIndexMap.put(i, filters[i]);
		}
		for (OBJ object : objectList) {
			for (Map.Entry<Integer, Filter<OBJ>> entry : filterIndexMap.entrySet()) {
				if (entry.getValue().accept(object)) {
					List<OBJ> matchedObjectList = matchedObjectsMap.get(entry.getKey());
					if (matchedObjectList == null) {
						matchedObjectList = new ArrayList<OBJ>();
						matchedObjectsMap.put(entry.getKey(), matchedObjectList);
					}
					matchedObjectList.add(object);
					break;
				}
			}
		}
		List<OBJ> returnList = new ArrayList<OBJ>();
		for (int i = 0; i < filters.length; i++) {
			List<OBJ> matchedList = matchedObjectsMap.get(i);
			if (matchedList != null) {
				ListUtils.addUnequalAll(matchedList, returnList);
			}
		}
		return returnList;
	}
}
