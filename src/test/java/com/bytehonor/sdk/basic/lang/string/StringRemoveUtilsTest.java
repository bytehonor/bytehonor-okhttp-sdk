package com.bytehonor.sdk.basic.lang.string;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringRemoveUtilsTest {

	private static final Logger LOG = LoggerFactory.getLogger(StringRemoveUtilsTest.class);

	private static final String SPECIAL1 = "2018\r\n\t\bb egin°′″＄￥〒￠￡％＠℃℉﹩﹪‰﹫㎡㏕㎜㎝㎞㏎m³㎎㎏㏄º○¤%$º¹²³end";

	private static final String SPECIAL2 = "2018\r\n\t\bb egin€£Ұ₴$₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦zł﷼₠₧₯₨Kčर₹ƒ₸￠end";

	@Test
	public void testReplaceNonChineseWithBlank() {
		LOG.info("replaceNonChineseWithBlank SPECIAL1:({})", StringRemoveUtils.replaceNonChineseWithBlank(SPECIAL1));
		LOG.info("replaceNonChineseWithBlank SPECIAL2:({})", StringRemoveUtils.replaceNonChineseWithBlank(SPECIAL2));
		assertTrue("*testReplaceNonChineseWithBlank*", true);
	}

	@Test
	public void testRemoveNonChinese() {
		LOG.info("removeNonChinese SPECIAL1:({})", StringRemoveUtils.removeNonChinese(SPECIAL1));
		LOG.info("removeNonChinese SPECIAL2:({})", StringRemoveUtils.removeNonChinese(SPECIAL2));
		assertTrue("*testRemoveNonChinese*", true);
	}

	@Test
	public void testReplaceNonNormalWithBlank() {
		LOG.info("replaceNonNormalWithBlank SPECIAL1:({})", StringRemoveUtils.replaceNonNormalWithBlank(SPECIAL1));
		LOG.info("replaceNonNormalWithBlank SPECIAL2:({})", StringRemoveUtils.replaceNonNormalWithBlank(SPECIAL2));
		assertTrue("*testReplaceNonChineseWithBlank*", true);
	}

	@Test
	public void testRemoveNonNormal() {
		LOG.info("removeNonNormal SPECIAL1:({})", StringRemoveUtils.removeNonNormal(SPECIAL1));
		LOG.info("removeNonNormal SPECIAL2:({})", StringRemoveUtils.removeNonNormal(SPECIAL2));
		assertTrue("*testRemoveNonChinese*", true);
	}

}
