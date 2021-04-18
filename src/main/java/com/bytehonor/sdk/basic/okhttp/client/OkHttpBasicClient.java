package com.bytehonor.sdk.basic.okhttp.client;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.basic.okhttp.exception.OkhttpBasicSdkException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpBasicClient {

    private static Logger LOG = LoggerFactory.getLogger(OkHttpBasicClient.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3968.0 Safari/537.36";

    private OkHttpClient mOkHttpClient;

    private OkHttpBasicClient() {
        this.init();
    }

    private void init() {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).build();
    }

    private static class LazzyHolder {
        private static OkHttpBasicClient INSTANCE = new OkHttpBasicClient();
    }

    public static OkHttpBasicClient getInstance() {
        return LazzyHolder.INSTANCE;
    }

    private static String execute(Request request) throws OkhttpBasicSdkException {
        String resultString = null;
        try {
            Response response = getInstance().mOkHttpClient.newCall(request).execute();
            if (LOG.isDebugEnabled()) {
                LOG.debug("[{}] {} - {}", response.code(), request.method(), request.url());
            }
            // ResponseUtils.valid(response);
            resultString = response.body().string();
        } catch (IOException e) {
            LOG.error("{}, error:{}", request.url(), e.getMessage());
            throw new OkhttpBasicSdkException(e.getMessage());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("response:{}", resultString);
        }
        return resultString;
    }

    /**
     * 不传递参数的get请求。
     * 
     * @param url
     * @return
     * @throws OkhttpBasicSdkException
     */
    public static String get(String url) throws OkhttpBasicSdkException {
        return get(url, null, null);
    }

    /**
     * 传递参数的get请求。
     * 
     * @param url
     * @param paramsMap
     * @return
     * @throws OkhttpBasicSdkException
     */
    public static String get(String url, Map<String, String> paramsMap) throws OkhttpBasicSdkException {
        return get(url, paramsMap, null);
    }

    /**
     * 传递参数，且传递请求头的get请求。
     * 
     * @param url
     * @param paramsMap
     * @param headerMap
     * @return
     * @throws OkhttpBasicSdkException
     */
    public static String get(String url, Map<String, String> paramsMap, Map<String, String> headerMap)
            throws OkhttpBasicSdkException {
        Objects.requireNonNull(url, "url");
        if (paramsMap != null && paramsMap.isEmpty() == false) {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Entry<String, String> item : paramsMap.entrySet()) {
                sb.append(item.getKey()).append("=").append(item.getValue());
                sb.append("&");
            }
            int length = sb.length();
            url = sb.substring(0, length - 1);
        }

        Request.Builder builder = new Request.Builder();
        if (headerMap != null && headerMap.isEmpty() == false) {
            for (Entry<String, String> item : headerMap.entrySet()) {
                builder.addHeader(item.getKey(), item.getValue());
            }
        } else {
            builder.header("User-Agent", USER_AGENT);
        }


        Request request = builder.url(url).get().build();
        return execute(request);
    }

    /**
     * 传递参数的postForm请求。
     * 
     * @param url
     * @param paramsMap
     * @return
     * @throws OkhttpBasicSdkException
     */
    public static String postForm(String url, Map<String, String> paramsMap) throws OkhttpBasicSdkException {
        return postForm(url, paramsMap, null);
    }

    /**
     * 传递参数，且传递请求头的postForm请求。
     * 
     * @param url
     * @param paramsMap
     * @param headerMap
     * @return
     * @throws OkhttpBasicSdkException
     */
    public static String postForm(String url, Map<String, String> paramsMap, Map<String, String> headerMap)
            throws OkhttpBasicSdkException {
        Objects.requireNonNull(url, "url");
        FormBody.Builder formBody = new FormBody.Builder();
        if (paramsMap != null && paramsMap.isEmpty() == false) {
            for (Entry<String, String> item : paramsMap.entrySet()) {
                formBody.add(item.getKey(), item.getValue());
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headerMap != null && headerMap.isEmpty() == false) {
            for (Entry<String, String> item : headerMap.entrySet()) {
                builder.addHeader(item.getKey(), item.getValue());
            }
        }

        Request request = builder.url(url).post(formBody.build()).build();
        return execute(request);
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url, String json) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(json, "json");
        // https://www.jianshu.com/p/c1655f5c0fc0
        // https://blog.csdn.net/qq_19306415/article/details/102954712
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);

        Request request = new Request.Builder().post(requestBody).url(url).build();

        return execute(request);
    }

    public static String postXml(String url, String xml) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(xml, "xml");

        MediaType mediaType = MediaType.parse("application/xml; charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(xml, mediaType);

        Request request = new Request.Builder().post(requestBody).url(url).build();

        return execute(request);
    }

    public static String uploadMedia(String url, Map<String, String> paramsMap, File file)
            throws OkhttpBasicSdkException {
        return upload(url, paramsMap, file, "media");
    }

    public static String uploadPic(String url, Map<String, String> paramsMap, File file)
            throws OkhttpBasicSdkException {
        return upload(url, paramsMap, file, "pic");
    }
    
    public static String uploadFile(String url, Map<String, String> paramsMap, File file)
            throws OkhttpBasicSdkException {
        return upload(url, paramsMap, file, "file");
    }

    public static String upload(String url, Map<String, String> paramsMap, File file, String fileKey)
            throws OkhttpBasicSdkException {
        Objects.requireNonNull(url, "url");
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody fileBody = FormBody.create(file, MultipartBody.FORM);
            multipartBuilder.addFormDataPart(fileKey, file.getName(), fileBody);
            multipartBuilder.addFormDataPart("filename", file.getName());
            multipartBuilder.addFormDataPart("filelength", String.valueOf(file.length()));
        }

        if (paramsMap != null && paramsMap.isEmpty() == false) {
            for (Entry<String, String> item : paramsMap.entrySet()) {
                multipartBuilder.addFormDataPart(item.getKey(), item.getValue());
            }
        }
        RequestBody multipartBody = multipartBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder.url(url).post(multipartBody).build();

        return execute(request);
    }
}
