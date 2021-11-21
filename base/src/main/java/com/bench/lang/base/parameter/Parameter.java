package com.bench.lang.base.parameter;

/**
 * 参数
 * 
 * @author cold
 * 
 * @version $Id: Parameter.java, v 0.1 2013-1-4 下午12:03:08 cold Exp $
 */
public class Parameter extends ParameterBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5513269985833535538L;

	public Parameter() {
		super();
	}

	public Parameter(String name, String value, ParameterTypeEnum type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public Parameter(String globalName, String name, String value, ParameterTypeEnum type) {
		super();
		this.globalName = globalName;
		this.name = name;
		this.value = value;
		this.type = type;
	}
}
