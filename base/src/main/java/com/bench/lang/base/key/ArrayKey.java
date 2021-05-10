package com.bench.lang.base.key;

import java.util.Arrays;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 数组Key
 */
public class ArrayKey<T> extends ToStringObject {

	private final T[] value;

	private final int hashCode;

	public ArrayKey(T... value) {
		this.value = value;
		hashCode = Arrays.hashCode(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ArrayKey<?> arrayKey = (ArrayKey<?>) o;
		return hashCode == arrayKey.hashCode && Arrays.equals(value, arrayKey.value);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	public Object at(int index) {
		return value[index];
	}

	@Override
	public String toString() {
		return "ArrayKey{" + "value=" + Arrays.toString(value) + '}';
	}

	public T[] getValue() {
		return value;
	}

}
