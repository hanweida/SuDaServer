package com.test.http;

import com.google.gson.Gson;
import com.suda.http.api.RequestCallBack;
import com.suda.http.api.hupu.news.HuPuNewsService;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.hupu.news.HuPuNewsDetail;
import com.suda.http.bean.hupu.news.HuPuNewsList;
import com.suda.http.bean.hupu.news.HuPuNewsReply;
import com.suda.http.bean.match.MatchList;
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
            public String onSuccess(MatchStat matchStat) {
                System.out.println(matchStat.toString());
                return null;
            }
            public void onFailure(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    public void TencentParse() throws InterruptedException {
        tencentService.getMatchStat("100000:1470662", "2", new RequestCallBack<MatchStat>() {
            public String onSuccess(MatchStat matchStat) {

                Gson gson = new Gson();
                System.out.println(gson.toJson(matchStat));
                return null;
            }
            public void onFailure(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    public void TencentMatchList() throws InterruptedException {
        tencentService.listByDate("2018-01-09", new RequestCallBack<MatchList>() {
            public String onSuccess(MatchList matchList) {
                Gson gson = new Gson();
                System.out.println(gson.toJson(matchList));
                return null;

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
                public String onSuccess(HuPuNewsList huPuNewsList) {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(huPuNewsList));
                    return null;

                }

                public void onFailure(String message) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHupuNewsSchma(){
        HuPuNewsService huPuNewsService = new HuPuNewsService();
        try {
            huPuNewsService.getNewsDetailSchema("2231928", new RequestCallBack<HuPuNewsDetail>() {
                public String onSuccess(HuPuNewsDetail huPuNewsDetail) {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(huPuNewsDetail));
                    return null;

                }
                public void onFailure(String message) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHupuNewsRpli(){
        HuPuNewsService huPuNewsService = new HuPuNewsService();
        try {
            huPuNewsService.getNewsDetailRepli("2231928", new RequestCallBack<HuPuNewsReply>() {
                public String onSuccess(HuPuNewsReply huPuNewsReply) {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(huPuNewsReply));
                    return null;

                }

                public void onFailure(String message) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
