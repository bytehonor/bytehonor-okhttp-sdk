package com.bytehonor.sdk.basic.okhttp.string;

import org.springframework.util.StringUtils;

import com.bytehonor.sdk.basic.okhttp.regex.PatternUtils;

public class SentenceAlgothrim {

    /**
     * <pre>
     * 语义相似
     * 如“刘国梁当选新一届中国乒协主席” 与 “刘国梁当选新一届乒协主席”
     * 序列分割后，短句子串全在长句中
     * 
     * 两词长度差不能大于短词长度
     * </pre>
     * 
     * @param src1
     * @param src2
     * @param maxCount 最大相同子串个数
     * @return
     */
    public static boolean isSemanticSimalar(String src1, String src2, int maxCount) {
        if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src2)) {
            return false;
        }
        // 统一去掉标点符号
        src1 = StringRemoveUtils.removeNonNormal(src1);
        src2 = StringRemoveUtils.removeNonNormal(src2);
        if (src1.length() < 1 || src2.length() < 1) {
            return false;
        }
        if (src1.length() == src2.length()) {
            return src1.equalsIgnoreCase(src2);
        }
        String longSrc = src1.toLowerCase(); // 长句
        String shortSrc = src2.toLowerCase(); // 短句
        if (src1.length() < src2.length()) {
            longSrc = shortSrc;
            shortSrc = src1.toLowerCase();
        }

        int longLength = longSrc.length();
        int shortLength = shortSrc.length();
        
        // 首字符不相等的时候，要求两词长度差不能大于短词长度本身，否则要全包含
        if (longSrc.charAt(0) != shortSrc.charAt(0) && (longLength - shortLength) > shortLength) {
            return longSrc.contains(shortSrc);
        }
        if (PatternUtils.isLetter(shortSrc)) {
            return false;
        }
        int maxDistance = shortLength - 1;
        int shortAt = 0;
        int longAt = 0;
        int distance = 0; // 最大不相似字串长度，且不能超过短词长度
        int count = 0; // 相同字串个数
        while (longAt < longLength) {
            // 首字符不相等的才校验长度差
            if (distance > maxDistance) {
                // LOG.info("distance:{}, maxDistance:{}", distance, maxDistance);
                return false;
            }
            if (longSrc.charAt(longAt) == shortSrc.charAt(shortAt)) {
                // LOG.info("longAt:{}, shortAt:{}", longAt, shortAt);
                distance = 0;
                count++;
                if (count > maxCount) {
                    // LOG.info("count:{}, maxCount:{}", count, maxCount);
                    return false;
                }

                while (longAt < longLength && shortAt < shortLength
                        && longSrc.charAt(longAt) == shortSrc.charAt(shortAt)) {
                    longAt++;
                    shortAt++;
                }
                if (shortAt == shortLength) {
                    return true;
                }
            } else {
                distance++;
            }

            longAt++;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}