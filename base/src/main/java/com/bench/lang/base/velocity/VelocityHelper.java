/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.lang.base.velocity;

import com.bench.lang.base.Constants;
import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.base64.utils.BASE64Utils;
import com.bench.lang.base.bean.utils.PropertyUtils;
import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.charset.utils.CharsetUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.collection.utils.CollectionUtils;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.enums.utils.EnumBaseUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.log.BenchConsoleLogger;
import com.bench.lang.base.map.utils.MapUtils;
import com.bench.lang.base.math.utils.MathUtils;
import com.bench.lang.base.math.utils.PercentageUtils;
import com.bench.lang.base.money.utils.MoneyUtils;
import com.bench.lang.base.money.utils.RMBMoneyUtils;
import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.os.linux.utils.LinuxUtils;
import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.lang.base.set.utils.SetUtils;
import com.bench.lang.base.string.utils.StringEscapeUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.system.environment.SystemEnvironment;
import com.bench.lang.base.system.utils.SystemUtils;
import com.bench.lang.base.uri.utils.URIUtils;
import com.bench.lang.base.url.utils.URLCodecUtils;
import com.bench.lang.base.url.utils.UrlUtils;
import com.bench.lang.base.utils.IOUtils;
import com.bench.lang.base.uuid.utils.UUIDUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.generic.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Velocity???????????????
 * 
 * @author cold
 */
public class VelocityHelper {

	static {
		// ??????velocity????????????
		Velocity.setProperty("RUNTIME_LOG_LOGSYSTEM_CLASS", "org.apache.velocity.runtime.log.NullLogChute");
	}

	/** ???????????? */
	public static final VelocityHelper INSTANCE = new VelocityHelper();

	private VelocityEngine ve = new VelocityEngine();
	private static final AlternatorTool alternator_instance = new AlternatorTool();

	private static final ComparisonDateTool date_instance = new ComparisonDateTool();

	private static final EscapeTool esc_instance = new EscapeTool();

	private static final MathTool math_instance = new MathTool();

	private static final NumberTool number_instance = new NumberTool();

	private static final ResourceTool text_instance = new ResourceTool();
	public static Map<String, Object> getCommonToolMap() {
		Map<String, Object> commonToolMap = new HashMap<String, Object>();
		// ???????????????
		commonToolMap.put("_benchConsoleLog", BenchConsoleLogger.INSTANCE);
		commonToolMap.put("arrayUtils", ArrayUtils.INSTNACE);
		commonToolMap.put("codecUtils", URLCodecUtils.INSTANCE);
		commonToolMap.put("urlCodecUtils", URLCodecUtils.INSTANCE);
		commonToolMap.put("base64Utils", BASE64Utils.INSTANCE);
		commonToolMap.put("booleanUtils", BooleanUtils.INSTANCE);
		commonToolMap.put("collectionUtils", CollectionUtils.INSTANCE);
		commonToolMap.put("classUtils", ClassUtils.INSTANCE);
		commonToolMap.put("dateUtils", DateUtils.INSTANCE);
		commonToolMap.put("fieldUtils", FieldUtils.INSTANCE);
		commonToolMap.put("listUtils", ListUtils.INSTANCE);
		commonToolMap.put("numberUtils", NumberUtils.INSTANCE);
		commonToolMap.put("number", number_instance);
		commonToolMap.put("math", math_instance);
		commonToolMap.put("mathUtils", MathUtils.INSTANCE);
		commonToolMap.put("mapUtils", MapUtils.INSTANCE);
		commonToolMap.put("moneyUtils", MoneyUtils.INSTANCE);
		commonToolMap.put("objectUtils", ObjectUtils.INSTANCE);
		commonToolMap.put("propertiesUtils", PropertiesUtils.INSTANCE);
		commonToolMap.put("propertyUtils", PropertyUtils.INSTANCE);
		commonToolMap.put("percentageUtils", PercentageUtils.INSTANCE);
		commonToolMap.put("rmbMoneyUtils", RMBMoneyUtils.INSTANCE);
		commonToolMap.put("stringUtils", StringUtils.INSTANCE);
		commonToolMap.put("stringEscapeUtils", StringEscapeUtils.INSTANCE);
		commonToolMap.put("text", text_instance);
		commonToolMap.put("uriUtils", URIUtils.INSTANCE);
		commonToolMap.put("setUtils", SetUtils.INSTANCE);
		commonToolMap.put("urlUtils", UrlUtils.INSTANCE);
		commonToolMap.put("linuxUtils", LinuxUtils.INSTANCE);
		commonToolMap.put("uuidUtils", UUIDUtils.INSTANCE);
		commonToolMap.put("velocityHelper", VelocityHelper.INSTANCE);
		commonToolMap.put("enumBaseUtils", EnumBaseUtils.INSTANCE);
		commonToolMap.put("systemEnvironment", SystemEnvironment.getSystemEnvironment());
		return commonToolMap;
	}

	/** ?????????????????? */
	private VelocityHelper() {
		// ?????????velocity????????? ??????????????????Velocity???????????????

		// ?????????
		try {
			Velocity.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static VelocityHelper getInstance() {
		return INSTANCE;
	}

	public boolean evaluate(Context context, Writer writer, Reader reader) {
		try {
			return Velocity.evaluate(context, writer, "", reader);
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	public boolean evaluateExpression(Context context, Writer writer, Reader reader) {
		try {
			return ve.evaluate(context, writer, "", reader);
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ???????????????
	 * 
	 * @param map
	 * @param text
	 * @return
	 */
	public String evaluate(Map<String, ? extends Object> map, String text) {

		VelocityContext context = convertVelocityContext(map);
		try (Reader reader = new StringReader(text); Writer writer = new StringWriter();) {
			evaluate(context, writer, reader);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ???????????????
	 * 
	 * @param
	 * @param text
	 * @return
	 */
	public String evaluate(String text) {
		return evaluate(null, text);
	}

	public String evaluateExpression(Map<String, Object> map, String text) {
		VelocityContext context = convertVelocityContext(map);
		try (Reader reader = new StringReader(text); Writer writer = new StringWriter();) {
			evaluateExpression(context, writer, reader);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ??????Map?????????????????????
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluate(Map<String, ? extends Object> map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ????????????
			evaluate(context, writer, reader);
			// if(log.isInfoEnabled()){
			// log.info("????????????????????? \n");
			// log.info(writer.toString());
			// }
			// ?????????????????????(?????????)?????????????????????(?????????)
			byte[] dataBytes = writer.toString().getBytes();
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(dataBytes));
			return bis;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ??????Map?????????????????????
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluateExpression(Map<String, ? extends Object> map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ????????????
			evaluateExpression(context, writer, reader);
			// ?????????????????????(?????????)?????????????????????(?????????)
			byte[] dataBytes = writer.toString().getBytes();
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(dataBytes));
			return bis;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ??????Map?????????????????????
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public Writer evaluateToWriter(Map<String, ? extends Object> map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ????????????
			evaluate(context, writer, reader);

			return writer;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	public Writer evaluateExpressionToWriter(Map<String, ? extends Object> map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ????????????
			evaluateExpression(context, writer, reader);
			return writer;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ???????????????Configration?????????????????????
	 * 
	 * @param
	 * @param reader
	 * @return
	 */
	public InputStream evaluate(Reader reader) {
		return evaluate(new HashMap<String, Object>(), reader);
	}

	/**
	 * ???????????????Configration?????????????????????
	 * 
	 * @param
	 * @param reader
	 * @return
	 */
	public InputStream evaluateExpression(Reader reader) {
		return evaluateExpression(new HashMap<String, Object>(), reader);
	}

	/**
	 * ???????????????Configration?????????????????????
	 *
	 * @param reader
	 * @return
	 */
	public Writer evaluateToWriter(Reader reader) {
		return evaluateToWriter(new HashMap<String, Object>(), reader);
	}

	/**
	 * ???????????????Configration?????????????????????
	 * 
	 * @param reader
	 * @return
	 */
	public Writer evaluateExpressionToWriter(Reader reader) {
		return evaluateExpressionToWriter(new HashMap<String, Object>(), reader);
	}


	/**
	 * ???????????????Configration?????????????????????
	 * @param inputStream
	 * @return
	 */
	public Writer evaluateToWriter(InputStream inputStream) {
		return evaluateToWriter(new HashMap<String, Object>(), new InputStreamReader(inputStream));
	}

	/**
	 * ???????????????Configration?????????????????????
	 *
	 * @return
	 */
	public Writer evaluateExpressionToWriter(InputStream inputStream) {
		return evaluateExpressionToWriter(new HashMap<String, Object>(), new InputStreamReader(inputStream));
	}

	/**
	 * ???????????????Configration?????????????????????
	 *
	 * @return
	 */
	public InputStream evaluate(InputStream inputStream) {
		return evaluate(inputStream, null);
	}

	public InputStream evaluate(InputStream inputStream, Map<String, Object> contextMap) {
		try {
			byte[] byteContent = IOUtils.toByteArray(inputStream);
			Charset charset = CharsetUtils.GBK;
			if (CharsetUtils.isUtf8Charset(byteContent)) {
				charset = CharsetUtils.UTF_8;
			}
			String content = new String(byteContent, charset);
			Map<String, Object> map = new HashMap<String, Object>();
			if (contextMap != null) {
				map.putAll(contextMap);
			}
			String renderedContent = evaluate(map, content);
			return new ByteArrayInputStream(renderedContent.getBytes(charset));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @param inputStream
	 * @param contextMap
	 * @return
	 */
	public String evaluateToString(InputStream inputStream, Map<String, Object> contextMap) {
		try {
			byte[] byteContent = IOUtils.toByteArray(inputStream);
			Charset charset = CharsetUtils.GBK;
			if (CharsetUtils.isUtf8Charset(byteContent)) {
				charset = CharsetUtils.UTF_8;
			}
			String content = new String(byteContent, charset);
			Map<String, Object> map = new HashMap<String, Object>();
			if (contextMap != null) {
				map.putAll(contextMap);
			}
			String renderedContent = evaluate(map, content);
			return renderedContent;
		} catch (Exception e) {
			throw new RuntimeException( e);
		}

	}

	/**
	 * ???????????????Configration?????????????????????
	 *
	 * @return
	 */
	public InputStream evaluate(InputStream inputStream, Map<String, String> properties, String encoding) {
		String content = null;
		try {
			content = IOUtils.toString(evaluate(properties, new InputStreamReader(inputStream)), encoding);
		} catch (Exception e) {
			throw new RuntimeException( e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	/**
	 * ???????????????Configration?????????????????????
	 *
	 * @return
	 */
	public InputStream evaluateExpression(InputStream inputStream) {
		String content = null;
		try {
			content = IOUtils.toString(evaluateExpression(new HashMap<String, Object>(), new InputStreamReader(inputStream)), Constants.DEFAULT_CHARSET);
		} catch (Exception e) {
			throw new RuntimeException( e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	/**
	 * ??????Velocity????????????
	 * 
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return Velocity.getProperty(key);
	}

	public static Map<String, Object> getCommonContextMap() {
		return getCommonToolMap();
	}

	public <T extends Object> VelocityContext convertVelocityContext(Map<String, T> map) {
		VelocityContext context = new VelocityContext(getCommonContextMap());
		if (map == null)
			return context;
		Set<Map.Entry<String, T>> entrySet = map.entrySet();
		for (Map.Entry<String, T> entry : entrySet) {
			context.put(entry.getKey(), entry.getValue());
		}
		return context;
	}
}
