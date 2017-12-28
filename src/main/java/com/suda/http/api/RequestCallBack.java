package com.suda.http.api;

/** '
 * 回调接口
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface RequestCallBack<T> {
    void onSuccess(T t);

    void onFailure(String message);
}
