package com.suda.http.api;

import com.suda.http.bean.hupu.news.HuPuNewsList;
import com.suda.http.utils.JsonParserPojo;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public abstract class BaseService<T extends Object>{

    public void requestCall(Call call, Class clz, RequestCallBack<T> cbk){
        Response<String> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.code() == 200){
            T huPuNewsList = (T)JsonParserPojo.parseWithGson(clz, response.body());
            cbk.onSuccess(huPuNewsList);
        } else {
            cbk.onFailure(response.message());
        }
    }
}
