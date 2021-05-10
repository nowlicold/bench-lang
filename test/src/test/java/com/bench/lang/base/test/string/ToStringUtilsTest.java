package com.bench.lang.base.test.string;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.bench.lang.base.list.utils.ListUtils;

public class ToStringUtilsTest {

	@Test
	public void listToString() {
		List<String> list = ListUtils.toList("1", "2", "3", "4");
		System.out.println(ToStringBuilder.reflectionToString(list, ToStringStyle.SIMPLE_STYLE));
	}
}
