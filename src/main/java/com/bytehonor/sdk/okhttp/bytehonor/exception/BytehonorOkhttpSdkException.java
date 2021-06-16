package com.bytehonor.sdk.okhttp.bytehonor.exception;

/**
 * @author <a href="https://github.com/lijianqiang">lijianqiang</a>
 *
 */
public class BytehonorOkhttpSdkException extends RuntimeException {

    private static final long serialVersionUID = 8241747723232910227L;

    public BytehonorOkhttpSdkException() {
        super();
    }

    public BytehonorOkhttpSdkException(String message) {
        super(message);
    }

    public BytehonorOkhttpSdkException(Exception cause) {
        super(cause);
    }
}
