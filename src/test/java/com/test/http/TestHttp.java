package com.test.http;

import com.suda.http.api.RequestCallBack;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.match.MatchStat;
import org.junit.Test;

/**
 * Created by ES-BF-IT-126 on 2017/12/28.
 */
public class TestHttp {
    @Test
    public void TencentService() throws InterruptedException {
        TencentService.getMatchStat("100000:1470416", "2", new RequestCallBack<MatchStat>() {
            public void onSuccess(MatchStat matchStat) {
                System.out.println(matchStat.toString());
            }
            public void onFailure(String message) {
                System.out.println(message);
            }
        });
    }
}
