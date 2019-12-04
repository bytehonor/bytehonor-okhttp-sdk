package com.bytehonor.sdk.basic.lang.util;

import java.util.UUID;

/**
 * Unid Utils
 * 
 * @author lijianqiang
 *
 */
public class UuidUtils {
	
	private static final char CON = '-';

	/**
	 * uuid by uuid
	 * 
	 * @return string without '-'
	 */
	public static String getSimple() {
		String full = getFull();
		int len = full.length();
		int count = 0;
		char[] target = new char[len];
		for (int i=0;i<len;i++) {
			if (full.charAt(i) != CON) {
				target[count++] = full.charAt(i);
			}
		}
		return new String(target, 0, count);
	}

	/**
	 * guid by uuid, toLowerCase
	 * 
	 * @return string with '-'
	 */
	public static String getFull() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
