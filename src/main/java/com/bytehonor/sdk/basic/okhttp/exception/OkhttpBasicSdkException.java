package com.bytehonor.sdk.basic.okhttp.exception;

/**
 * @author <a href="https://github.com/lijianqiang">lijianqiang</a>
 *
 */
public class OkhttpBasicSdkException extends RuntimeException {

    private static final long serialVersionUID = 8241747723232910227L;

    public OkhttpBasicSdkException() {
        super();
    }

    public OkhttpBasicSdkException(String message) {
        super(message);
    }

    public OkhttpBasicSdkException(Exception cause) {
        super(cause);
    }
}
