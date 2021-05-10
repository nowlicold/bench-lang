/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.atomic.model.jdk;

import com.bench.lang.base.atomic.model.AtomicLong;

/**
 * 
 * @author cold
 *
 * @version $Id: JavaAtomicLong.java, v 0.1 2017年5月17日 下午1:13:08 cold Exp $
 */
public class JDKAtomicLong implements AtomicLong {

	private java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong();

	public JDKAtomicLong() {
		super();
	}

	public JDKAtomicLong(long initValue) {
		atomicLong.set(initValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#getAndDecrement()
	 */
	@Override
	public long getAndDecrement() {
		// TODO Auto-generated method stub
		return atomicLong.getAndDecrement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#addAndGet(long)
	 */
	@Override
	public long addAndGet(long delta) {
		// TODO Auto-generated method stub
		return atomicLong.addAndGet(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#compareAndSet(long, long)
	 */
	@Override
	public boolean compareAndSet(long expect, long update) {
		// TODO Auto-generated method stub
		return atomicLong.compareAndSet(expect, update);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#decrementAndGet()
	 */
	@Override
	public long decrementAndGet() {
		// TODO Auto-generated method stub
		return atomicLong.decrementAndGet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#get()
	 */
	@Override
	public long get() {
		// TODO Auto-generated method stub
		return atomicLong.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#getAndAdd(long)
	 */
	@Override
	public long getAndAdd(long delta) {
		// TODO Auto-generated method stub
		return atomicLong.getAndAdd(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#getAndSet(long)
	 */
	@Override
	public long getAndSet(long newValue) {
		// TODO Auto-generated method stub
		return atomicLong.getAndSet(newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#incrementAndGet()
	 */
	@Override
	public long incrementAndGet() {
		// TODO Auto-generated method stub
		return atomicLong.incrementAndGet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#getAndIncrement()
	 */
	@Override
	public long getAndIncrement() {
		// TODO Auto-generated method stub
		return atomicLong.getAndIncrement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.platform.config.atomic.AtomicLong#set(long)
	 */
	@Override
	public void set(long newValue) {
		// TODO Auto-generated method stub
		atomicLong.set(newValue);
	}

}
