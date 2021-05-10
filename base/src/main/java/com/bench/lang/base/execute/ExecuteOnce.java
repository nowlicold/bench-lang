package com.bench.lang.base.execute;

import com.bench.lang.base.object.BooleanObject;

/**
 * 执行一次
 * 
 * @author cold
 *
 * @version $Id: ExecuteOnce.java, v 0.1 2019年2月7日 上午9:47:57 cold Exp $
 */
public abstract class ExecuteOnce {

	private BooleanObject executed = new BooleanObject(false);

	/**
	 * 内部执行
	 */
	protected abstract void executeInternal();

	/**
	 * 执行一次
	 */
	public void execute() {
		if (executed.booleanValue()) {
			return;
		}
		synchronized (executed) {
			if (executed.booleanValue()) {
				return;
			}
			executeInternal();
			executed.setBoolean(true);
		}
	}
}
