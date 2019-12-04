package com.bytehonor.sdk.basic.lang.util;

import java.util.Random;

/**
 * Random Utils
 * 
 * @author lijianqiang
 *
 */
public class RandomUtils {

	/** 数字与字母字典 */
	private static final char[] LETTER_AND_DIGIT = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
			.toCharArray();

	/** 数字与字母字典长度 */
	private static final int LETTER_AND_DIGIT_LENGTH = LETTER_AND_DIGIT.length;

	/**
	 * 取得随机数，左闭右闭
	 * 
	 * @param min
	 * @param max
	 * @return 生成的随机数
	 */
	public static int getInt(int min, int max) {
		Random random = new Random(System.nanoTime());
		int temp = random.nextInt(max - min + 1) + min;
		return temp;
	}

	/**
	 * 取得随机字符串
	 * 
	 * @param len
	 *            随机数长度
	 * @return 生成的随机数
	 */
	public static String getString(final int len) {
		if (len < 1) {
			return "";
		}
		Random random = new Random(System.nanoTime());
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(LETTER_AND_DIGIT[random.nextInt(LETTER_AND_DIGIT_LENGTH)]);
		}
		return sb.toString();
	}

}
