package com.test.http;

import com.google.gson.Gson;
import com.suda.http.api.RequestCallBack;
import com.suda.http.api.hupu.news.HuPuNewsService;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.hupu.news.HuPuNewsList;
import com.suda.http.bean.match.MatchStat;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/12/28.
 */
public class TestHttp {
    TencentService tencentService = new TencentService();
    @Test
    public void TencentService() throws InterruptedException {

        tencentService.getMatchStat("100000:1470662", "2", new RequestCallBack<MatchStat>() {
            public void onSuccess(MatchStat matchStat) {
                System.out.println(matchStat.toString());
            }
            public void onFailure(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    public void TencentParse() throws InterruptedException {
        tencentService.getMatchStat("100000:1470662", "2", new RequestCallBack<MatchStat>() {
            public void onSuccess(MatchStat matchStat) {

                Gson gson = new Gson();
                System.out.println(gson.toJson(matchStat));
            }
            public void onFailure(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    public void test(){
        Map map = new HashMap<Integer,Integer>();
        map.put(1,"dfdf");
        System.out.println(map.get(1));
    }

    @Test
    public void testHupuSin(){
        HuPuNewsService huPuNewsService = new HuPuNewsService();
        try {
            huPuNewsService.getNewsList(new RequestCallBack<HuPuNewsList>() {
                public void onSuccess(HuPuNewsList huPuNewsList) {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(huPuNewsList));
                }

                public void onFailure(String message) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
