package com.suda.http.okhttp;

import com.suda.http.constant.Constants;
import com.suda.utils.StringUtil;
import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Cookie拦截器。用于保存和设置Cookies
 */
public class CookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        for (String header : chain.proceed(original).headers("Set-Cookie")) {
            if (header.startsWith("u=")) {
                String cookie = header.split(";")[0].substring(2);
                //LogUtils.d("okhttplog: add cookie:" + cookie);
                    if (!StringUtil.isBlank(cookie)) {
                        Constants.Cookie = cookie;
                    }
            }
        }
        return chain.proceed(original);
    }
}
