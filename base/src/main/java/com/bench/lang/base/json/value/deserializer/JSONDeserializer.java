package com.bench.lang.base.json.value.deserializer;

/**
 * JSON值序列化
 * 
 * @author chenbug
 * 
 * @version $Id: JSONSerializer.java, v 0.1 2012-6-25 下午12:19:08 chenbug Exp $
 */
public interface JSONDeserializer {

	/**
	 * 
	 * @param value
	 * @return
	 */
	Object serialize(Object value);
}
