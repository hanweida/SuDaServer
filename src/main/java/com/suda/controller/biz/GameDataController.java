package com.suda.controller.biz;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSONObject;
import com.suda.http.api.RequestCallBack;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.match.MatchStat;
import com.suda.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by ES-BF-IT-126 on 2018/1/2.
 */

@Controller
@RequestMapping(value = "/gamedata")
public class GameDataController {
    @Autowired
    private LiveService liveService;
    @RequestMapping(value = "/matchStat", method = {RequestMethod.GET})
    @ResponseBody
    public JSONObject matchStat(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                          @RequestParam(value = "mid", required = true) String mid,
                          @RequestParam(value = "tabType", defaultValue = "2") String tabType,
                          @RequestParam(value = "homeTeamName", defaultValue = "") String homeTeamName,
                          @RequestParam(value = "guestTeamName", defaultValue = "") String guestTeamName
                          ){
        //TODO 此处由于没有NBA比赛，所以用 json串代替测试
        String homeStr = null;
        String guestStr = null;
        try {
            homeStr = new String(homeTeamName.getBytes("iso-8859-1"), "utf-8");
            guestStr = new String(guestTeamName.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("3434");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //JSONObject jsonObject = liveService.getMatchState(mid, tabType, "独行侠", "灰熊");
        System.out.println(homeStr);
        System.out.println(guestStr);
        JSONObject jsonObject = liveService.getMatchState(mid, tabType, homeStr, guestStr);
        //JSONObject jsonObject = JSON.parseObject(str);
        return jsonObject;
    }
}
