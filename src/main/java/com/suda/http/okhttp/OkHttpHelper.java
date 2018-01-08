package com.suda.http.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author yuyh.
 * @date 16/6/26.
 */
public class OkHttpHelper {

    /**
     * 自定义日志输出
     */
    static class MyLog implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {

        }
    }
    /**
     * OKHTTP拦截器
     * @author:ES-BF-IT-126 
     * @method: 
     * @date:Date 2018/1/5
     * @params:
     * @returns:
     */
    public static OkHttpClient getHuPuClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLog());
        //CookieInterceptor cookieInterceptor = new CookieInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                //.addInterceptor(cookieInterceptor)
                .addInterceptor(logging);
        return builder.build();
    }

    public static OkHttpClient getTecentClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLog());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(logging)
                .addInterceptor(new CommonParamsInterceptor());
        return builder.build();
    }
}
