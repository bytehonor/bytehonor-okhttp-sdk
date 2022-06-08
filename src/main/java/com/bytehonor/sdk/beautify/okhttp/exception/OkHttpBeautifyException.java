package com.bytehonor.sdk.beautify.okhttp.exception;

/**
 * @author lijianqiang
 *
 */
public class OkHttpBeautifyException extends RuntimeException {

    private static final long serialVersionUID = 8241747723232910227L;

    public OkHttpBeautifyException() {
        super();
    }

    public OkHttpBeautifyException(String message) {
        super(message);
    }

    public OkHttpBeautifyException(Exception cause) {
        super(cause);
    }
}
