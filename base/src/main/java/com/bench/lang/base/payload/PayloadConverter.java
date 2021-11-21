package com.bench.lang.base.payload;

import com.bench.lang.base.Dom4jUtils;
import com.bench.lang.base.payload.converter.PayloadValueConverter;
import com.bench.lang.base.payload.converter.PayloadValueConverterManager;
import com.bench.lang.base.string.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * payload从xml到payload，payload到xml转换器
 * 
 * @author cold
 *
 * @version $Id: PayloadConverter.java, v 0.1 2015年7月3日 下午12:08:59 Administrator
 *          Exp $
 */
public class PayloadConverter {

	public static final PayloadConverter INSTNACE = new PayloadConverter();

	/**
	 * 默认编码
	 */
	public static final String DEFAULT_XML_ENCODING = "UTF-8";

	/**
	 * data元素xpath路径
	 */
	public static final String DATA_XPATH_STRING = "/datas/data";

	/**
	 * values元素
	 */
	public static final String ELEMENT_VALUE = "value";

	/**
	 * datas元素
	 */
	public static final String ELEMENT_DATAS = "datas";

	/**
	 * name属性
	 */
	public static final String ATTRIBUTE_NAME = "name";

	/**
	 * type属性
	 */
	public static final String ATTRIBUTE_TYPE = "type";

	/**
	 * data属性
	 */
	public static final String ELEMENT_DATA = "data";

	/**
	 * value属性
	 */
	public static final String ATTRIBUTE_VALUE = "value";

	/**
	 * key属性
	 */
	public static final String ATTRIBUTE_KEY = "key";

	/**
	 * entry元素
	 */
	public static final String ELEMENT_ENTRY = "entry";

	/**
	 * 从XML解析
	 * 
	 * @param xml
	 */
	public Payload parseXml(String xml) {
		Payload payload = new Payload();
		Document document = Dom4jUtils.fromXml(xml);
		List<?> dataElmList = document.selectNodes(DATA_XPATH_STRING);
		for (Object dataElmObj : dataElmList) {
			Element dataElm = (Element) dataElmObj;
			parseDataXmlElement(payload, dataElm);
		}
		return payload;
	}

	/**
	 * 从XML解析
	 * 
	 * @param datasElement
	 */
	public Payload parseXml(Element datasElement) {
		Payload payload = new Payload();
		List<?> dataElmList = datasElement.selectNodes(DATA_XPATH_STRING);
		for (Object dataElmObj : dataElmList) {
			Element dataElm = (Element) dataElmObj;
			parseDataXmlElement(payload, dataElm);
		}
		return payload;
	}

	

	/**
	 * 解析Xml元素
	 * 
	 * @param dataElm
	 */
	protected void parseDataXmlElement(Payload payload, Element dataElm) {
		String name = StringUtils.trim(dataElm.attributeValue(ATTRIBUTE_NAME));
		List<?> childElmList = dataElm.elements();

		PayloadValueConverter<?> converter = null;
		if (childElmList.size() == 0) {
			converter = getParseXmlConverter(dataElm, null);
			payload.datas.put(name, converter.fromXml(payload, dataElm, null));
		} else {
			Element childElm = (Element) childElmList.get(0);
			converter = getParseXmlConverter(dataElm, childElm);
			payload.datas.put(name, converter.fromXml(payload, dataElm, childElm));
		}
	}

	/**
	 * 根据dataElement和子元素获取转换为数据对象的转换器
	 * 
	 * @param dataElm
	 * @param childElm
	 * @return
	 */
	protected PayloadValueConverter<?> getParseXmlConverter(Element dataElm, Element childElm) {
		if (childElm == null) {
			return PayloadValueConverterManager.getDefaultPayloadConverter();
		}
		PayloadValueConverter<?> converter = PayloadValueConverterManager.getConverter(childElm.getName());
		if (converter == null) {
			converter = PayloadValueConverterManager.getDefaultPayloadConverter();
		}
		return converter;
	}

	/**
	 * 将Payload转换成XMl
	 * 
	 * @return
	 */
	public String toXml(Payload payload) {
		Document document = Dom4jUtils.createNew();
		Element rootElm = document.addElement(ELEMENT_DATAS);
		for (Map.Entry<String, Object> entry : payload.datas.entrySet()) {
			Element dataElm = rootElm.addElement(ELEMENT_DATA);
			toXmlElement(payload, entry, dataElm);
		}
		return Dom4jUtils.asXml(document, DEFAULT_XML_ENCODING, false);
	}

	/**
	 * 将Payload转换成Document
	 * 
	 * @return
	 */
	public Document toDocument(Payload payload) {
		Document document = Dom4jUtils.createNew();
		Element rootElm = document.addElement(ELEMENT_DATAS);
		for (Map.Entry<String, Object> entry : payload.datas.entrySet()) {
			Element dataElm = rootElm.addElement(ELEMENT_DATA);
			toXmlElement(payload, entry, dataElm);
		}
		return document;
	}

	/**
	 * 将键值数据转换成XML数据
	 * 
	 * @param entry
	 * @param dataElm
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void toXmlElement(Payload payload, Map.Entry<String, Object> entry, Element dataElm) {
		String name = StringUtils.trim(entry.getKey());
		dataElm.addAttribute(ATTRIBUTE_NAME, name);
		if (entry.getValue() == null) {
			return;
		}
		PayloadValueConverter converter = getToXmlConverter(entry.getValue().getClass());
		converter.toXml(payload, dataElm, name, entry.getValue());
	}

	/**
	 * 根据class类型得到转换为XML的转换器
	 * 
	 * @param clasz
	 * @return
	 */
	protected PayloadValueConverter<?> getToXmlConverter(Class<?> clasz) {
		PayloadValueConverter<?> converter = PayloadValueConverterManager.getConverter(clasz);
		if (converter == null) {
			converter = PayloadValueConverterManager.getDefaultPayloadConverter();
		}
		return converter;
	}
}
