package com.bytehonor.sdk.basic.okhttp.string;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.bytehonor.sdk.basic.okhttp.constant.CharConstants;

public class StringAlgorithm {

    private static final Logger LOG = LoggerFactory.getLogger(StringAlgorithm.class);

    /**
     * <pre>
     * 公共子串集合
     * 最长优先
     * </pre>
     * 
     * @param src1
     * @param src2
     * @param minLength:1
     * @return
     */
    public static List<String> commonSubstrings(String src1, String src2) {
        return commonSubstrings(src1, src2, 1);
    }

    /**
     * <pre>
     * 公共子串集合
     * 最长优先
     * </pre>
     * 
     * @param src1
     * @param src2
     * @param minLength
     * @return
     */
    public static List<String> commonSubstrings(String src1, String src2, int minLength) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src1)) {
            return result;
        }
        if (minLength < 1) {
            throw new RuntimeException("minLength is invalide");
        }
        String lcs = null;
        while ((lcs = longestCommonSubstring(src1, src2)) != null) {
            if (lcs.length() < minLength) {
                break;
            }
            result.add(lcs.trim());
            src1 = StringRemoveUtils.removeRegex(src1, lcs);
            src2 = StringRemoveUtils.removeRegex(src2, lcs);
            if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src1)) {
                break;
            }
        }
        return result;
    }

    /**
     * <pre>
     * 最长公共子串(Longest Common Substring)
     * 
     * https://www.cnblogs.com/ider/p/longest-common-substring-problem-optimization.html
     * </pre>
     * 
     * @param src1
     * @param src2
     * @return
     */
    public static int longestCommonSubstringLength(String src1, String src2) {
        int size1 = src1 != null ? src1.length() : 0;
        int size2 = src2 != null ? src2.length() : 0;
        if (size1 == 0 || size2 == 0) {
            return 0;
        }

        // the start position of substring in original string
        int start1 = -1;
        int start2 = -1;
        // the longest length of common substring
        int longest = 0;

        // record how many comparisons the solution did;
        // it can be used to know which algorithm is better
        int comparisons = 0;

        char[] str1 = src1.toCharArray();
        char[] str2 = src2.toCharArray();
        int c1At = 0;
        int c2At = 0;
        int length = 0;

        // "官方通报泉港碳九泄露", "福建泉港碳九泄露事件"
        // shift string1 to find the longest common substring
        for (int i = 0; i < size1; ++i) {
            c1At = i;
            c2At = 0;
            length = 0;
            if (size1 - c1At <= longest || size2 - c2At <= longest) {
                break;
            }
            while (c1At < size1 && c2At < size2) {
                ++comparisons;
                if (str1[c1At] != str2[c2At]) {
                    length = 0;
                } else {
                    ++length;
                    if (longest < length) {
                        longest = length;
                        start1 = c1At - longest + 1;
                        start2 = c2At - longest + 1;
                    }
                }

                ++c1At;
                ++c2At;
            }
        }

        // shift string2 to find the longest common substring
        for (int j = 1; j < size2; ++j) {
            c1At = 0;
            c2At = j;
            length = 0;
            if (size1 - c1At <= longest || size2 - c2At <= longest) {
                break;
            }
            while (c1At < size1 && c2At < size2) {
                ++comparisons;
                if (str1[c1At] != str2[c2At]) {
                    length = 0;
                } else {
                    ++length;
                    if (longest < length) {
                        longest = length;
                        start1 = c1At - longest + 1;
                        start2 = c2At - longest + 1;
                    }
                }

                ++c1At;
                ++c2At;
            }
        }

        LOG.debug("longest:{}, comparisons:{}, start1:{}, start2:{}", longest, comparisons, start1, start2);
        return longest;
    }

    public static String longestCommonSubstring(String src1, String src2) {
        int size1 = src1 != null ? src1.length() : 0;
        int size2 = src2 != null ? src2.length() : 0;
        if (size1 == 0 || size2 == 0) {
            return "";
        }

        // the start position of substring in original string
        int start1 = -1;
        // int start2 = -1;
        // the longest length of common substring
        int longest = 0;

        // record how many comparisons the solution did;
        // it can be used to know which algorithm is better
        // int comparisons = 0;

        char[] str1 = src1.toCharArray();
        char[] str2 = src2.toCharArray();
        int c1At = 0;
        int c2At = 0;
        int length = 0;

        // "官方通报泉港碳九泄露", "福建泉港碳九泄露事件"
        // shift string1 to find the longest common substring
        for (int i = 0; i < size1; ++i) {
            c1At = i;
            c2At = 0;
            length = 0;
            if (size1 - c1At <= longest || size2 - c2At <= longest) {
                break;
            }
            while (c1At < size1 && c2At < size2) {
                // ++comparisons;
                if (str1[c1At] != str2[c2At]) {
                    length = 0;
                } else {
                    ++length;
                    if (longest < length) {
                        longest = length;
                        start1 = c1At - longest + 1;
                        // start2 = c2At - longest + 1;
                    }
                }

                ++c1At;
                ++c2At;
            }
        }

        // shift string2 to find the longest common substring
        for (int j = 1; j < size2; ++j) {
            c1At = 0;
            c2At = j;
            length = 0;
            if (size1 - c1At <= longest || size2 - c2At <= longest) {
                break;
            }
            while (c1At < size1 && c2At < size2) {
                // ++comparisons;
                if (str1[c1At] != str2[c2At]) {
                    length = 0;
                } else {
                    ++length;
                    if (longest < length) {
                        longest = length;
                        start1 = c1At - longest + 1;
                        // start2 = c2At - longest + 1;
                    }
                }

                ++c1At;
                ++c2At;
            }
        }

        if (start1 < 0) {
            return null;
        }

        // LOG.debug("longest:{}, comparisons:{}, start1:{}, start2:{}", longest,
        // comparisons, start1, start2);
        return src1.substring(start1, start1 + longest);
    }

    /**
     * <pre>
     * 编辑距离：Levenshtein字符串距离
     * </pre>
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static int levenshtein(String str1, String str2) {
        int len1 = str1 != null ? str1.length() : 0;
        int len2 = str2 != null ? str2.length() : 0;
        if (StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)) {
            return len1 > len2 ? len1 : len2;
        }

        int[][] edit = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            edit[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            edit[0][j] = j;
        }

        // for (int i = 0; i <= len1; i++) {
        // for (int j = 0; j <= len2; j++) {
        // System.out.print(edit[i][j] + " ");
        // }
        // System.out.print("\n");
        // }
        // System.out.print("--------------\n");
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    edit[i][j] = edit[i - 1][j - 1];
                } else {
                    edit[i][j] = min(edit[i - 1][j - 1], edit[i][j - 1], edit[i - 1][j]) + 1;
                }

            }
        }
        // for (int i = 0; i <= len1; i++) {
        // for (int j = 0; j <= len2; j++) {
        // System.out.print(edit[i][j] + " ");
        // }
        // System.out.print("\n");
        // }
        return edit[len1][len2];
    }

    /**
     * 是否包含关系
     * 
     * @param src1
     * @param src2
     * @return
     */
    public static boolean contains(String src1, String src2) {
        if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src2)) {
            return false;
        }
        return src1.length() > src2.length() ? src1.contains(src2) : src2.contains(src1);
    }

    public static int lengthIgnoreBlank(String src) {
        if (StringUtils.isEmpty(src)) {
            return 0;
        }
        int sum = 0;
        int length = src.length();
        for (int i = 0; i < length; i++) {
            if (src.charAt(i) == CharConstants.BLANK) {
                continue;
            }
            sum++;
        }
        return sum;
    }

    private static int min(int one, int two, int three) {
        return one < two ? (one < three ? one : three) : (two < three ? two : three);
    }

//    public static void main(String[] args) {
//        // String src = longestCommonSubstring("官方通报泉港碳九泄露", "福建泉港碳九泄露事件");
//        // System.out.println(src);
//        // int a = levenshtein("abcdefghi", "fghiabcde");
//        // System.out.println(a);
//        String src1 = "官方通报泉港碳九泄露";
//        String src2 = "官方通报泉港碳九泄露";
//        boolean res = contains(src1, src2);
//        System.out.println(src1 + ":" + src1.length() + ", " + src2 + ":" + src2.length() + " = " + res);
//        long start = System.nanoTime();
//        int times = 10000000;
//        for (int i = 0; i < times; i++) {
//            contains(src1, src2 + i);
//        }
//        long cost1 = System.nanoTime() - start;
//        start = System.nanoTime();
//        for (int j = 0; j < times; j++) {
//            src1.contains(src2 + j);
//        }
//        long cost2 = System.nanoTime() - start;
//        System.out.print("cost1:" + cost1 + ", cost2:" + cost2 + ", diff:" + (cost2 - cost1));
//    }
}
