package com.bench.lang.base.math.permutation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.bench.lang.base.math.permutation.PermutationEachVisitor;
import com.bench.lang.base.math.permutation.PermutationEntry;
import com.bench.lang.base.math.permutation.PermutationGenerator;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

/**
 * 排列工具类
 * 
 * @author cold
 * 
 * @version $Id: PermutationUtils.java, v 0.1 2011-3-3 下午09:12:12 cold Exp $
 */
public class PermutationUtils {

	/**
	 * 从elementList元素中，取出size个，进行排列，返回排列清单
	 * 
	 * @param elementList
	 * @param size
	 * @return
	 */
	public static <T> List<List<T>> permutation(List<T> elementList, int size) {
		final List<List<T>> reurnList = new ArrayList<List<T>>();
		permutationVisit(elementList, size, new PermutationEachVisitor<T>() {
			public void visit(List<T> singlePermutationList) {
				// TODO Auto-generated method stub
				reurnList.add(singlePermutationList);
			}

		});
		return reurnList;
	}

	/**
	 * 根据elementList，计算长度为size的排列值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan 返回符合要求的集合列表
	 * 
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @return
	 */
	public static <T> List<List<T>> permutation(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final List<List<T>> returnList = new ArrayList<List<T>>();
		permutationVisit(elementList, size, danIndexSet, minDan, maxDan, new PermutationEachVisitor<T>() {
			public void visit(List<T> singlePermutationList) {
				// TODO Auto-generated method stub
				returnList.add(singlePermutationList);
			}

		});
		return returnList;
	}

	/**
	 * 获取总排列数
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @return
	 */
	public static <T> int getPermutationTotalAmount(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final AtomicInteger i = new AtomicInteger();
		permutationVisit(elementList, size, danIndexSet, minDan, maxDan, new PermutationEachVisitor<T>() {
			public void visit(List<T> singlePermutationList) {
				// TODO Auto-generated method stub
				i.incrementAndGet();
			}

		});
		return i.intValue();
	}

	/**
	 * 获取总排列数
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @return
	 */
	public static <T> int getPermutationTotalAmount(final List<T> elementList, int size) {
		return getPermutationTotalAmount(elementList, size, null, -1, -1);
	}

	/**
	 * 根据elementList，计算长度为size的排列值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 对符合要求的每个排列，调用visitor进行访问
	 * 
	 * @param <T>
	 * @param elementList
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param visitor
	 */
	public static <T> void permutationVisit(final List<T> elementList, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final PermutationEachVisitor<T> visitor) {
		if (danIndexSet == null && (minDan > 0 || maxDan > 0) || minDan > maxDan) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "不正确的胆范围,minDan=" + minDan + ",maxDan=" + maxDan);
		}
		permutationVisit(elementList, size, new PermutationEachVisitor<T>() {
			public void visit(List<T> singlePermutationList) {
				// TODO Auto-generated method stub
				// 无胆
				if (danIndexSet == null || danIndexSet.size() == 0) {
					visitor.visit(singlePermutationList);
				}
				// 有胆，识别胆范围
				else {
					int mactchCount = 0;
					for (Integer index : danIndexSet) {
						if (singlePermutationList.contains(elementList.get(index))) {
							mactchCount++;
							continue;
						}
					}
					if (mactchCount >= minDan && mactchCount <= maxDan) {
						visitor.visit(singlePermutationList);
					}
				}
			}
		});
	}

	/**
	 * 根据elementList，计算长度为size的排列值，<br>
	 * 调用visitor进行访问
	 * 
	 * @param elementList
	 * @param size
	 * @param visitor
	 */
	public static <T> void permutationVisit(List<T> elementList, int size, PermutationEachVisitor<T> visitor) {
		PermutationGenerator generator = new PermutationGenerator(elementList.size(), size);
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
	 * 根据elementList，计算长度为size的排列值，<br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 并且，每个排列后的集合，choice的X乘，长度不能大于maxSingleSize 对符合要求的每个排列，调用visitor进行访问
	 * 
	 * @param elements
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param maxSingleSize
	 * @param visitor
	 */
	public static <T> void permutationEntryVisit(List<PermutationEntry<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final int maxSingleSize, final PermutationEachVisitor<PermutationEntry<T>> visitor) {
		permutationVisit(elements, size, danIndexSet, minDan, maxDan, new PermutationEachVisitor<PermutationEntry<T>>() {
			public void visit(List<PermutationEntry<T>> singlePermutationList) {
				// TODO Auto-generated method stub
				permutationEntrySplitVisit(singlePermutationList, maxSingleSize, visitor);
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
	public static <T> void permutationEntrySplitVisit(final List<PermutationEntry<T>> elements, int maxSingleSize,
			final PermutationEachVisitor<PermutationEntry<T>> visitor) {
		List<PermutationEntry<T>> prepareToSingleList = new ArrayList<PermutationEntry<T>>();
		int currentSingleSize = 1;
		for (PermutationEntry<T> entry : elements) {
			currentSingleSize *= entry.getChoiceList().size();
		}

		// 大于的话,要准备拆分
		if (currentSingleSize > maxSingleSize) {
			// 如果大于，则一直循环
			while (currentSingleSize > maxSingleSize) {
				// 移除选项最多的1个
				PermutationEntry<T> maxChoiceLengthEntry = null;
				for (PermutationEntry<T> entry : elements) {
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
				for (PermutationEntry<T> entry : elements) {
					currentSingleSize *= entry.getChoiceList().size();
				}
			}

		} else {
			// 不大于，直接访问
			visitor.visit(elements);
			return;
		}

		// 排列准备单选的项
		List<List<PermutationEntry<T>>> entryList = new ArrayList<List<PermutationEntry<T>>>();
		for (PermutationEntry<T> entry : prepareToSingleList) {
			List<PermutationEntry<T>> tmpList = new ArrayList<PermutationEntry<T>>();
			entryList.add(tmpList);
			for (T choice : entry.getChoiceList()) {
				PermutationEntry<T> choiceEntry = new PermutationEntry<T>();
				choiceEntry.setKey(entry.getKey());
				choiceEntry.setChoiceList(new ArrayList<T>());
				choiceEntry.getChoiceList().add(choice);
				tmpList.add(choiceEntry);
			}
		}
		twoDimensionPermutationVisit(entryList, prepareToSingleList.size(), new PermutationEachVisitor<PermutationEntry<T>>() {
			public void visit(List<PermutationEntry<T>> singlePermutationList) {
				// TODO Auto-generated method stub
				singlePermutationList.addAll(elements);
				visitor.visit(singlePermutationList);
			}

		});

	}

	/**
	 * 二维数组排列访问 <br>
	 * 其中索引danIndexSet中指定的在elementList中的元素必须出现， 出现的允许次数，最小为minDan，最大为maxDan<br>
	 * 对符合要求的每个排列，调用visitor进行访问
	 * 
	 * @param <T>
	 * @param elements
	 * @param size
	 * @param danIndexSet
	 * @param minDan
	 * @param maxDan
	 * @param visitor
	 */
	public static <T> void twoDimensionPermutationVisit(List<List<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan,
			final PermutationEachVisitor<T> visitor) {
		permutationVisit(elements, size, danIndexSet, minDan, maxDan, new PermutationEachVisitor<List<T>>() {
			public void visit(List<List<T>> singlePermutationList) {
				// TODO Auto-generated method stub
				int max = 1;
				for (List<T> single : singlePermutationList) {
					max *= single.size();
				}
				for (int i = 0; i < max; i++) {
					List<T> singleList = new ArrayList<T>();
					int temp = 1;
					for (List<T> single : singlePermutationList) {
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
	public static <T> int getTwoDimensionPermutationTotalAmount(List<List<T>> elements, int size, final Set<Integer> danIndexSet, final int minDan, final int maxDan) {
		final AtomicInteger i = new AtomicInteger();
		twoDimensionPermutationVisit(elements, size, danIndexSet, minDan, maxDan, new PermutationEachVisitor<T>() {
			public void visit(List<T> singlePermutationList) {
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
	public static <T> int getTwoDimensionPermutationTotalAmount(List<List<T>> elements, int size) {
		return getTwoDimensionPermutationTotalAmount(elements, size, null, -1, -1);
	}

	/**
	 * 访问二维数组，任取size个的每种排列
	 * 
	 * @param <T>
	 * @param elements
	 * @param size
	 * @param visitor
	 */
	public static <T> void twoDimensionPermutationVisit(List<List<T>> elements, int size, final PermutationEachVisitor<T> visitor) {
		twoDimensionPermutationVisit(elements, size, null, -1, -1, visitor);
	}

	public static void main(String[] args) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> childList = new ArrayList<String>();
		childList.add("a1");
		childList.add("a2");
		list.add(childList);
		childList = new ArrayList<String>();

		childList.add("b");
		list.add(childList);

		childList = new ArrayList<String>();
		childList.add("c");
		list.add(childList);

		System.out.println(PermutationUtils.getTwoDimensionPermutationTotalAmount(list, 2));
	}

}
