package com.bytehonor.sdk.basic.lang.string;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.bytehonor.sdk.basic.lang.constant.CharConstants;
import com.bytehonor.sdk.basic.lang.regex.PatternUtils;

public class StringRemoveUtils {
    
    /**
     * 大写 P 表示 Unicode 字符集七个字符属性之一：标点字符。
     * 
     * 其他六个是
     * 
     * L：字母； M：标记符号（一般不会单独出现）； Z：分隔符（比如空格、换行等）； S：符号（比如数学符号、货币符号等）；
     * N：数字（比如阿拉伯数字、罗马数字等）； C：其他字符
     */

    private static final String PS = "\\pP|\\pS";
    private static final Pattern PS_PATTERN = Pattern.compile(PS);

    private static final String PSZ = "\\pP|\\pS|\\pZ";
    private static final Pattern PSZ_PATTERN = Pattern.compile(PSZ);
    
    /**
     * 清除标点符号
     * 
     * @param src
     * @return clear
     */
    public static String cleanPS(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        // return src.replaceAll(PSZ, "");
        return PS_PATTERN.matcher(src).replaceAll("");
    }

    /**
     * 清除标点符号
     * 
     * @param src
     * @return clear
     */
    public static String cleanPSZ(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        // return src.replaceAll(PSZ, "");
        return PSZ_PATTERN.matcher(src).replaceAll("");
    }

    /**
     * <pre>
     * Java 过滤非汉字的utf8的字符（包括emoji）
     * 4字节以上的过滤掉
     * </pre>
     * 
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String removeUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("utf-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }

            b += 256; // 去掉符号位

            if (((b >> 5) ^ 0x06) == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            } else if (((b >> 4) ^ 0x0E) == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            } else if (((b >> 3) ^ 0x1E) == 0) {
                i += 4;
            } else if (((b >> 2) ^ 0xBE) == 0) {
                i += 5;
            } else {
                i += 6;
            }
        }
        buffer.flip();
        return new String(buffer.array(), 0, buffer.limit(), "utf-8"); 
    }
    
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String src = "🧠工作联系VX:z111x333 赠送一条：157cm 80斤《》：“{}+——）*&……%￥#@ф";
//        System.out.println(src);
//        System.out.println(removeUtf8Mb4(src));
//    }
    
    /**
     * 替换非中文字符为空格
     * 
     * @param src
     * @return
     */
    public static String replaceNonChineseWithBlank(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        int length = src.length();
        char[] target = new char[length];
        int at = 0;
        boolean hasInsertBlank = false;
        for (int i = 0; i < length; i++) {
            if (PatternUtils.isChineseChar(src.charAt(i))) {
                target[at++] = src.charAt(i);
                hasInsertBlank = false;
            } else {
                if (at == 0 || hasInsertBlank) {
                    continue;
                }
                hasInsertBlank = true;
                target[at++] = CharConstants.BLANK;
            }
        }
        if (at > 0 && CharConstants.BLANK == target[at - 1]) {
            at--;
        }
        if (at > 0) {
            return new String(target, 0, at);
        }
        return "";
    }

    /**
     * 除去非中文字符
     * 
     * @param src
     * @return
     */
    public static String removeNonChinese(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        int length = src.length();
        char[] target = new char[length];
        int total = 0;
        for (int i = 0; i < length; i++) {
            if (PatternUtils.isChineseChar(src.charAt(i))) {
                target[total++] = src.charAt(i);
            }
        }
        if (total > 0) {
            return new String(target, 0, total);
        }
        return "";
    }

    /**
     * 替换非Normal字符为空格
     * 
     * @param src
     * @return
     */
    public static String replaceNonNormalWithBlank(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        int length = src.length();
        char[] target = new char[length];
        int at = 0;
        boolean hasInsertBlank = false;
        for (int i = 0; i < length; i++) {
            if (PatternUtils.isNormalChar(src.charAt(i))) {
                target[at++] = src.charAt(i);
                hasInsertBlank = false;
            } else {
                if (at == 0 || hasInsertBlank) {
                    continue;
                }
                hasInsertBlank = true;
                target[at++] = CharConstants.BLANK;
            }
        }
        if (at > 0 && CharConstants.BLANK == target[at - 1]) {
            at--;
        }
        if (at > 0) {
            return new String(target, 0, at);
        }
        return "";
    }

    /**
     * 除去非Normal字符
     * 
     * @param src
     * @return
     */
    public static String removeNonNormal(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        int length = src.length();
        char[] target = new char[length];
        int total = 0;
        for (int i = 0; i < length; i++) {
            if (PatternUtils.isNormalChar(src.charAt(i))) {
                target[total++] = src.charAt(i);
            }
        }
        if (total > 0) {
            return new String(target, 0, total);
        }
        return "";
    }

    /**
     * 替换{regex}字符为空格
     * 
     * @param src
     * @return
     */
    public static String replaceRegexWithBlank(String src, String regex) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        if (StringUtils.isEmpty(regex)) {
            return src;
        }
        int regLen = regex.length();
        char[] regChars = regex.toCharArray();
        char[] source = src.toCharArray();
        int length = src.length();
        char[] result = new char[length];
        int at = 0;
        boolean hasInsertBlank = false;
        for (int i = 0; i < length; i++) {
            if (regChars[0] == src.charAt(i) && isEquals(source, i, length, regChars, regLen)) {
                if (hasInsertBlank) {
                    continue;
                }
                hasInsertBlank = true;
                result[at++] = CharConstants.BLANK;
                i += (regLen - 1);
            } else {
                result[at++] = src.charAt(i);
                hasInsertBlank = false;
            }
        }
        if (at > 0 && CharConstants.BLANK == result[at - 1]) {
            at--;
        }
        if (at > 0) {
            return new String(result, 0, at);
        }
        return "";
    }

    /**
     * 移除{regex}字符
     * 
     * @param src
     * @return
     */
    public static String removeRegex(String src, String regex) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        if (StringUtils.isEmpty(regex)) {
            return src;
        }
        int regLen = regex.length();
        char[] regChars = regex.toCharArray();
        char[] source = src.toCharArray();
        int length = src.length();
        char[] result = new char[length];
        int at = 0;
        for (int i = 0; i < length; i++) {
            if (regChars[0] == src.charAt(i) && isEquals(source, i, length, regChars, regLen)) {
                i += (regLen - 1);
            } else {
                result[at++] = src.charAt(i);
            }
        }
        if (at > 0) {
            return new String(result, 0, at);
        }
        return "";
    }

    public static boolean isEquals(char[] source, int from, int end, char[] target, int length) {
        if (end - from < length) {
            return false;
        }
        int i = 0;
        int at = from;
        for (i = 0; i < length && at < end; i++) {
            at = i + from;
            // System.out.println(source[at] + ", " + target[i]);
            if (target[i] != source[at]) {
                return false;
            }
        }
        // System.out.println(i + ", " + at);
        return (i == length);
    }
}
