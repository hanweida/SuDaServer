package com.suda.http.api.tencent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface TencentApi {
    String URL_SUFFIX = "?appver=1.0.2.2&appvid=1.0.2.2&network=WIFI";

    /**根据mid,和tabType类型查看比赛数据数据*/
    @GET("/match/stat")
    Call<String> getMatchStat(@Query("mid") String mid, @Query("tabType") String tabType);

    /**根据日期查看比赛数据列表*/
    @GET("/match/listByDate")
    Call<String> listByDate(@Query("date") String date);

    /**根据日期查看比赛数据列表*/
    @GET("/match/baseInfo")
    Call<String> getMatchBaseInfo(@Query("mid") String mid);
}
