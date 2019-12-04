package com.bytehonor.sdk.basic.okhttp.exception;

/**
 * @author <a href="https://github.com/lijianqiang">lijianqiang</a>
 *
 */
public class WeixinMpSdkException extends RuntimeException {

    private static final long serialVersionUID = 8241747723232910227L;

    public WeixinMpSdkException() {
        super();
    }

    public WeixinMpSdkException(String message) {
        super(message);
    }

    public WeixinMpSdkException(Exception cause) {
        super(cause);
    }
}
