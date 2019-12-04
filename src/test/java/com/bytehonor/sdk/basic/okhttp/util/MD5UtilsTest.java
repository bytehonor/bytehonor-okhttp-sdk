package com.bytehonor.sdk.basic.okhttp.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bytehonor.sdk.basic.okhttp.util.MD5Utils;

public class MD5UtilsTest {

	@Test
	public void testMd5() {
		long now = System.currentTimeMillis();
		
		String md5 = MD5Utils.md5("1910259602" + "8b98f937554a180c9a6a" + now);
		System.out.println("now:" + now);
		System.out.println("md5:" + md5.toLowerCase());
		boolean isOk = md5 != null && md5.length() > 30;
		assertTrue("testBuild", isOk);
	}

}
