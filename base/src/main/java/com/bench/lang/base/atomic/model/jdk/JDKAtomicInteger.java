/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.atomic.model.jdk;

import com.bench.lang.base.atomic.model.AtomicInteger;

/**
 * 
 * @author cold
 *
 * @version $Id: JavaAtomicInteger.java, v 0.1 2017年5月17日 下午1:13:08 cold Exp $
 */
public class JDKAtomicInteger implements AtomicInteger {

	private java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger();

	public JDKAtomicInteger() {
		super();
	}

	public JDKAtomicInteger(int initValue) {
		atomicInteger.set(initValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#getAndDecrement()
	 */
	@Override
	public int getAndDecrement() {
		// TODO Auto-generated method stub
		return atomicInteger.getAndDecrement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#addAndGet(int)
	 */
	@Override
	public int addAndGet(int delta) {
		// TODO Auto-generated method stub
		return atomicInteger.addAndGet(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#compareAndSet(int, int)
	 */
	@Override
	public boolean compareAndSet(int expect, int update) {
		// TODO Auto-generated method stub
		return atomicInteger.compareAndSet(expect, update);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#decrementAndGet()
	 */
	@Override
	public int decrementAndGet() {
		// TODO Auto-generated method stub
		return atomicInteger.decrementAndGet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#get()
	 */
	@Override
	public int get() {
		// TODO Auto-generated method stub
		return atomicInteger.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#getAndAdd(int)
	 */
	@Override
	public int getAndAdd(int delta) {
		// TODO Auto-generated method stub
		return atomicInteger.getAndAdd(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#getAndSet(int)
	 */
	@Override
	public int getAndSet(int newValue) {
		// TODO Auto-generated method stub
		return atomicInteger.getAndSet(newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#incrementAndGet()
	 */
	@Override
	public int incrementAndGet() {
		// TODO Auto-generated method stub
		return atomicInteger.incrementAndGet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#getAndIncrement()
	 */
	@Override
	public int getAndIncrement() {
		// TODO Auto-generated method stub
		return atomicInteger.getAndIncrement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicInteger#set(int)
	 */
	@Override
	public void set(int newValue) {
		// TODO Auto-generated method stub
		atomicInteger.set(newValue);
	}

}
