/**
 * benchcode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.collection.utils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 可自动删除的队列，当添加的原属超过容量时，自动remove最老的元素，然后加入新的。
 * 
 * @author cold
 *
 * @version $Id: RemoveableLinkedBlockingQueue.java, v 0.1 2010-7-24 下午04:59:53 cold Exp $
 */
public class RemoveableLinkedBlockingQueue<E> extends java.util.concurrent.LinkedBlockingQueue<E> {

	public RemoveableLinkedBlockingQueue() {
		super();
	}

	public RemoveableLinkedBlockingQueue(int capacity) {
		super(capacity);
	}

	public RemoveableLinkedBlockingQueue(Collection<? extends E> c) {
		super(c);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016609106136780303L;

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		while (!super.offer(e, timeout, unit)) {
			super.poll(timeout, unit);
		}
		return true;
	}

	@Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		while (!super.offer(e)) {
			super.poll();
		}
		return true;
	}

}
