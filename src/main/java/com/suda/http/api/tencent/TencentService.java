package com.suda.http.api.tencent;

import com.suda.http.api.RequestCallBack;
import com.suda.http.bean.match.MatchStat;
import com.suda.http.okhttp.OkHttpHelper;
import com.suda.utils.StringUtil;
import com.suda.web.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public class TencentService {
    static Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.TENTCENTAPI)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpHelper.getTecentClient())
            .build();
    static TencentApi tencentApi = retrofit.create(TencentApi.class);

    /**
     *
     * @author:ES-BF-IT-126
     * @method:getMatchStat
     * @date:Date 2017/12/14
     * @params:[mid, tableType, cbk]
     * @returns:void
     */
    public static void getMatchStat(String mid, String tableType, final RequestCallBack<MatchStat> cbk){
        Call<String> call = tencentApi.getMatchStat(mid, tableType);
        call.enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if(response != null && !StringUtil.isBlank(response.body())){
                    String jsonStr = response.body();
                    //MatchStat matchStat = JsonParser.parseWithGson(MatchStat.class, jsonStr);
                }
            }

            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
}
