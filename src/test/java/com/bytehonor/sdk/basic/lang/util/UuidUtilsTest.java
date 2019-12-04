package com.bytehonor.sdk.basic.lang.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UuidUtilsTest {

	@Test
	public void testGetSimple() {
		String src = UuidUtils.getSimple();
		boolean isOk = src != null && src.length() == 32;
		assertTrue("testGetSimple", isOk);
	}

	@Test
	public void testGetFull() {
		String src = UuidUtils.getFull();
		boolean isOk = src != null && src.length() == 36;
		assertTrue("testGetFull", isOk);
	}

	public void testTime() {
		int size = 10000;
		long start1 = System.nanoTime();
		for (int i=0;i<size;i++) {
			UuidUtils.getFull().replaceAll("-", "");
		}
		long cost1 = System.nanoTime() - start1;
		
		long start2 = System.nanoTime();
		for (int i=0;i<size;i++) {
			UuidUtils.getSimple();
		}
		long cost2 = System.nanoTime() - start2;
		
		System.out.println("cost1:" + cost1 + ", cost2:" + cost2 + ", diff:" + (cost1 - cost2));
	}
}
