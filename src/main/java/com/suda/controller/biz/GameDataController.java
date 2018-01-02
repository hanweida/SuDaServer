package com.suda.controller.biz;

import com.google.gson.Gson;
import com.suda.http.api.RequestCallBack;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.match.MatchStat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ES-BF-IT-126 on 2018/1/2.
 */

@Controller
@RequestMapping(value = "/gamedata")
public class GameDataController {
    @RequestMapping(value = "/matchStat", method = {RequestMethod.GET})
    public void matchStat(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                          @RequestParam(value = "mid", required = true) String mid, @RequestParam(value = "tabType", defaultValue = "2") String tabType
                          ){
        TencentService tencentService = new TencentService();
        tencentService.getMatchStat(mid, tabType, new RequestCallBack<MatchStat>() {
            public void onSuccess(MatchStat matchStat) {
                Gson gson = new Gson();
                System.out.println(gson.toJson(matchStat));
            }

            public void onFailure(String message) {

            }
        });

    }
}
