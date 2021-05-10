package com.bench.lang.base.dependon.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bench.lang.base.dependon.DependsOn;
import com.bench.lang.base.dependon.comparator.DependsOnComparator;
import com.bench.lang.base.list.utils.ListUtils;

/**
 * dependson工具类
 * 
 * @author cold
 *
 * @version $Id: DependsonUtils.java, v 0.1 2020年5月11日 下午6:22:30 cold Exp $
 */
public class DependsOnUtils {

	/**
	 * 返回所有的依赖
	 * 
	 * @param <T>
	 * @param object
	 * @param searchObjects
	 * @return
	 */
	public static <T extends DependsOn<T>> List<T> getAllDependsOn(T object, List<T> searchObjectList) {
		List<T> foundObjectList = new ArrayList<T>();
		getAllDependsOnInner(foundObjectList, object, new HashSet<T>(), searchObjectList);
		return foundObjectList;
	}

	private static <T extends DependsOn<T>> void getAllDependsOnInner(List<T> foundObjectList, T object, Set<T> visitedObjectSet, List<T> searchObjectList) {
		if (visitedObjectSet.contains(object)) {
			return;
		}
		if (ListUtils.size(object.getDependsOn()) == 0) {
			visitedObjectSet.add(object);
			return;
		}
		for (Class<?> dependsOnClass : object.getDependsOn()) {
			T dependsOnObject = searchObjectList.stream().filter(p -> p.getClass() == dependsOnClass).findFirst().orElse(null);
			if (dependsOnObject != null) {
				foundObjectList.add(dependsOnObject);
				getAllDependsOnInner(foundObjectList, dependsOnObject, visitedObjectSet, searchObjectList);
				visitedObjectSet.add(dependsOnObject);
			}
		}
		// 查找所有的子
		for (T searchObject : searchObjectList) {
			if (searchObject.getParent() != object.getClass()) {
				continue;
			}
			// 取子的dependsOn
			for (Class<?> dependsOnClass : searchObject.getDependsOn()) {
				T dependsOnObject = searchObjectList.stream().filter(p -> p.getClass() == dependsOnClass).findFirst().orElse(null);
				if (dependsOnObject != null) {
					foundObjectList.add(dependsOnObject);
					getAllDependsOnInner(foundObjectList, dependsOnObject, visitedObjectSet, searchObjectList);
					visitedObjectSet.add(dependsOnObject);
				}
			}
		}

		visitedObjectSet.add(object);
	}

	/**
	 * 排序
	 * 
	 * @param <T>
	 * @param orders
	 */
	public static <T extends DependsOn<T>> void sort(List<T> orders) {
		DependsOnComparator<T> comparator = new DependsOnComparator<T>(orders);
		Collections.sort(orders, comparator);
	}

}
