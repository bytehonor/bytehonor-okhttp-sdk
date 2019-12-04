package com.bytehonor.sdk.basic.okhttp.client;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.basic.okhttp.exception.WeixinMpSdkException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp4Client {

    private static Logger LOG = LoggerFactory.getLogger(OkHttp4Client.class);

    private static boolean DEBUG = false;

    private OkHttpClient mOkHttpClient;

    private OkHttp4Client() {
        this.init();
    }

    private void init() {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).build();
    }

    private static class LazzyHolder {
        private static OkHttp4Client INSTANCE = new OkHttp4Client();
    }

    public static OkHttp4Client getInstance() {
        return LazzyHolder.INSTANCE;
    }

    private static String execute(Request request) throws WeixinMpSdkException {
        Call call = getInstance().mOkHttpClient.newCall(request);
        String resultString = null;
        try {
            Response response = call.execute();
            if (DEBUG && LOG.isInfoEnabled()) {
                LOG.info("[{}] {} - {}", response.code(), request.method(), request.url());
            }
            // ResponseUtils.valid(response);
            resultString = response.body().string();
        } catch (IOException e) {
            LOG.error("{}, error:{}", request.url(), e.getMessage());
            throw new WeixinMpSdkException(e.getMessage());
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
     * @throws WeixinMpSdkException
     */
    public static String get(String url) throws WeixinMpSdkException {
        return get(url, null, null);
    }

    /**
     * 传递参数的get请求。
     * 
     * @param url
     * @param paramsMap
     * @return
     * @throws WeixinMpSdkException
     */
    public static String get(String url, Map<String, String> paramsMap) throws WeixinMpSdkException {
        return get(url, paramsMap, null);
    }

    /**
     * 传递参数，且传递请求头的get请求。
     * 
     * @param url
     * @param paramsMap
     * @param headerMap
     * @return
     * @throws WeixinMpSdkException
     */
    public static String get(String url, Map<String, String> paramsMap, Map<String, String> headerMap)
            throws WeixinMpSdkException {
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
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
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
     * @throws WeixinMpSdkException
     */
    public static String postForm(String url, Map<String, String> paramsMap) throws WeixinMpSdkException {
        return postForm(url, paramsMap, null);
    }

    /**
     * 传递参数，且传递请求头的postForm请求。
     * 
     * @param url
     * @param paramsMap
     * @param headerMap
     * @return
     * @throws WeixinMpSdkException
     */
    public static String postForm(String url, Map<String, String> paramsMap, Map<String, String> headerMap)
            throws WeixinMpSdkException {
        Objects.requireNonNull(url, "url");
        FormBody.Builder formBody = new FormBody.Builder();
        if (paramsMap != null && paramsMap.isEmpty() == false) {
            for (String key : paramsMap.keySet()) {
                formBody.add(key, paramsMap.get(key));
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headerMap != null && headerMap.isEmpty() == false) {
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
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
        // https://www.jianshu.com/p/c1655f5c0fc0
        // https://blog.csdn.net/qq_19306415/article/details/102954712
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);// FormBody.create(json,
                                                                                // MediaType.parse("application/json"));//RequestBody.create(mediaType,
                                                                                // json);

        Request request = new Request.Builder().post(requestBody).url(url).build();

        return execute(request);
    }

    public static String upload(String url, Map<String, String> paramsMap, File file) throws WeixinMpSdkException {
        Objects.requireNonNull(url, "url");
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody fileBody = FormBody.create(file, MediaType.parse("multipart/form-data"));
            multipartBuilder.addFormDataPart("media", file.getName(), fileBody);
            multipartBuilder.addFormDataPart("filename", file.getName());
            multipartBuilder.addFormDataPart("filelength", String.valueOf(file.length()));
        }

        if (paramsMap != null && paramsMap.isEmpty() == false) {
            for (String key : paramsMap.keySet()) {
                multipartBuilder.addFormDataPart(key, paramsMap.get(key));
            }
        }
        MultipartBody multipartBody = multipartBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder.url(url).post(multipartBody).build();

        return execute(request);
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void setDebug(boolean debug) {
        OkHttp4Client.DEBUG = debug;
    }

}
