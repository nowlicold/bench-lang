/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 解决int、long不能进入final的问题
 * 
 * @author cold
 * 
 * @version $Id: IntegerObject.java, v 0.1 2011-6-13 下午08:31:49 cold Exp $
 */
public class BigDecimalObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7995313448035457069L;

	private BigDecimal value;

	public BigDecimalObject(BigDecimal value) {
		super();
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * 当前对象加上addValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param addValue
	 * @return
	 */
	public BigDecimalObject addTo(BigDecimal addValue) {
		this.value = value.add(addValue);
		return this;
	}

	/**
	 * 当前对象加上addValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param addValue
	 * @return
	 */
	public BigDecimalObject addTo(BigDecimal addValue, MathContext mc) {
		this.value = value.add(addValue, mc);
		return this;
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象加addValue，但当前对象的值不变
	 * 
	 * @param addValue
	 * @return
	 */
	public BigDecimal add(BigDecimal addValue) {
		return value.add(addValue);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象加addValue，但当前对象的值不变
	 * 
	 * @param addValue
	 * @return
	 */
	public BigDecimal add(BigDecimal addValue, MathContext mc) {
		return value.add(addValue, mc);
	}

	/**
	 * 当前对象减去subValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimalObject subtractFrom(BigDecimal subValue) {
		this.value = value.subtract(subValue);
		return this;
	}

	/**
	 * 当前对象减去subValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimalObject subtractFrom(BigDecimal subValue, MathContext mc) {
		this.value = value.subtract(subValue, mc);
		return this;
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象减去subValue，但当前对象的值不变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimal subtract(BigDecimal subValue) {
		return this.value.subtract(subValue);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象减去subValue，但当前对象的值不变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimal subtract(BigDecimal subValue, MathContext mc) {
		return this.value.subtract(subValue, mc);
	}

	/**
	 * 当前对象乘以multiplyValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimalObject multiplyBy(BigDecimal multiplyValue) {
		this.value = value.multiply(multiplyValue);
		return this;
	}

	/**
	 * 当前对象乘以multiplyValue的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param multiplyValue
	 * @param mc
	 * @return
	 */
	public BigDecimalObject multiplyBy(BigDecimal multiplyValue, MathContext mc) {
		this.value = value.multiply(multiplyValue, mc);
		return this;
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象乘以multiplyValue，但当前对象的值不变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimal multiply(BigDecimal multiplyValue) {
		return this.value.multiply(multiplyValue);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象乘以multiplyValue，但当前对象的值不变
	 * 
	 * @param multiplyValue
	 * @param mc
	 * @return
	 */
	public BigDecimal multiply(BigDecimal multiplyValue, MathContext mc) {
		return this.value.multiply(multiplyValue, mc);
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimalObject divideBy(BigDecimal divisor) {
		this.value = value.divide(divisor);
		return this;
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public BigDecimalObject divideBy(BigDecimal divisor, int scale, RoundingMode roundingMode) {
		// TODO Auto-generated method stub
		this.value = this.value.divide(divisor, scale, roundingMode);
		return this;
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param divisor
	 * @param roundingMode
	 * @return
	 */
	public BigDecimalObject divideBy(BigDecimal divisor, RoundingMode roundingMode) {
		// TODO Auto-generated method stub
		this.value = this.value.divide(divisor, roundingMode);
		return this;
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param divisor
	 * @param mc
	 * @return
	 */
	public BigDecimalObject divideBy(BigDecimal divisor, MathContext mc) {
		// TODO Auto-generated method stub
		this.value = this.value.divide(divisor, mc);
		return this;
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param divisor
	 * @return
	 */
	public BigDecimalObject divideToIntegralValueBy(BigDecimal divisor) {
		// TODO Auto-generated method stub
		this.value = this.value.divideToIntegralValue(divisor);
		return this;
	}

	/**
	 * 当前对象除以divisor的值重新赋值给当前对象，并返回当前对象，注意：当前对象的值已改变
	 * 
	 * @param divisor
	 * @param mc
	 * @return
	 */
	public BigDecimalObject divideToIntegralValueBy(BigDecimal divisor, MathContext mc) {
		// TODO Auto-generated method stub
		this.value = this.value.divideToIntegralValue(divisor, mc);
		return this;
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param subValue
	 * @return
	 */
	public BigDecimal divide(BigDecimal divideValue) {
		return this.value.divide(divideValue);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
		// TODO Auto-generated method stub
		return this.value.divide(divisor, scale, roundingMode);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @param roundingMode
	 * @return
	 */
	public BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode) {
		// TODO Auto-generated method stub
		return this.value.divide(divisor, roundingMode);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @param mc
	 * @return
	 */
	public BigDecimal divide(BigDecimal divisor, MathContext mc) {
		// TODO Auto-generated method stub
		return this.value.divide(divisor, mc);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @return
	 */
	public BigDecimal divideToIntegralValue(BigDecimal divisor) {
		// TODO Auto-generated method stub
		return this.value.divideToIntegralValue(divisor);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @param mc
	 * @return
	 */
	public BigDecimal divideToIntegralValue(BigDecimal divisor, MathContext mc) {
		// TODO Auto-generated method stub
		return this.value.divideToIntegralValue(divisor, mc);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @return
	 */
	public BigDecimal[] divideAndRemainder(BigDecimal divisor) {
		// TODO Auto-generated method stub
		return this.value.divideAndRemainder(divisor);
	}

	/**
	 * 返回一个新的BigDecimal，该值为当前对象除以divisor，但当前对象的值不变
	 * 
	 * @param divisor
	 * @param mc
	 * @return
	 */
	public BigDecimal[] divideAndRemainder(BigDecimal divisor, MathContext mc) {
		// TODO Auto-generated method stub
		return this.value.divideAndRemainder(divisor, mc);
	}

}
