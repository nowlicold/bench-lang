package com.bench.lang.base.test.convert;

import java.util.Date;

import org.junit.Test;

import com.bench.lang.base.convert.Convert;
import com.bench.lang.base.list.utils.ListUtils;

/**
 * converter测试
 * 
 * @author cold
 *
 * @version $Id: ConvertTest.java, v 0.1 2020年4月2日 下午6:55:09 cold Exp $
 */
public class ConvertTest {

	@Test
	public void convert() {
		System.out.println(Convert.asType(CommonHttpErrorCodeEnum.class, CommonHttpErrorCodeEnum.CSRF_TOKEN_INVALID.name()));
		System.out.println(Convert.asType(Date.class, "2012-12-12 12:12:13"));
		System.out.println(Convert.asType(Integer[].class, ListUtils.toList("1", "2")));
	}
	@Test
	public void covertManager(){	

	}
}
