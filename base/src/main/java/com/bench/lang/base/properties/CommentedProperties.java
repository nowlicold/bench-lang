package com.bench.lang.base.properties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

/**
 * <br/>
 * Example:<br/>
 * <br/>
 * 
 * <pre>
 * ## General comment line 1
 * ## General comment line 2
 * ##!General comment line 3, is ignored and not loaded
 * ## General comment line 4
 * 
 * 
 * # Property A comment line 1
 * A=1
 * 
 * # Property B comment line 1
 * # Property B comment line 2
 * B=2
 * 
 * ! Property C comment line 1
 * ! Property C comment line 2
 * 
 * C=3
 * D=4
 * 
 * # Property E comment line 1
 * ! Property E comment line 2  
 * E=5
 * 
 * # Property F comment line 1
 * #!Property F comment line 2, is ignored and not loaded
 * ! Property F comment line 3  
 * F=5
 * </pre>
 * 
 * @author Frederik Heick
 * @since 1.4
 */
public class CommentedProperties {

	private final static String MARKER_GENERAL_COMMENT = "##";
	private final static String MARKER_PROPERTY_COMMENT_1 = "#";
	private final static String MARKER_PROPERTY_COMMENT_2 = "!";
	private final static String NEW_LINE = "\n";
	private final static String EQUALS_SIGN = "=";
	private final static String MULTI_LINE_INDICATOR = "\\";
	private final static String MARKER_GENERAL_COMMENT_IGNORE = "##!";
	private final static String MARKER_PROPERTY_COMMENT_IGNORE = "#!";
	//
	private List<String> propertiesComment = new ArrayList<String>();
	private Hashtable<String, List<String>> comments = new Hashtable<String, List<String>>();
	private Properties properties = new Properties();

	//

	/**
	 * Constructor
	 */
	public CommentedProperties() {
		super();
	}

	/**
	 * Constructor with preset comments, not comments is set.
	 * 
	 * @param properties
	 *            the properties to use.
	 */
	public CommentedProperties(Properties properties) {
		super();
		this.properties = properties;
	}

	private void reset() {
		this.propertiesComment = new ArrayList<String>();
		this.properties = new Properties();
		this.comments = new Hashtable<String, List<String>>();
	}

	/**
	 * Loads the properties and comments from a file.<br/>
	 * All entries are reset when loading a new file.
	 * 
	 * @param file
	 *            the file
	 * @throws IOException
	 *             if any IO exception.
	 * @see File
	 */
	public void load(File file) throws IOException {
		reset();
		loadProperties(new FileReader(file));
		List<String> lines = loadLines(new FileReader(file));
		parseComments(lines);
	}

	/**
	 * Loads the properties with a reader. <br/>
	 * The reader will be closed by the
	 * 
	 * @param reader
	 *            the reader instance.
	 * @throws IOException
	 *             if the an exception occurs reading the properties.
	 */
	/*
	 * public void load(Reader reader) throws IOException { reset(); try {
	 * loadProperties(reader); List<String> lines = loadLines(reader);
	 * parseComments(lines); } finally { reader.close(); }
	 * 
	 * }
	 */

	/**
	 * Loads the properties and comments from a string.<br/>
	 * All entries are reset when loading a new file.
	 * 
	 * @param text
	 *            the text containing properties and comments.
	 * @throws IOException
	 *             if any IO exception.
	 */
	public void load(String text) throws IOException {
		reset();
		loadProperties(new StringReader(text));
		List<String> lines = loadLines(new StringReader(text));
		parseComments(lines);
	}

	public String toString() {
		StringWriter sw = new StringWriter();
		try {
			this.store(sw);
		} catch (IOException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR.errorCode(), "转换为String异常", e);
		}
		return sw.toString();
	}

	/**
	 * Writes this property list (key and element pairs) and comments in this
	 * Properties table to the output character stream in a format suitable for
	 * using the load(Reader) method.<br/>
	 * The writer is closed when done.
	 * 
	 * @param writer
	 *            the writer instance.
	 * @throws IOException
	 *             if any IO exception.
	 * @see Writer
	 */
	public void store(Writer writer) throws IOException {
		try {
			if (hasPropertiesCommment()) {
				for (String token : getPropertiesComment()) {
					writer.write(MARKER_GENERAL_COMMENT);
					writer.write(" ");
					writer.write(token);
					writer.write(NEW_LINE);
				}
				writer.write(NEW_LINE);
			}
			List<String> keys = getPropertyNamesAsList();
			for (String key : keys) {
				if (hasPropertyComment(key)) {
					List<String> tokens = getPropertyComment(key);
					for (String token : tokens) {
						writer.write(MARKER_PROPERTY_COMMENT_1);
						writer.write(" ");
						writer.write(token);
						writer.write(NEW_LINE);
					}
				}
				String value = getPropertyValue(key);
				if (value.contains(NEW_LINE)) {
					String[] tokens = value.split(NEW_LINE);
					writer.write(key);
					writer.write(EQUALS_SIGN);
					for (int index = 0; index < tokens.length; index++) {
						String token = tokens[index];
						writer.write(token);
						if ((index + 1) != tokens.length) {
							writer.write(MULTI_LINE_INDICATOR);
						}
						writer.write(NEW_LINE);
					}
				} else {
					writer.write(key);
					writer.write(EQUALS_SIGN);
					writer.write(value);
					writer.write(NEW_LINE);
					writer.write(NEW_LINE);
				}
			}
		} finally {
			writer.flush();
			// writer.close();
		}
	}

	/**
	 * Gets a iterator with all the property keys.
	 * 
	 * @return the iterator with property keys.
	 */
	public Iterator<String> getPropertyNames() {
		return properties.stringPropertyNames().iterator();
	}

	/**
	 * Get a list of all property keys as a sorted unmodifiable list.
	 * 
	 * @return a unmodifiable list of sorted property keys.
	 */
	public List<String> getPropertyNamesAsList() {
		List<String> list = new ArrayList<String>();
		Iterator<String> it = getPropertyNames();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	/**
	 * Gets the value for a property.
	 * 
	 * @param key
	 *            the property key.
	 * @return the value for the property.
	 */
	public String getPropertyValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Gets the comment for a property as a list of strings.
	 * 
	 * @param key
	 *            the property key.
	 * @return a list of strings which are the comments for the property, if no
	 *         comment for the property, than <code>null</code> is returned.
	 */
	public List<String> getPropertyComment(String key) {
		return comments.get(key);
	}

	/**
	 * Gets the comment for a property as a string Concatinated with a new line
	 * '\n'
	 * 
	 * @param key
	 *            the property key.
	 * @return the property comment, if no comment for the property, than
	 *         <code>null</code> is returned.
	 */
	public String getPropertyCommentAsString(String key) {
		return asString(getPropertyComment(key));
	}

	/**
	 * Gets the general properties comments as a list og strings.
	 * 
	 * @return a list of strings which the general comment for the properties,
	 *         if no comment, than <code>null</code> is returned.
	 */
	public List<String> getPropertiesComment() {
		if (hasPropertiesCommment()) {
			return propertiesComment;
		} else {
			return null;
		}
	}

	/**
	 * Gets the general properties comments as a string Concatinated with a new
	 * line '\n'
	 * 
	 * @return the general properties, if no comment for the property, than
	 *         <code>null</code> is returned.
	 */
	public String getPropertiesCommentAsString() {
		return asString(getPropertiesComment());
	}

	/**
	 * If there any general properties comment.
	 * 
	 * @return <code>true</code> if the properties comment is not
	 *         <code>null</code> and not empty after trim, otherwise
	 *         <code>false</code>.
	 */
	public boolean hasPropertiesCommment() {
		return propertiesComment != null && propertiesComment.size() > 0;
	}

	/**
	 * If there a comment to a given property.
	 * 
	 * @param key
	 *            the property key.
	 * @return @return <code>true</code> if the property comment is not
	 *         <code>null</code> and not empty after trim, otherwise
	 *         <code>false</code>.
	 */
	public boolean hasPropertyComment(String key) {
		List<String> comment = getPropertyComment(key);
		return comment != null && comment.size() > 0;
	}

	/**
	 * Set the general properties comment.<br/>
	 * The string will be split in lines for each new line.
	 * 
	 * @param comment
	 *            the comment.
	 */
	public void setPropertiesComment(String comment) {
		if (comment == null) {
			this.propertiesComment.clear();
		} else {
			this.propertiesComment = new ArrayList<String>();
			this.propertiesComment.addAll(Arrays.asList(comment.split(NEW_LINE)));
		}
	}

	/**
	 * Appends a comment to the properties comment with a new line a front.<br/>
	 * Will only add the line if it is not <code>null</code>. If there is no
	 * current properties comment the line is just set with a new line.<br/>
	 * The string will be split in lines for each new line.
	 * 
	 * @param comment
	 *            the comment to append.
	 */
	public void appendPropertiesComment(String comment) {
		if (comment != null) {
			this.propertiesComment.addAll(Arrays.asList(comment));
		}
	}

	/**
	 * Sets a property comment.<br/>
	 * The string will be split in lines for each new line.
	 * 
	 * @param key
	 *            the property key.
	 * @param comment
	 *            the new property comment.
	 */
	public void setPropertyComment(String key, String comment) {
		List<String> commentList = getPropertyComment(key);
		if (commentList == null) {
			commentList = new ArrayList<String>();
			comments.put(key, commentList);
		} else {
			commentList.clear();
		}
		commentList.addAll(Arrays.asList(comment.split(NEW_LINE)));
	}

	public void remove(String key) {
		this.properties.remove(key);
		this.comments.remove(key);
	}

	/**
	 * Appends a comment to the property comment with a new line a front.<br/>
	 * Will only add the line if it is not <code>null</code>. If there is no
	 * current property comment the line is just set with a new line.
	 * 
	 * @param key
	 *            the property key.
	 * @param comment
	 *            the comment to append.
	 */
	public void appendPropertyComment(String key, String comment) {
		if (comment != null) {
			if (hasPropertyComment(key)) {
				getPropertyComment(key).addAll(Arrays.asList(comment));
			} else {
				setPropertyComment(key, comment);
			}
		}
	}

	/**
	 * Sets a property value.
	 * 
	 * @param key
	 *            the property key.
	 * @param value
	 *            the new property value.
	 */
	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}

	/**
	 * Sets a property value and comment.
	 * 
	 * @param key
	 *            the property key.
	 * @param value
	 *            the new property value.
	 * @param comment
	 *            the new property comment.
	 */
	public void setProperty(String key, String value, String comment) {
		setProperty(key, value);
		setPropertyComment(key, comment);
	}

	/**
	 * The number of properties.
	 * 
	 * @return the number of properties.
	 */
	public int size() {
		return properties.size();
	}

	/**
	 * Gets the properties loaded.
	 * 
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Gets the number of properties with a comment attached.
	 * 
	 * @return the comment count.
	 */
	public int getPropertiesWithCommentCount() {
		int result = 0;
		List<String> keys = getPropertyNamesAsList();
		for (String key : keys) {
			if (hasPropertyComment(key)) {
				result++;
			}
		}
		return result;
	}

	private boolean isPropertyComment(String line) {
		if (line == null) {
			return false;
		} else if (isPropertiesComment(line)) {
			return false;
		} else if (line.trim().startsWith(MARKER_PROPERTY_COMMENT_1)) {
			return true;

		} else if (line.trim().startsWith(MARKER_PROPERTY_COMMENT_2)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isPropertiesComment(String line) {
		if (line == null) {
			return false;
		} else if (line.trim().startsWith(MARKER_GENERAL_COMMENT)) {
			return true;
		} else {
			return false;
		}
	}

	private void parseComments(List<String> lines) {
		// class comments
		for (String line : lines) {
			if (isPropertiesComment(line)) {
				if (!line.trim().startsWith(MARKER_GENERAL_COMMENT_IGNORE)) {
					appendPropertiesComment(line.substring(2).trim());
				}
			}
			if ((!isPropertyComment(line)) && (line.indexOf('=') > 0)) {
				break;
			}
		}

		// property comments
		Iterator<String> it = getPropertyNames();
		while (it.hasNext()) {
			String key = it.next();
			int index = 0;
			String result = null;
			int resultIndex = -1;
			while ((result == null) && (index < lines.size())) {
				String line = lines.get(index);
				if (line.startsWith(key)) {
					String tmp = line.substring(key.length()).trim();
					if (tmp.startsWith(EQUALS_SIGN)) {
						result = line;
						resultIndex = index;
					}
				}
				index++;
			}
			if (result != null) {
				List<String> comment = new ArrayList<String>();
				for (int i = resultIndex - 1; i >= 0; i--) {
					String line = lines.get(i).trim();
					if (isPropertyComment(line)) {
						if (!line.trim().startsWith(MARKER_PROPERTY_COMMENT_IGNORE)) {
							comment.add(line.trim().substring(1).trim());
						}
					} else if ((!isPropertyComment(line)) && (line.trim().length() > 0)) {
						break;
					}
				}
				if (comment.size() > 0) {
					Collections.reverse(comment);
					comments.put(key, comment);
				}
			}
		}
	}

	private void loadProperties(Reader reader) throws IOException {
		try {
			properties.load(reader);
		} finally {
			reader.close();
		}
	}

	private List<String> loadLines(Reader reader) throws IOException {
		try {
			List<String> lines = new ArrayList<String>();
			LineNumberReader lnr = null;
			lnr = new LineNumberReader(reader);
			String line = lnr.readLine();
			while (line != null) {
				lines.add(line);
				line = lnr.readLine();
			}
			return lines;
		} finally {
			reader.close();
		}
	}

	private String asString(List<String> list) {
		if (list != null) {
			StringBuilder s = new StringBuilder();
			for (int index = 0; index < list.size(); index++) {
				if (index != 0) {
					s.append(NEW_LINE);
				}
				s.append(list.get(index));
			}
			return s.toString();
		} else {
			return null;
		}
	}

	public Map<String, String> toMap() {
		return PropertiesUtils.toMap(this.properties);
	}
}
