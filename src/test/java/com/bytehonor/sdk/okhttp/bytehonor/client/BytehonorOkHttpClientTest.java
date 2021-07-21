package com.bytehonor.sdk.okhttp.bytehonor.client;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.okhttp.bytehonor.exception.BytehonorOkhttpSdkException;

public class BytehonorOkHttpClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(BytehonorOkHttpClientTest.class);

    @Test
    public void testGetString2() {
        boolean isOk = true;
        try {
            // 测测header是否是浏览器的
            String html = BytehonorOkHttpClient.get("https://www.bytehonor.com");
            LOG.info("html:{}", html);
        } catch (BytehonorOkhttpSdkException e) {
            LOG.error("xxxx", e);
            isOk = false;
        }

        assertTrue("*testStartThread*", isOk);
    }

    public static void main(String[] arg) {
        ExecutorService service = Executors.newFixedThreadPool(64);
        int size = 256;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicInteger ai = new AtomicInteger(0);
        for (int i = 0; i < size; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 测测header是否是浏览器的
                        BytehonorOkHttpClient.get("https://www.baidu.com");
                        ai.incrementAndGet();
                        countDownLatch.countDown();
                    } catch (BytehonorOkhttpSdkException e) {
                        LOG.error("xxxx error:{}", e.getMessage());
                    }
                }
            });
        }
        try {
            countDownLatch.await();
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            LOG.info("sleep", e);
        }
        int success = ai.intValue();
        LOG.info("success:{}", success);
//        assertTrue("*testThread*", size == success);
    }

//    @Test
    public void testUpload() {
        String url = "xx";

        boolean isOk = true;
        File file = null;
        try {
            file = new File("/xxx/1.jpg");
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("status", "xx");
            paramsMap.put("access_token", "xx");

            String res = BytehonorOkHttpClient.uploadPic(url, paramsMap, file);
            LOG.info("res:{}", res);
        } catch (Exception e) {
            isOk = false;
            LOG.error("testUpload", e);
        }
        assertTrue("testUpload", isOk);
    }
}
