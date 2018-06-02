package io.github.nobe0716.iwsu.hash;

import org.junit.Test;

public class HashCodeBaseShortenerTest {

	@Test
	public void digest() {
	}

	/**
	 * 이론적으로 발생할 수 있는 key의 최대 값 체크
	 * (hashCode의 최대값 Integer.MAX + (양수를 위한 보정값 -Integer.MAX_VALUE)
	 * 6글자이므로 기존 HashRule 체계 변경
	 */
	@Test
	public void testMaximumHashStr() {
		long maximumKeyVal = (long) Integer.MAX_VALUE - (long) Integer.MIN_VALUE;
		String str = HashCodeBaseShortener.decodeFromHash(maximumKeyVal);
		System.out.println(str);
	}
}