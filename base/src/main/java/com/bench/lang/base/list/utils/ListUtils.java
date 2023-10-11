/**
 * benchcode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.list.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.bag.HashBag;

import com.bench.lang.base.accept.Acceptor;
import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.collection.utils.CollectionUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.visit.Visitor;

/**
 * List工具类
 *
 * @author cold
 * @version $Id: ListUtils.java, v 0.1 2010-5-31 下午05:34:09 cold Exp $
 */
public class ListUtils extends org.apache.commons.collections.ListUtils {

    public static final ListUtils INSTANCE = new ListUtils();

    /**
     * 取集合部分元素
     *
     * @param list
     * @param fromIndex
     * @param endIndex
     * @return
     */
    public static <T> List<T> subList(List<T> list, int fromIndex, int endIndex) {
        List<T> returnList = new ArrayList<T>();
        for (int i = fromIndex; i < endIndex && i < list.size(); i++) {
            returnList.add(list.get(i));
        }
        return returnList;
    }

    public static <E> List<E> subtract2(final List<E> list1, final List<? extends E> list2) {
        final ArrayList<E> result = new ArrayList<E>();
        final HashBag<E> bag = new HashBag<E>(list2);
        for (final E e : list1) {
            if (!bag.remove(e, 1)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * 移除相等的,只保留1个
     */
    public static <T> List<T> removeEqual() {
        return removeEqual();
    }

    /**
     * 移除相等的,只保留1个
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> removeEqual(List<T> list) {
        Set<T> set = new LinkedHashSet<T>(list);
        return new ArrayList<T>(set);
    }


    /**
     * 添加所有不相等的,将fromList加入到toList
     *
     * @param fromList
     * @param toList
     * @param <T>
     * @return
     */
    public static <T> List<T> addUnequalAll(List<T> fromList, List<T> toList) {
        if (ListUtils.size(fromList) == 0) {
            return toList;
        }
        for (T t : fromList) {
            if (!toList.contains(t)) {
                toList.add(t);
            }
        }
        return toList;
    }

    /**
     * @param list
     * @return
     */
    public static <T> List<T> clone(List<T> list) {
        List<T> retList = new ArrayList<T>();
        retList.addAll(list);
        return retList;
    }

    /**
     * 返回一个新数组
     *
     * @return
     */
    public static final List<?> newList() {
        return newArrayList();
    }

    /**
     * 返回一个新数组
     *
     * @return
     */
    public static final List<?> newList(int size) {
        return newArrayList(size);
    }

    /**
     * 返回一个新数组
     *
     * @return
     */
    public static final List<?> newArrayList() {
        return new ArrayList<Object>();
    }

    /**
     * 返回一个新数组
     *
     * @return
     */
    public static final List<?> newArrayList(int size) {
        return new ArrayList<Object>(size);
    }

    /**
     * 返回一个新集合
     *
     * @return
     */
    public static final <T> List<T> newList(Collection<T> collections) {
        return new ArrayList<T>(collections);
    }

    /**
     * @param ts
     * @param visitor
     */
    public static <T> void visit(List<T> ts, Visitor<T> visitor) {
        if (size(ts) == 0) {
            return;
        }
        for (T t : ts) {
            visitor.visit(t);
        }
    }

    /**
     * List转换到数组
     *
     * @param clasz
     * @param list
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Class<T> clasz, List<T> list) {
        return list.toArray((T[]) Array.newInstance(clasz, list.size()));
    }

    /**
     * @param ts
     * @param acceptor
     * @return
     */
    public static <T> List<T> filter(List<T> ts, Acceptor<T> acceptor) {
        List<T> returnList = new ArrayList<T>();
        if (size(ts) == 0) {
            return returnList;
        }
        for (T t : ts) {
            if (acceptor.accept(t)) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * @param ts
     * @param acceptor
     * @return
     */
    public static <T> List<T> filter(T[] ts, Acceptor<T> acceptor) {
        List<T> returnList = new ArrayList<T>();
        if (ArrayUtils.getLength(ts) == 0) {
            return returnList;
        }
        for (T t : ts) {
            if (acceptor.accept(t)) {
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return size(list) == 0;
    }

    /**
     * 非空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        return size(list) > 0;
    }

    @SafeVarargs
    public static <T> List<T> toList(T... ts) {
        if (ts == null) {
            return new ArrayList<T>(0);
        }
        List<T> retList = new ArrayList<T>();
        CollectionUtils.addAll(retList, ts);
        return retList;
    }

    public static List<String> toList(String... ts) {
        if (ts == null) {
            return new ArrayList<String>(0);
        }
        List<String> retList = new ArrayList<String>();
        CollectionUtils.addAll(retList, StringUtils.trim(ts));
        return retList;
    }

    public static <T> List<T> toList(Iterator<T> ite) {
        if (ite == null) {
            return new ArrayList<T>(0);
        }
        List<T> retList = new ArrayList<T>();
        while (ite.hasNext()) {
            retList.add(ite.next());
        }
        return retList;
    }

    /**
     * 返回list长度
     *
     * @param list
     * @return
     */
    public static int size(List<?> list) {
        return list == null ? 0 : list.size();
    }

    /**
     * 转换为字符串List
     *
     * @param list
     * @return
     */
    public static <T> List<String> toStringList(List<T> list) {
        if (list == null) {
            return null;
        }
        List<String> returnList = new ArrayList<String>();
        for (T t : list) {
            if (t == null) {
                returnList.add(null);
            } else {
                returnList.add(ObjectUtils.toString(t));
            }
        }
        return returnList;

    }

    /**
     * 转换为Long List
     *
     * @param list
     * @return
     */
    public static <T> List<Long> toLong(List<T> list) {
        if (list == null) {
            return null;
        }
        List<Long> returnList = new ArrayList<Long>();
        for (T t : list) {
            if (t == null) {
                returnList.add(null);
            } else if (t instanceof Number) {
                returnList.add(((Number) t).longValue());
            } else if (t instanceof String) {
                returnList.add(Long.parseLong((String) t));
            } else {
                returnList.add(Long.parseLong(t.toString()));
            }
        }
        return returnList;
    }

    /**
     * 转换为Integer List
     *
     * @param list
     * @return
     */
    public static <T> List<Integer> toInteger(List<T> list) {
        if (list == null) {
            return null;
        }
        List<Integer> returnList = new ArrayList<Integer>();
        for (T t : list) {
            if (t == null) {
                returnList.add(null);
            } else if (t instanceof Number) {
                returnList.add(((Number) t).intValue());
            } else if (t instanceof String) {
                returnList.add(Integer.parseInt((String) t));
            } else {
                returnList.add(Integer.parseInt(t.toString()));
            }
        }
        return returnList;
    }

    /**
     * 转换为Integer List
     *
     * @param list
     * @return
     */
    public static <T> List<Double> toDouble(List<T> list) {
        if (list == null) {
            return null;
        }
        List<Double> returnList = new ArrayList<Double>();
        for (T t : list) {
            if (t == null) {
                returnList.add(null);
            } else if (t instanceof Number) {
                returnList.add(((Number) t).doubleValue());
            } else if (t instanceof String) {
                returnList.add(Double.parseDouble((String) t));
            } else {
                returnList.add(Double.parseDouble(t.toString()));
            }
        }
        return returnList;
    }

    /**
     * 是否包含
     *
     * @param list
     * @param t
     * @return
     */
    public static <T> boolean contains(List<T> list, T t) {
        if (list == null) {
            return false;
        }
        return list.contains(t);
    }

    /**
     * 是否包含
     *
     * @param list
     * @param t
     * @return
     */
    public static <T> boolean containsAny(List<T> list, T... ts) {
        if (list == null) {
            return false;
        }
        if (ts == null) {
            return false;
        }
        for (T t : ts) {
            if (list.contains(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * list1是否包含至少一个list2内的元素
     *
     * @param list
     * @param t
     * @return
     */
    public static <T> boolean containsAny(List<T> list, List<T> list2) {
        if (list == null) {
            return false;
        }
        if (list2 == null) {
            return false;
        }
        for (T t : list2) {
            if (list.contains(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * list2中元素是否全部存在于list当中
     *
     * @param list
     * @param list2
     * @return
     */
    public static <T> boolean containsAll(List<T> list, List<T> list2) {
        if (list == null || isEmpty(list)) {
            return false;
        }
        if (list2 == null) {
            return false;
        }
        for (T t : list2) {
            if (!list.contains(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回collection和retain共同含有的元素
     *
     * @param collection
     * @param retain
     * @return
     */
    public static <T> List<T> retainAll2(Collection<T> collection, Collection<T> retain) {
        List<T> list = new ArrayList<T>(Math.min(collection.size(), retain.size()));

        for (Iterator<T> iter = collection.iterator(); iter.hasNext(); ) {
            T obj = iter.next();
            if (retain.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 创建一个数组，长度为length，每个元素默认值为defaultValue
     *
     * @param length
     * @param defaultValue
     * @return
     */
    public static List<Integer> createIntSequenceList(int length) {
        List<Integer> returnList = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            returnList.add(i);
        }
        return returnList;
    }

    /**
     * 创建一个数组，长度为length，每个元素默认值为defaultValue
     *
     * @param length
     * @param defaultValue
     * @return
     */
    public static <T> List<T> createList(int length, T defaultValue) {
        List<T> returnList = new ArrayList<T>();
        for (int i = 0; i < length; i++) {
            returnList.add(defaultValue);
        }
        return returnList;
    }

    /**
     * 从集合里移除互斥的对象
     *
     * @param list
     * @param ifReject
     * @return
     */
    public static <T> List<T> removeReject(List<T> list, IfReject<T> ifReject) {
        List<T> returnList = new ArrayList<T>(list);
        // 去掉互斥
        for (int i = 0; i < returnList.size(); i++) {
            T consultT = returnList.get(i);
            // 待删除的集合
            List<T> currentRemoveList = new ArrayList<T>();
            for (int j = i; j < returnList.size(); j++) {
                T testT = returnList.get(j);
                if (ifReject.reject(consultT, testT)) {
                    currentRemoveList.add(testT);
                }
            }
            returnList.removeAll(currentRemoveList);
        }
        return returnList;
    }

    public static interface IfReject<T> {

        public boolean reject(T t1, T t2);
    }

    public static <T> T first(List<T> list) {
        return list.get(0);
    }

    public static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }
}
