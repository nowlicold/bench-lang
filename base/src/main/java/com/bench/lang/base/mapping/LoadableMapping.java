package com.bench.lang.base.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bench.lang.base.object.FinalObject;
import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;

/**
 * 可以加载的映射,注意，这个不是映射，只是为了方便建立组件映射
 * 
 * @author cold
 *
 * @version $Id: LoadableMapping.java, v 0.1 2020年4月8日 上午10:02:41 cold Exp $
 */
public abstract class LoadableMapping<K, V> {

	private static final int DEFAULT_MAX_SIZE = 100;

	/**
	 * 映射Map
	 */
	protected Map<K, FinalObject<V>> mappingMap;

	/**
	 * 是否允许value为null
	 */
	protected boolean valueNullable;

	/**
	 * 最大映射数量
	 */
	protected int maxSize = DEFAULT_MAX_SIZE;

	/**
	 * 加载映射的值
	 * 
	 * @param key
	 * @return
	 */
	protected abstract V load(K key);

	/**
	 * 
	 */
	public LoadableMapping() {
		this(100, true);
	}

	/**
	 * @param maxSize
	 */
	public LoadableMapping(int maxSize) {
		this(maxSize, true);
	}

	public LoadableMapping(boolean valueNullable) {
		this(100, valueNullable);
	}

	/**
	 * @param maxSize
	 * @param valueNullable
	 */
	public LoadableMapping(int maxSize, boolean valueNullable) {
		super();
		this.maxSize = maxSize;
		this.valueNullable = valueNullable;
		mappingMap = new ConcurrentHashMap<K, FinalObject<V>>();
	}

	/**
	 * 返回映射的值<Br>
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		FinalObject<V> cachedObject = this.mappingMap.get(key);
		if (cachedObject != null) {
			return cachedObject.getObject();
		}

		// 同步锁定
		synchronized (this) {
			/*************************
			 * 再次判断
			 **********************/
			cachedObject = this.mappingMap.get(key);
			if (cachedObject != null) {
				return cachedObject.getObject();
			}
			V value = load(key);
			// 如果value不允许为空，但是加载的value是空，则抛异常
			if (!this.valueNullable && value == null) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"加载的值为null,key=" + key);
			}

			cachedObject = new FinalObject<V>(value);
			FinalObject<V> existed = mappingMap.putIfAbsent(key, cachedObject);
			if (existed != null) {
				return existed.getObject();
			}
			return cachedObject.getObject();
		}
	}

	/**
	 * 移除
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key) {
		FinalObject<V> cachedObject = mappingMap.remove(key);
		return cachedObject == null ? null : cachedObject.getObject();
	}
}