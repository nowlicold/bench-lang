/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.parameter;


import com.bench.lang.base.properties.utils.PropertiesUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map类参数<br>
 * MapParameter mapParameter= new MapParameter(....);<br>
 * mapParameter.put(....);<br>
 * mapParameter.put(....);<br>
 * mapParameter.toParameter();
 * 
 * @author chenbug
 * 
 * @version $Id: MapParameter.java, v 0.1 2013-1-17 下午7:44:36 chenbug Exp $
 */
public class MapParameter implements Map<String, String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3579695921772630542L;

	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * 全局系统唯一名，当多个业务公用参数时，如果globalName相同，则认为是同一个参数
	 */
	protected String globalName;

	/**
	 * 参数
	 */
	protected String name;

	public MapParameter(String globalName, String name) {
		super();
		this.globalName = globalName;
		this.name = name;
	}

	public MapParameter(String name) {
		super();
		this.name = name;
	}

	public Parameter toParameter() {
		return new Parameter(this.globalName, this.name, PropertiesUtils.convert2String(map, false),
				ParameterTypeEnum.PROPERTIES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public String get(Object key) {
		// TODO Auto-generated method stub
		return map.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String put(String key, String value) {
		// TODO Auto-generated method stub
		return map.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public String remove(Object key) {
		// TODO Auto-generated method stub
		return map.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		// TODO Auto-generated method stub
		map.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		map.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<String> values() {
		// TODO Auto-generated method stub
		return map.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Entry<String, String>> entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

}
