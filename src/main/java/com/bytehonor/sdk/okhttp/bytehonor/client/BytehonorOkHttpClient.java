package com.bytehonor.sdk.okhttp.bytehonor.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.okhttp.bytehonor.exception.BytehonorOkHttpSdkException;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author lijianqiang
 *
 */
public class BytehonorOkHttpClient {

    private static Logger LOG = LoggerFactory.getLogger(BytehonorOkHttpClient.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36";

    private static final int MAX_IDLE = 10;

    private static final int CONNECT_POOL_MAX_TOTAL = 1024;

    private static final int CONNECT_POOL_MAX_PER_ROUTE = 1024;

    private OkHttpClient mOkHttpClient;

    private BytehonorOkHttpClient() {
        this.init();
    }

    private void init() {
        mOkHttpClient = build();
    }

    public static OkHttpClient build() {
        ConnectionPool pool = new ConnectionPool(MAX_IDLE, 3L, TimeUnit.MINUTES);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(CONNECT_POOL_MAX_TOTAL);
        dispatcher.setMaxRequestsPerHost(CONNECT_POOL_MAX_PER_ROUTE);
        return new OkHttpClient.Builder().dispatcher(dispatcher).connectionPool(pool)
                .connectTimeout(6L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS)
                .writeTimeout(5L, TimeUnit.SECONDS).build();
    }

    private static class LazzyHolder {
        private static BytehonorOkHttpClient INSTANCE = new BytehonorOkHttpClient();
    }

    public static BytehonorOkHttpClient getInstance() {
        return LazzyHolder.INSTANCE;
    }

    private static String execute(Request request) throws BytehonorOkHttpSdkException {
        String resultString = null;
        Response response = null;
        try {
            response = getInstance().mOkHttpClient.newCall(request).execute();
            if (LOG.isDebugEnabled()) {
                LOG.debug("[{}] {} - {}", response.code(), request.method(), request.url());
            }
            // ResponseUtils.valid(response);
            ResponseBody body = response.body();
            resultString = body.string();
            body.close();
        } catch (IOException e) {
            LOG.error("{}, error:{}", request.url(), e.getMessage());
            throw new BytehonorOkHttpSdkException(e);
        } finally {
            if (response != null) {
                response.close(); // 20211024
            }
        }
        return resultString;
    }

    /**
     * 不传递参数的get请求。
     * 
     * @param url
     * @return
     * @throws BytehonorOkHttpSdkException
     */
    public static String get(String url) throws BytehonorOkHttpSdkException {
        return get(url, null, null);
    }

    /**
     * 传递参数的get请求。
     * 
     * @param url
     * @param params
     * @return
     * @throws BytehonorOkHttpSdkException
     */
    public static String get(String url, Map<String, String> params) throws BytehonorOkHttpSdkException {
        return get(url, params, null);
    }

    /**
     * 传递参数，且传递请求头的get请求。
     * 
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws BytehonorOkHttpSdkException
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers)
            throws BytehonorOkHttpSdkException {
        Objects.requireNonNull(url, "url");
        if (params != null && params.isEmpty() == false) {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Entry<String, String> item : params.entrySet()) {
                sb.append(item.getKey()).append("=").append(item.getValue());
                sb.append("&");
            }
            int length = sb.length();
            url = sb.substring(0, length - 1);
        }

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
            }
        }

        Request request = builder.url(url).get().build();
        return execute(request);
    }

    /**
     * 传递参数的postForm请求。
     * 
     * @param url
     * @param params
     * @return
     * @throws BytehonorOkHttpSdkException
     */
    public static String postForm(String url, Map<String, String> params) throws BytehonorOkHttpSdkException {
        return postForm(url, params, null);
    }

    /**
     * 传递参数，且传递请求头的postForm请求。
     * 
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws BytehonorOkHttpSdkException
     */
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers)
            throws BytehonorOkHttpSdkException {
        Objects.requireNonNull(url, "url");
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null && params.isEmpty() == false) {
            for (Entry<String, String> item : params.entrySet()) {
                formBody.add(item.getKey(), item.getValue());
            }
        }

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
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

        return postJson(url, json, null);
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url, String json, Map<String, String> headers) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(json, "json");
        // https://www.jianshu.com/p/c1655f5c0fc0
        // https://blog.csdn.net/qq_19306415/article/details/102954712
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
            }
        }

        Request request = builder.post(requestBody).url(url).build();

        return execute(request);
    }

    public static String postXml(String url, String xml) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(xml, "xml");

        return postXml(url, xml);
    }

    public static String postXml(String url, String xml, Map<String, String> headers) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(xml, "xml");

        MediaType mediaType = MediaType.parse("application/xml; charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(xml, mediaType);

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
            }
        }

        Request request = builder.post(requestBody).url(url).build();

        return execute(request);
    }

    public static String postPlain(String url, String text) {
        return postPlain(url, text, null);
    }

    /**
     * @param url
     * @param text
     * @return
     */
    public static String postPlain(String url, String text, Map<String, String> headers) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(text, "text");
        // https://www.jianshu.com/p/c1655f5c0fc0
        // https://blog.csdn.net/qq_19306415/article/details/102954712
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(text, mediaType);

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
            }
        }

        Request request = builder.post(requestBody).url(url).build();

        return execute(request);
    }

    public static String uploadMedia(String url, Map<String, String> params, File file)
            throws BytehonorOkHttpSdkException {
        return upload(url, params, file, "media");
    }

    public static String uploadPic(String url, Map<String, String> params, File file)
            throws BytehonorOkHttpSdkException {
        return upload(url, params, file, "pic");
    }

    public static String uploadFile(String url, Map<String, String> params, File file)
            throws BytehonorOkHttpSdkException {
        return upload(url, params, file, "file");
    }

    public static String upload(String url, Map<String, String> params, File file, String fileKey)
            throws BytehonorOkHttpSdkException {
        Objects.requireNonNull(url, "url");
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody fileBody = FormBody.create(file, MultipartBody.FORM);
            multipartBuilder.addFormDataPart(fileKey, file.getName(), fileBody);
            multipartBuilder.addFormDataPart("filename", file.getName());
            multipartBuilder.addFormDataPart("filelength", String.valueOf(file.length()));
        }

        if (params != null && params.isEmpty() == false) {
            for (Entry<String, String> item : params.entrySet()) {
                multipartBuilder.addFormDataPart(item.getKey(), item.getValue());
            }
        }
        RequestBody multipartBody = multipartBuilder.build();
        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        Request request = builder.url(url).post(multipartBody).build();

        return execute(request);
    }

    public static void download(String url, String filePath) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(filePath, "filePath");
        download(url, filePath, null);
    }

    public static void download(String url, String filePath, Map<String, String> headers) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(filePath, "filePath");

        Request.Builder builder = new Request.Builder();
        builder.header("User-Agent", USER_AGENT);
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> item : headers.entrySet()) {
                builder.header(item.getKey(), item.getValue());
            }
        }

        Request request = builder.url(url).get().build();

        // 异步请求
        InputStream is = null;
        byte[] buf = new byte[4096];
        int len = 0;
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);

            Response response = getInstance().mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                is = response.body().byteStream();
                fos = new FileOutputStream(file);
                int size = 0;
                long total = response.body().contentLength();
                while ((size = is.read(buf)) != -1) {
                    len += size;
                    fos.write(buf, 0, size);
                    if (LOG.isDebugEnabled()) {
                        int process = (int) Math.floor(((double) len / total) * 100);
                        LOG.debug("process:{}", process);
                    }
                }
                fos.flush();
            } else {
                throw new BytehonorOkHttpSdkException("Unexpected response " + response.toString());
            }
        } catch (IOException e) {
            LOG.error("download url:{}", url, e);
            throw new BytehonorOkHttpSdkException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.error("is.close error", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOG.error("fos.close error", e);
                }
            }
        }
    }
}
