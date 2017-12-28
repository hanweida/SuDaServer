package com.suda.http.api.tencent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface TencentApi {
    String URL_SUFFIX = "?appver=1.0.2.2&appvid=1.0.2.2&network=WIFI";

    @GET("/match/stat")
    Call<String> getMatchStat(@Query("mid") String mid, @Query("tabType") String tabType);
}
