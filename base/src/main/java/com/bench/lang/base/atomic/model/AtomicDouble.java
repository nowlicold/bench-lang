/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.atomic.model;

/**
 * 
 * @author cold
 *
 * @version $Id: AtomicDouble.java, v 0.1 2017年2月10日 下午3:30:16 cold Exp $
 */
public interface AtomicDouble {
	/**
	 * Atomically decrements by one the current value.
	 *
	 * @return the previous value
	 */
	double getAndDecrement();

	/**
	 * Atomically adds the given value to the current value.
	 *
	 * @param delta
	 *            the value to add
	 * @return the updated value
	 */
	double addAndGet(double delta);

	/**
	 * Atomically sets the value to the given updated value only if the current
	 * value {@code ==} the expected value.
	 *
	 * @param expect
	 *            the expected value
	 * @param update
	 *            the new value
	 * @return true if successful; or false if the actual value was not equal to
	 *         the expected value.
	 */
	boolean compareAndSet(double expect, double update);

	/**
	 * Atomically decrements the current value by one.
	 *
	 * @return the updated value
	 */
	double decrementAndGet();

	/**
	 * Gets the current value.
	 *
	 * @return the current value
	 */
	double get();

	/**
	 * Atomically adds the given value to the current value.
	 *
	 * @param delta
	 *            the value to add
	 * @return the old value before the add
	 */
	double getAndAdd(double delta);

	/**
	 * Atomically sets the given value and returns the old value.
	 *
	 * @param newValue
	 *            the new value
	 * @return the old value
	 */
	double getAndSet(double newValue);

	/**
	 * Atomically increments the current value by one.
	 *
	 * @return the updated value
	 */
	double incrementAndGet();

	/**
	 * Atomically increments the current value by one.
	 *
	 * @return the old value
	 */
	double getAndIncrement();

	/**
	 * Atomically sets the given value.
	 *
	 * @param newValue
	 *            the new value
	 */
	void set(double newValue);
}
