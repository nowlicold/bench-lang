package com.bench.lang.base.payload.converter;

import com.bench.lang.base.payload.Payload;
import org.dom4j.Element;

/**
 * payload的值转换器,将值转换成xml,后者从xml转换
 * 
 * @author chenbug
 * 
 * @version $Id: PayloadValueConverter.java, v 0.1 2014-8-4 上午12:55:31 chenbug
 *          Exp $
 */
public interface PayloadValueConverter<T> {

	/**
	 * 获取data下的Element元素
	 * 
	 * @return
	 */
	public String getElementName();

	/**
	 * 将value转换到xml
	 * 
	 * @param payload
	 * @param dataElement
	 * @param name
	 * @param value
	 */
	public void toXml(Payload payload, Element dataElement, String name, T value);

	/**
	 * 从xml转换回数据
	 * 
	 * @param dataElm
	 * @return
	 */
	public T fromXml(Payload payload, Element dataElm, Element valueElm);

}
