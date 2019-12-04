package com.bytehonor.sdk.basic.okhttp.string;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.basic.okhttp.string.StringCreator;

public class StringCreatorTest {

    private static final Logger LOG = LoggerFactory.getLogger(StringCreatorTest.class);

    @Test
    public void test() {
        String src = StringCreator.create().append(null).append("xxx").toString();
        LOG.info("src:{}", src);
        boolean isOk = "xxx".equals(src);

        String xx = null;
        String src2 = new StringBuilder().append(xx).append("xxx").toString();
        LOG.info("src2:{}", src2);

        assertTrue("test", isOk);
    }

}
