package com.bytehonor.sdk.okhttp.bytehonor.exception;

/**
 * @author lijianqiang
 *
 */
public class BytehonorOkHttpSdkException extends RuntimeException {

    private static final long serialVersionUID = 8241747723232910227L;

    public BytehonorOkHttpSdkException() {
        super();
    }

    public BytehonorOkHttpSdkException(String message) {
        super(message);
    }

    public BytehonorOkHttpSdkException(Exception cause) {
        super(cause);
    }
}
