package com.suda.http.api;

import com.suda.http.utils.JsonParserPojo;
import com.suda.utils.LogUtil;
import org.slf4j.Logger;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public abstract class BaseService<T extends Object>{

    public void requestCall(Call call, Class<T> clz, RequestCallBack<T> cbk){
        Logger Log = LogUtil.getErrorLog();
        Response<String> response = null;
        try {
            response = call.execute();
            if(response.code() == 200){
                T list = (T)JsonParserPojo.parseWithGson(clz, response.body());
                cbk.onSuccess(list);
            } else {
                cbk.onFailure(response.message());
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }
}
