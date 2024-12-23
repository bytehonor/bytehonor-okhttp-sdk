package com.bytehonor.sdk.beautify.okhttp.config;

public class OkhttpConfig {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36";

    private static final int MAX_IDLE = 10;

    private static final int CONNECT_POOL_MAX_TOTAL = 1024;

    private static final int CONNECT_POOL_MAX_PER_ROUTE = 1024;

    private String userAgent;

    private int maxIdle;

    private int connectPollMaxTotal;

    private int connectPollMaxPerRoute;

    private int connectTimeoutSeconds;

    private OkhttpConfig() {
        this.userAgent = USER_AGENT;
        this.maxIdle = MAX_IDLE;
        this.connectPollMaxTotal = CONNECT_POOL_MAX_TOTAL;
        this.connectPollMaxPerRoute = CONNECT_POOL_MAX_PER_ROUTE;
        this.connectTimeoutSeconds = 5;
    }

    private static class LazyHolder {
        private static OkhttpConfig SINGLE = new OkhttpConfig();
    }

    public static OkhttpConfig config() {
        return LazyHolder.SINGLE;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getConnectPollMaxTotal() {
        return connectPollMaxTotal;
    }

    public void setConnectPollMaxTotal(int connectPollMaxTotal) {
        this.connectPollMaxTotal = connectPollMaxTotal;
    }

    public int getConnectPollMaxPerRoute() {
        return connectPollMaxPerRoute;
    }

    public void setConnectPollMaxPerRoute(int connectPollMaxPerRoute) {
        this.connectPollMaxPerRoute = connectPollMaxPerRoute;
    }

    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public void setConnectTimeoutSeconds(int connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
    }

}
