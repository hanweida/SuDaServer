package com.suda.http.api;

import com.google.gson.Gson;

/** '
 * 回调接口
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface RequestCallBack<T> {
    String onSuccess(T t);

    void onFailure(String message);
}
