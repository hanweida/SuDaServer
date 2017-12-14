package com.suda.http.api.tencent;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public interface TencentVideoApi {

    @GET("/getinfo?otype=xml&platform=1&ran=0%2E9652906153351068")
    Call<String> getVideoRealUrl(@Query("vid") String vid);

    /**
     * 最新方法
     * http://h5vv.video.qq.com/getinfo?callback=tvp_request_getinfo_callback_340380&platform=11001&charge=0&otype=json&ehost=http%3A%2F%2Fv.qq.com&sphls=0&sb=1&nocache=0&_rnd=1474896074003&vids=m0022ect1qs&defaultfmt=auto&&_qv_rmt=CTWS8OLbA17867igt=&_qv_rmt2=x6oMojAw144904luQ=&sdtfrom=v3010&callback=tvp_request_getinfo_callback_
     *
     * @param vids
     * @return
     */
    @GET("getinfo?platform=11001&charge=0&otype=json")
    Call<String> getVideoRealUrls(@Query("vids") String vids);
}
