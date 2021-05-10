package com.bench.lang.base.combine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.bench.lang.base.collection.utils.CollectionUtils;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.math.utils.MathUtils;

/**
 * 组合工具类
 * 
 * @author cold
 * 
 * @version $Id: CombineUtils.java, v 0.1 2011-3-3 下午09:12:12 cold Exp $
 */
public class CombineUtils {

	/**
	 * 一个集合A,其中集合中每个元素又是1个集合, <Br>
	 * 从结合A的每个元素中,取出一个子元素,组成长度为r的集合 <br>
	 * 输出所有的这些组合
	 * 
	 * @param listStr
	 * @param r
	 * @return
	 */

	private static <T> List<List<T>> tqq(List<List<T>> coms, int len) {
		List<List<T>> rs = new ArrayList<List<T>>();
		if (coms.size() == len) {
			return qims(coms);
		} else if (len == 1) {// 从coms中遍历所有元素
			return cims(coms);
		} else {
			List<List<T>> comsss = new ArrayList<List<T>>();
			for (int i = 1; i < coms.size(); i++) {
				comsss.add(coms.get(i));
			}
			for (T tt : coms.get(0)) {
				rs.addAll(syscom(tt, tqq(comsss, len - 1)));
			}
			rs.addAll(tqq(comsss, len));

			return rs;
		}

	}

	private static <T> List<List<T>> cims(List<List<T>> coms) {
		List<List<T>> cos = new ArrayList<List<T>>();
		for (List<T> s : coms) {

			for (T ss : s) {
				List<T> list = new ArrayList<T>();

				list.add(ss);
				cos.add(list);
			}

		}
		return cos;
	}

	private static <T> List<List<T>> qims(List<List<T>> coms) {

		List<List<T>> cos = new ArrayList<List<T>>();

		if (coms.size() == 1) {

			for (T str : coms.get(0)) {

				List<T> list = new ArrayList<T>();
				list.add(str);
				cos.add(list);

			}

			return cos;
		} else {
			for (T ss : coms.get(0)) {
				List<List<T>> cc = new ArrayList<List<T>>();

				for (int i = 1; i < coms.size(); i++) {
					cc.add(coms.get(i));
				}

				for (List<T> listTemp : qims(cc)) {
					List<T> list = new ArrayList<T>();
					list.add(ss);
					for (T str : listTemp) {
						list.add(str);

					}
					cos.add(list);
				}

			}
			return cos;
		}

	}

	private static <T> List<List<T>> syscom(T a, List<List<T>> com) {
		List<List<T>> liss = new ArrayList<List<T>>();
		/***
		 * 将二维List拆分成一维
		 */
		for (List<T> list : com) {
			List<T> tempList = new ArrayList<T>();
			tempList.add(a);
			for (T LetteryResult : list) {

				tempList.add(LetteryResult);
			}
			liss.add(tempList);
		}

		return liss;

	}

	private static int cols(int[] ints, int len) {
		if (len == ints.length) {
			return coll(ints);
		} else if (len == 0) {
			return 1;
		} else {
			int[] temps = new int[ints.length - 1];
			System.arraycopy(ints, 1, temps, 0, temps.length);
			return ints[0] * cols(temps, len - 1) + cols(temps, len);
		}
	}

	/**
	 * 计算从ints中各取一个数字的方法
	 * 
	 * @param ints
	 * @return
	 */
	private static int coll(int[] ints) {
		int result = 1;
		for (int i : ints) {
			result *= i;
		}
		return result;
	}

	/**
	 * 超级组合,listStr是一个集合列表,这个列表中 的每个元素又都是1个集合, <br>
	 * 从listStr的元素集合中各取1个,组成成长度为r的集合,返回组合清单
	 * 
	 * @param <T>
	 * @param listStr
	 * @param r
	 * @return
	 */
	public static <T> List<List<T>> superCombine(List<List<T>> listStr, int r) {

		return tqq(listStr, r);
	}

	public static int calculateNum(List<Integer> list, int r) {
		// 组装计算数组
		int[] calculateArr = new int[list.size()];

		int i = 0;
		for (Integer sum : list) {
			calculateArr[i] = sum;
			i++;
		}

		return cols(calculateArr, r);
	}

	/**
	 * 从elementList元素中，取出size个，进行组合，返回组合清单
	 * 
	 * @param elementList
	 * @param size
	 * @return
	 */
	public static <T> List<List<T>> combine(List<T> elementList, int size) {
		final List<List<T>> reurnList = new ArrayList<List<T>>();
		if (size >= elementList.size()) {
			reurnList.add(new ArrayList<T>(elementList));
			return reurnList;
		}
		combineVisit(elementList, size, new CombineEachVisitor<T>() {
			public void visit(List<T> singleCombineList) {
				// TODO Auto-generated method stub
				reurnList.add(singleCombineList);
			}

		});
		return reurnList;
	}

	/**
	 * 根据elementList，计算长度为size的组合值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan 返回符合要求的集合列表
	 * 
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @return
	 */
	public static <T> List<List<T>> combine(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final List<List<T>> returnList = new ArrayList<List<T>>();
		combineVisit(elementList, size, danIndexSet, minDan, maxDan, new CombineEachVisitor<T>() {
			public void visit(List<T> singleCombineList) {
				// TODO Auto-generated method stub
				returnList.add(singleCombineList);
			}

		});
		return returnList;
	}

	/**
	 * 获取总组合数
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @return
	 */
	public static <T> int getCombineTotalAmount(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final AtomicInteger i = new AtomicInteger();
		combineVisit(elementList, size, danIndexSet, minDan, maxDan, new CombineEachVisitor<T>() {
			public void visit(List<T> singleCombineList) {
				// TODO Auto-generated method stub
				i.incrementAndGet();
			}

		});
		return i.intValue();
	}

	/**
	 * 获取总组合数
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @return
	 */
	public static <T> int getCombineTotalAmount(final List<T> elementList, int size) {
		return getCombineTotalAmount(elementList, size, null, -1, -1);
	}

	/**
	 * 根据elementList，计算长度为size的组合值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 对符合要求的每个组合，调用visitor进行访问
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param visitor
	 */
	public static <T> void combineVisit(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final CombineEachVisitor<T> visitor) {
		if (CollectionUtils.size(danIndexSet) == 0 && (minDan > 0 || maxDan > 0) || minDan > maxDan) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "不正确的胆范围,minDan=" + minDan + ",maxDan=" + maxDan);
		}
		combineVisit(elementList, size, new CombineEachVisitor<T>() {
			public void visit(List<T> singleCombineList) {
				// TODO Auto-generated method stub
				// 无胆
				if (danIndexSet == null || danIndexSet.size() == 0) {
					visitor.visit(singleCombineList);
				}
				// 有胆，识别胆范围
				else {
					int mactchCount = 0;
					for (Integer index : danIndexSet) {
						if (singleCombineList.contains(elementList.get(index))) {
							mactchCount++;
							continue;
						}
					}
					if (mactchCount >= minDan && mactchCount <= maxDan) {
						visitor.visit(singleCombineList);
					}
				}
			}
		});
	}

	/**
	 * 根据elementList，计算长度为size的组合值，<br>
	 * 调用visitor进行访问
	 * 
	 * @param elementList
	 * @param size
	 * @param visitor
	 */
	public static <T> void combineVisit(List<T> elementList, int size, CombineEachVisitor<T> visitor) {
		CombinationGenerator generator = new CombinationGenerator(elementList.size(), size);
		while (generator.hasMore()) {
			int[] indices = generator.getNext();
			List<T> simpleComboineList = new ArrayList<T>();
			for (int i = 0; i < indices.length; i++) {
				simpleComboineList.add(elementList.get(indices[i]));
			}
			visitor.visit(simpleComboineList);
		}
	}

	/**
	 * 根据elementList，计算长度为size的组合值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 并且，每个组合后的集合，choice的X乘，长度不能大于maxSingleSize 对符合要求的每个组合，调用visitor进行访问
	 * 
	 * @param elements
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param maxSingleSize
	 * @param visitor
	 */
	public static <T> void combineEntryVisit(List<CombineEntry<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final int maxSingleSize, final CombineEachVisitor<CombineEntry<T>> visitor) {
		combineVisit(elements, size, danIndexSet, minDan, maxDan, new CombineEachVisitor<CombineEntry<T>>() {
			public void visit(List<CombineEntry<T>> singleCombineList) {
				// TODO Auto-generated method stub
				combineEntrySplitVisit(singleCombineList, maxSingleSize, visitor);
			}

		});
	}

	/**
	 * 如果元素X乘大于maxSingleSize,则进行拆分,并访问
	 * 
	 * @param <T>
	 * @param elements
	 * @param maxSingleSize
	 */
	public static <T> void combineEntrySplitVisit(final List<CombineEntry<T>> elements, int maxSingleSize, final CombineEachVisitor<CombineEntry<T>> visitor) {
		List<CombineEntry<T>> prepareToSingleList = new ArrayList<CombineEntry<T>>();
		int currentSingleSize = 1;
		for (CombineEntry<T> entry : elements) {
			currentSingleSize *= entry.getChoiceList().size();
		}

		// 大于的话,要准备拆分
		if (currentSingleSize > maxSingleSize) {
			// 如果大于，则一直循环
			while (currentSingleSize > maxSingleSize) {
				// 移除选项最多的1个
				CombineEntry<T> maxChoiceLengthEntry = null;
				for (CombineEntry<T> entry : elements) {
					if (maxChoiceLengthEntry == null || maxChoiceLengthEntry.getChoiceList().size() < entry.getChoiceList().size()) {
						maxChoiceLengthEntry = entry;
					}

				}
				prepareToSingleList.add(maxChoiceLengthEntry);
				elements.remove(maxChoiceLengthEntry);

				// 没有剩余元素了,则报错
				if (elements.size() == 0) {
					throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,
							"没有剩余元素可移除，maxSingleSize不正确,maxSingleSize=" + maxSingleSize + ",elements=" + elements);
				}

				// 再次计算最大值
				currentSingleSize = 1;
				for (CombineEntry<T> entry : elements) {
					currentSingleSize *= entry.getChoiceList().size();
				}
			}

		} else {
			// 不大于，直接访问
			visitor.visit(elements);
			return;
		}

		// 组合准备单选的项
		List<List<CombineEntry<T>>> entryList = new ArrayList<List<CombineEntry<T>>>();
		for (CombineEntry<T> entry : prepareToSingleList) {
			List<CombineEntry<T>> tmpList = new ArrayList<CombineEntry<T>>();
			entryList.add(tmpList);
			for (T choice : entry.getChoiceList()) {
				CombineEntry<T> choiceEntry = new CombineEntry<T>();
				choiceEntry.setKey(entry.getKey());
				choiceEntry.setChoiceList(new ArrayList<T>());
				choiceEntry.getChoiceList().add(choice);
				tmpList.add(choiceEntry);
			}
		}
		twoDimensionCombineVisit(entryList, prepareToSingleList.size(), new CombineEachVisitor<CombineEntry<T>>() {
			public void visit(List<CombineEntry<T>> singleCombineList) {
				// TODO Auto-generated method stub
				singleCombineList.addAll(elements);
				visitor.visit(singleCombineList);
			}

		});

	}

	/**
	 * 二维数组组合访问 <br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 对符合要求的每个组合，调用visitor进行访问
	 * 
	 * @param <T>
	 * @param elements
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param visitor
	 */
	public static <T> void twoDimensionCombineVisit(List<List<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final CombineEachVisitor<T> visitor) {
		combineVisit(elements, size, danIndexSet, minDan, maxDan, new CombineEachVisitor<List<T>>() {
			public void visit(List<List<T>> singleCombineList) {
				// TODO Auto-generated method stub
				int max = 1;
				for (List<T> single : singleCombineList) {
					max *= single.size();
				}
				for (int i = 0; i < max; i++) {
					List<T> singleList = new ArrayList<T>();
					int temp = 1;
					for (List<T> single : singleCombineList) {
						temp *= single.size();
						singleList.add(single.get(i / (max / temp) % single.size()));
					}
					visitor.visit(singleList);
				}
			}

		});
	}

	/**
	 * 返回二维数组排列总排列数
	 * 
	 * @param elements
	 *            二维元素列表
	 * @param size
	 *            取size个
	 * @param danIndexSet
	 *            胆indexi集合
	 * @param minDan
	 *            最小胆个数
	 * @param maxDan
	 *            最大但个数
	 * @return
	 */
	public static <T> int getTwoDimensionCombineTotalAmount(List<List<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final AtomicInteger i = new AtomicInteger();
		twoDimensionCombineVisit(elements, size, danIndexSet, minDan, maxDan, new CombineEachVisitor<T>() {
			public void visit(List<T> singleCombineList) {
				i.incrementAndGet();
			}
		});
		return i.intValue();
	}

	/**
	 * 返回二维数组排列总排列数
	 * 
	 * @param elements
	 * @param size
	 * @return
	 */
	public static <T> int getTwoDimensionCombineTotalAmount(List<List<T>> elements, int size) {
		return getTwoDimensionCombineTotalAmount(elements, size, null, -1, -1);
	}

	/**
	 * 访问二维数组，任取size个的每种组合
	 * 
	 * @param <T>
	 * @param elements
	 * @param size
	 * @param visitor
	 */
	public static <T> void twoDimensionCombineVisit(List<List<T>> elements, int size, final CombineEachVisitor<T> visitor) {
		twoDimensionCombineVisit(elements, size, null, -1, -1, visitor);
	}

	/**
	 * 将objectList拆成容量为fixedSize的组合，每个组合，最多不超过maxCombineCount种组合<br>
	 * 返回这样的组合列表
	 * 
	 * @param objectList
	 * @param fixedSize
	 * @param maxCombineCount
	 * @return
	 */
	public static <T> List<List<T>> combine(List<T> objectList, int fixedSize, int maxCombineCount) {
		// 求组多允许取多少个数据来组合
		List<List<T>> returnList = new ArrayList<List<T>>();
		int maxSizeCount = MathUtils.getMaxToCombineElementCount(fixedSize, maxCombineCount);
		if (maxSizeCount >= objectList.size()) {
			returnList.add(objectList);
			return returnList;
		}
		List<T> firstList = objectList.subList(0, maxSizeCount);
		returnList.add(firstList);
		List<T> secondList = objectList.subList(maxSizeCount, objectList.size());
		int firstCount = fixedSize - secondList.size();
		if (secondList.size() >= fixedSize) {
			firstCount = 1;
			for (List<T> eachSecondList : CombineUtils.combine(secondList, fixedSize)) {
				returnList.add(eachSecondList);
			}
		}
		for (int i = firstCount; i < fixedSize; i++) {
			for (List<T> eachFirstList : CombineUtils.combine(firstList, i)) {
				for (List<T> eachSecondList : CombineUtils.combine(secondList, fixedSize - i)) {
					List<T> list = new ArrayList<T>(eachFirstList);
					list.addAll(eachSecondList);
					returnList.add(list);
				}
			}
		}
		return returnList;
	}

	/**
	 * 将objectList拆成容量为fixedSize的组合，每个组合，最多不超过maxCombineCount种组合，可以使用胆码方式<br>
	 * 返回这样的组合列表
	 * 
	 * @param objectList
	 * @param fixedSize
	 * @param maxCombineCount
	 * @return
	 */
	public static <T> List<DanCombineEntry<T>> combineAsDan(List<T> objectList, int fixedSize, int maxCombineCount) {
		List<DanCombineEntry<T>> entryList = new ArrayList<DanCombineEntry<T>>();
		if (fixedSize == 1) {
			for (List<T> eachList : MathUtils.part(objectList, maxCombineCount)) {
				DanCombineEntry<T> entry = new DanCombineEntry<T>();
				entry.setObjectList(eachList);
				entryList.add(entry);

			}
			return entryList;
		}
		if (fixedSize == maxCombineCount) {
			for (List<T> eachList : CombineUtils.combine(objectList, fixedSize)) {
				DanCombineEntry<T> entry = new DanCombineEntry<T>();
				entry.setObjectList(eachList);
				entryList.add(entry);

			}
			return entryList;
		}
		int maxSizeCount = MathUtils.getMaxToCombineElementCount(fixedSize, maxCombineCount);
		if (maxSizeCount >= objectList.size()) {
			DanCombineEntry<T> entry = new DanCombineEntry<T>();
			entry.setObjectList(objectList);
			entryList.add(entry);
			return entryList;
		}
		List<T> firstList = objectList.subList(0, maxSizeCount);
		DanCombineEntry<T> entry = new DanCombineEntry<T>();
		entry.setObjectList(firstList);
		entryList.add(entry);
		List<T> secondList = objectList.subList(maxSizeCount, objectList.size());
		int firstCount = fixedSize - secondList.size();
		if (secondList.size() >= fixedSize) {
			firstCount = 1;
			for (List<T> eachSecondList : CombineUtils.combine(secondList, fixedSize)) {
				entry = new DanCombineEntry<T>();
				entry.setObjectList(eachSecondList);
				entryList.add(entry);
			}
		}
		// 后续胆拖
		for (int i = firstCount; i < fixedSize; i++) {
			for (List<T> eachSecondList : CombineUtils.combine(secondList, fixedSize - i)) {
				entry = new DanCombineEntry<T>();
				entry.setObjectList(firstList);
				entry.setDanObjectList(eachSecondList);
				entryList.add(entry);
			}
		}
		return entryList;
	}

	/**
	 * 
	 * 组合拆分， 将firstList和secondList拆分成单个entry最多不超过maxEachEntryTotalCombineCount个单一组合 其中,first维度为fistSize个元素,seconds维度为secondSize个元素
	 * 
	 * @param firstList
	 * @param secondList
	 * @param maxFirstSize
	 * @param maxSecondSize
	 * @param maxEachEntryTotalCount
	 * @return
	 */
	public static <T, E> List<TwoDimensionCombineEntry<T, E>> combineSplit(List<T> firstList, List<T> firstDanList, List<E> secondList, List<E> secondDanList,
			int firstSize, int secondSize, int maxEachEntryTotalCombineCount) {
		List<TwoDimensionCombineEntry<T, E>> returnList = new ArrayList<TwoDimensionCombineEntry<T, E>>();
		int firstCombineCount = MathUtils.combineCount(firstList.size(), firstSize - ListUtils.size(firstDanList));
		// 不用拆分
		if (firstCombineCount * MathUtils.combineCount(secondList.size(), secondSize - ListUtils.size(secondDanList)) <= maxEachEntryTotalCombineCount) {
			TwoDimensionCombineEntry<T, E> entry = new TwoDimensionCombineEntry<T, E>();
			entry.setFirstList(firstList);
			entry.setFirstDanList(firstDanList);
			entry.setSecondList(secondList);
			entry.setSecondDanList(secondDanList);
			returnList.add(entry);
			return returnList;
		}

		// 求第1维最大允许值,即组多允许取多少个数据来组合
		int maxFirstSizeCount = MathUtils.getMaxToCombineElementCount(firstSize - ListUtils.size(firstDanList), maxEachEntryTotalCombineCount);
		// 大于第1维度容量,第1维度不用拆分
		if (maxFirstSizeCount >= firstList.size()) {
			// 第2维度单个组合的最大组合数
			int maxSecondSingleCombineCount = maxEachEntryTotalCombineCount / firstCombineCount;
			List<DanCombineEntry<E>> secondCombineEntryList = CombineUtils.combineAsDan(secondList, secondSize - ListUtils.size(secondDanList),
					maxSecondSingleCombineCount);
			for (DanCombineEntry<E> secondEntry : secondCombineEntryList) {
				TwoDimensionCombineEntry<T, E> entry = new TwoDimensionCombineEntry<T, E>();
				entry.setFirstList(firstList);
				entry.setFirstDanList(firstDanList);
				entry.setSecondList(secondEntry.getObjectList());
				List<E> currentSecondDanList = new ArrayList<E>();
				if (secondEntry.getDanObjectList() != null) {
					currentSecondDanList.addAll(secondEntry.getDanObjectList());
				}
				if (secondDanList != null) {
					currentSecondDanList.addAll(secondDanList);
				}
				entry.setSecondDanList(currentSecondDanList);
				returnList.add(entry);
			}
		} else {
			// 第1维胆拖组合
			List<DanCombineEntry<T>> firstCombineEntryList = CombineUtils.combineAsDan(firstList, firstSize - ListUtils.size(firstDanList),
					maxEachEntryTotalCombineCount);
			for (DanCombineEntry<T> firstEntry : firstCombineEntryList) {
				firstCombineCount = MathUtils.combineCount(firstEntry.getObjectList().size(),
						firstSize - ListUtils.size(firstEntry.getDanObjectList()) - ListUtils.size(firstDanList));
				// 第2维度单个组合的最大组合数
				int maxSecondSingleCombineCount = maxEachEntryTotalCombineCount / firstCombineCount;
				List<DanCombineEntry<E>> secondCombineEntryList = CombineUtils.combineAsDan(secondList, secondSize - ListUtils.size(secondDanList),
						maxSecondSingleCombineCount);
				for (DanCombineEntry<E> secondEntry : secondCombineEntryList) {
					TwoDimensionCombineEntry<T, E> entry = new TwoDimensionCombineEntry<T, E>();
					entry.setFirstList(firstEntry.getObjectList());
					List<T> currentFirstDanList = new ArrayList<T>();
					if (firstEntry.getDanObjectList() != null) {
						currentFirstDanList.addAll(firstEntry.getDanObjectList());
					}
					if (firstDanList != null) {
						currentFirstDanList.addAll(firstDanList);
					}
					entry.setFirstDanList(currentFirstDanList);

					entry.setSecondList(secondEntry.getObjectList());

					List<E> currentSecondDanList = new ArrayList<E>();
					if (secondEntry.getDanObjectList() != null) {
						currentSecondDanList.addAll(secondEntry.getDanObjectList());
					}
					if (secondDanList != null) {
						currentSecondDanList.addAll(secondDanList);
					}
					entry.setSecondDanList(currentSecondDanList);
					returnList.add(entry);
				}
			}
		}
		return returnList;
	}
}
