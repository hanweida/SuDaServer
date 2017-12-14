package com.suda.http.api;

/**
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface RequestCallBack<T> {
    void onSuccess(T t);

    void onFailure(String message);
}
