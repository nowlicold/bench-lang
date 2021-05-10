package com.bench.lang.base.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bench.lang.base.map.utils.MapUtils;
import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.object.utils.ObjectUtils;

/**
 * 
 * 树深度计算器
 * 
 * @author cold
 *
 * @version $Id: TreeDepthCalculator.java, v 0.1 2015年11月24日 下午4:33:50 Administrator Exp $
 */
public class TreeDepthCalculator {

	/**
	 * 根据子父关系map计算每个节点的深度
	 * 
	 * @param childParentMap
	 *            ,key为子，value为父
	 * @return 返回每个节点的深度，根为0
	 */
	public static <T> Map<T, Integer> getDepth(Map<T, T> childParentMap) {
		Map<T, Integer> returnMap = new HashMap<T, Integer>();
		// 计算每个子节点的深度
		for (Map.Entry<T, T> entry : childParentMap.entrySet()) {
			returnMap.put(entry.getKey(), getDepth(childParentMap, entry.getKey()));
		}
		// 补全父节点
		for (Map.Entry<T, T> entry : childParentMap.entrySet()) {
			// 如果当前父节点已经计算过了，则不处理
			if (returnMap.containsKey(entry.getValue())) {
				continue;
			}
			// 存储父节点下所有子节点的深度
			List<Integer> childDepthList = new ArrayList<Integer>();
			// 取当前父节点的所有子
			List<T> childList = MapUtils.getKeyByValue(childParentMap, entry.getValue());
			// 遍历获取每个子的深度
			for (T child : childList) {
				childDepthList.add(returnMap.get(child) + 1);
			}
			// 取子深度的最大值+1
			returnMap.put(entry.getValue(), NumberUtils.max(childDepthList));
		}
		return returnMap;
	}

	/**
	 * 根据子父关系map，计算node节点的深度
	 * 
	 * @param childParentMap
	 * @param node
	 * @return
	 */
	public static <T> Integer getDepth(Map<T, T> childParentMap, T node) {
		List<T> childList = getChilds(childParentMap, node);
		// 无子，表示最末节点，返回1
		if (childList.size() == 0) {
			return 1;
		}
		List<Integer> childDepthList = new ArrayList<Integer>();
		// 计算每个子节点的深度后，作为当前节点可能的最大深度
		for (T child : childList) {
			childDepthList.add(getDepth(childParentMap, child));
		}
		// 取这些值的最大值1 +
		return NumberUtils.max(childDepthList) + 1;
	}

	/**
	 * 根据子父节点map，获取parent的所有子节点
	 * 
	 * @param childParentMap
	 * @param parent
	 * @return
	 */
	public static <T> List<T> getChilds(Map<T, T> childParentMap, T parent) {
		List<T> returnList = new ArrayList<T>();
		for (Map.Entry<T, T> entry : childParentMap.entrySet()) {
			if (ObjectUtils.equals(entry.getValue(), parent)) {
				returnList.add(entry.getKey());
			}
		}
		return returnList;
	}
}
