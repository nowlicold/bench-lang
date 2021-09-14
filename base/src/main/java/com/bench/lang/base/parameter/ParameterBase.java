package com.bench.lang.base.parameter;

import com.bench.lang.base.object.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class ParameterBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 259582039346462233L;

	/**
	 * 全局系统唯一名，当多个业务公用参数时，如果globalName相同，则认为是同一个参数
	 */
	protected String globalName;

	/**
	 * 参数
	 */
	protected String name;
	/**
	 * 数据值
	 */
	protected String value;

	/**
	 * 数据格式
	 */
	protected ParameterTypeEnum type;

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return Returns the type.
	 */
	public ParameterTypeEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(ParameterTypeEnum type) {
		this.type = type;
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
	 * @return Returns the globalName.
	 */
	public String getGlobalName() {
		return globalName;
	}

	/**
	 * @param globalName
	 *            The globalName to set.
	 */
	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
