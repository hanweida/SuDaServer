package com.suda.http.api.hupu.news;

import com.suda.http.api.BaseService;
import com.suda.http.api.RequestCallBack;
import com.suda.http.bean.hupu.news.HuPuNewsList;
import com.suda.http.bean.match.MatchStat;
import com.suda.http.constant.Constants;
import com.suda.http.okhttp.OkHttpHelper;
import com.suda.http.utils.JsonParserPojo;
import com.suda.http.utils.RequestHelper;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Map;


/**
 * Created by ES-BF-IT-126 on 2018/1/5.
 */
public class HuPuNewsService extends BaseService{
    static Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.HUPUNEWS)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpHelper.getHuPuClient())
            .build();

    public static HuPuNewsApi huPuNewsApi = retrofit.create(HuPuNewsApi.class);
    public void getNewsList( final RequestCallBack<HuPuNewsList> cbk) throws IOException {
            Map<String, String> params = RequestHelper.getRequestMap();
            String sign = RequestHelper.getRequestSign(params);
            Call<String> call = huPuNewsApi.getHuPuNewsList(sign, params);
            super.requestCall(call, HuPuNewsList.class, cbk);
    }
}
