/**
 * 
 */
package com.bench.lang.base.error;

import com.bench.lang.base.enums.EnumBase;
import com.bench.lang.base.string.build.ToStringObject;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * <p>
 * 
 * </p>
 * 
 * @author cold
 * @version $Id: ErrorCode.java,v 0.1 2009-8-24 上午11:04:35 cold Exp $
 */

public class ErrorCode extends ToStringObject {

	private String name;

	private String message;

	public String name() {
		return name;
	}

	public ErrorCode() {
		super();
	}

	public boolean equals(EnumBase enumBase) {
		return StringUtils.equals(this.name, enumBase.name());
	}

	public ErrorCode(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public ErrorCode(EnumBase enumBase) {
		super();
		this.name = enumBase.name();
		this.message = enumBase.message();
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}
		if (obj instanceof ErrorCode) {
			return ((ErrorCode) obj).name.equals(this.name);
		}
		if (obj instanceof EnumBase) {
			return ((EnumBase) obj).name().equals(this.name);
		}
		return false;
	}

}
