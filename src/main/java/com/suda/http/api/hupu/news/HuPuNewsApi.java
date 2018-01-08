package com.suda.http.api.hupu.news;

import com.suda.http.bean.hupu.news.HuPuNewsList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2018/1/5.
 */
public interface HuPuNewsApi {
    @GET("nba/getNews")
    Call<String> getHuPuNewsList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("nba/getNewsDetailSchema")
    Call<String> getNewsDetailSchema(@Query("nid") String nid, @QueryMap Map<String, String> params);

}
