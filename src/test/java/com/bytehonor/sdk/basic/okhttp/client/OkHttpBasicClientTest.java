package com.bytehonor.sdk.basic.okhttp.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.basic.okhttp.exception.OkhttpBasicSdkException;

public class OkHttpBasicClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(OkHttpBasicClientTest.class);

    @Test
    public void testGetString2() {
        boolean isOk = true;
        try {
            // 测测header是否是浏览器的
            String html = OkHttpBasicClient.get("https://www.bytehonor.com");
            LOG.info("html:{}", html);
        } catch (OkhttpBasicSdkException e) {
            LOG.error("xxxx", e);
            isOk = false;
        }

        assertTrue("*testStartThread*", isOk);
    }

}
