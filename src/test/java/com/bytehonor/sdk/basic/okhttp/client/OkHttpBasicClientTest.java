package com.bytehonor.sdk.basic.okhttp.client;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.basic.okhttp.exception.OkhttpBasicSdkException;

public class OkHttpBasicClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(OkHttpBasicClientTest.class);

//    @Test
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

    @Test
    public void testUpload() {
        String url = "https://api.weibo.com/2/statuses/share.json";

        boolean isOk = true;
        File file = null;
        try {
            file = new File("/Users/lijianqiang/Downloads/1.jpg");
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("status", "图片微博 https://www.bytehonor.com");
            paramsMap.put("access_token", "2.00_HdkYHnLI2TE2c1f14a473FclYfC");

            String res = OkHttpBasicClient.uploadPic(url, paramsMap, file);
            LOG.info("res:{}", res);
        } catch (Exception e) {
            isOk = false;
            LOG.error("testUpload", e);
        }
        assertTrue("testUpload", isOk);
    }
}
