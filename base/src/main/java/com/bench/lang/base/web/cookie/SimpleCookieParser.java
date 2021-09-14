/*
 * Copyright (c) 2008-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bench.lang.base.web.cookie;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.bench.common.model.StringKV;

/**
 * 简单cookie解析器，只能解析<code> k=v; </code>方式的cookie
 * 
 * @author chenbug
 *
 * @version $Id: SimpleCookieParser.java, v 0.1 2016年2月24日 下午12:57:20 chenbug
 *          Exp $
 */
public class SimpleCookieParser {
	private SimpleCookieParser() {
	}

	public static List<StringKV> parse(String header) throws ParseException {
		List<StringKV> returnList = new ArrayList<StringKV>();
		State state = State.NAME;
		String name = null;
		int begin = 0;
		boolean quoted = false;

		for (int end = 0; end < header.length(); ++end) {
			char ch = header.charAt(end);
			switch (state) {
			case NAME: {
				if (ch == '$') {
					state = State.SEPARATOR;
				} else if (ch == '=') {
					name = header.substring(begin, end).trim();
					if (name.length() == 0)
						throw new ParseException(header, end);
					state = State.VALUE;
					begin = end + 1;
				}
				break;
			}
			case VALUE: {
				// Skip initial whitespace.
				if (Character.isWhitespace(ch) && begin == end) {
					begin = end + 1;
					break;
				}

				switch (ch) {
				case '"': {
					if (quoted) {
						String value = header.substring(begin, end).trim();
						returnList.add(new StringKV(name, value));
						state = State.SEPARATOR;
						name = null;
						quoted = false;
					} else {
						quoted = true;
						begin = end + 1;
					}
					break;
				}
				case ';': {
					if (!quoted) {
						String value = header.substring(begin, end).trim();
						returnList.add(new StringKV(name, value));
						state = State.NAME;
						name = null;
						begin = end + 1;
					}
					break;
				}
				default: {
					if (end + 1 == header.length()) {
						if (quoted)
							throw new ParseException(header, begin);
						String value = header.substring(begin, end + 1).trim();
						returnList.add(new StringKV(name, value));
					}
					break;
				}
				}
				break;
			}
			case SEPARATOR: {
				if (ch == ';') {
					state = State.NAME;
					name = null;
					begin = end + 1;
				}
				break;
			}
			}
		}
		return returnList;
	}

	private enum State {
		NAME, VALUE, SEPARATOR
	}
}
