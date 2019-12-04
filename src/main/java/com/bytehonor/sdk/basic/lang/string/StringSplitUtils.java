package com.bytehonor.sdk.basic.lang.string;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bytehonor.sdk.basic.lang.constant.CharConstants;

public class StringSplitUtils {

	public static List<String> splitWithBlank(String src) {
		return split(src, CharConstants.BLANK);
	}

	public static List<String> split(String src, char sp) {
		if (StringUtils.isEmpty(src)) {
			return new ArrayList<String>();
		}
		int length = src.length();
		List<String> res = new ArrayList<String>(length);
		int begin = 0;
		int count = 0;
		boolean findOne = false;
		boolean beginNewOne = true;
		char[] charArray = src.toCharArray();
		for (int i = 0; i < length; i++) {
			if (sp != charArray[i]) {
				if (beginNewOne) {
					begin = i;
					beginNewOne = false;
				}
				count++;
				findOne = false;
			} else {
				findOne = true;
			}

			if (findOne && count > 0) {
				res.add(new String(charArray, begin, count));
				count = 0;
				beginNewOne = true;
			}
		}

		if (count > 0) {
			res.add(new String(charArray, begin, count));
		}

		return res;
	}
}
