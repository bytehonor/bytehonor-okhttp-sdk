package com.bytehonor.sdk.basic.lang.string;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRemove {

	private static final Logger LOG = LoggerFactory.getLogger(TestRemove.class);

	private static final String SPECIAL1 = "2018\r\n\t\bb egin°′″＄￥〒￠￡％＠℃℉﹩﹪‰﹫㎡㏕㎜㎝㎞㏎m³㎎㎏㏄º○¤%$º¹²³end";

	private static final String SPECIAL2 = "2018\r\n\t\b保持完整句子b egin€£Ұ₴$₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦zł﷼₠₧₯₨Kčर₹ƒ₸￠end";

	@Test
	public void testTime() {
		LOG.info("replaceNonChineseWithBlank SPECIAL1:({})", StringRemoveUtils.replaceNonChineseWithBlank(SPECIAL1));
		LOG.info("replaceNonChineseWithBlank SPECIAL2:({})", StringRemoveUtils.replaceNonChineseWithBlank(SPECIAL2));
		LOG.info("removeNonChinese SPECIAL1:({})", StringRemoveUtils.removeNonChinese(SPECIAL1));
		LOG.info("removeNonChinese SPECIAL2:({})", StringRemoveUtils.removeNonChinese(SPECIAL2));
		LOG.info("replaceNonNormalWithBlank SPECIAL1:({})", StringRemoveUtils.replaceNonNormalWithBlank(SPECIAL1));
		LOG.info("replaceNonNormalWithBlank SPECIAL2:({})", StringRemoveUtils.replaceNonNormalWithBlank(SPECIAL2));
		LOG.info("removeNonNormal SPECIAL1:({})", StringRemoveUtils.removeNonNormal(SPECIAL1));
		LOG.info("removeNonNormal SPECIAL2:({})", StringRemoveUtils.removeNonNormal(SPECIAL2));
		LOG.info("PS SPECIAL1:{}", StringRemoveUtils.cleanPS(SPECIAL1));
		LOG.info("PS SPECIAL2:{}", StringRemoveUtils.cleanPS(SPECIAL2));
		LOG.info("PSZ SPECIAL1:{}", StringRemoveUtils.cleanPSZ(SPECIAL1));
		LOG.info("PSZ SPECIAL2:{}", StringRemoveUtils.cleanPSZ(SPECIAL2));

		int total = 10000;
		long start1 = System.nanoTime();
		for (int i = 0; i < total; i++) {
			StringRemoveUtils.replaceNonNormalWithBlank(SPECIAL2);
		}
		long cost1 = System.nanoTime() - start1;

		long start2 = System.nanoTime();
		for (int i = 0; i < total; i++) {
		    StringRemoveUtils.cleanPSZ(SPECIAL2);
		}
		long cost2 = System.nanoTime() - start2;

		LOG.info("cost1:{}, cost2:{}, diff:{}", cost1, cost2, (cost1 - cost2));
		assertTrue("*testTime*", true);
	}

}
