package com.suda.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.utils.HtmlParserTool;
import com.suda.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/11/10.
 */
@Controller
@RequestMapping(value="/video")
public class VideoController {
    @RequestMapping(value = "/geturl", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String match_url = httpServletRequest.getParameter("url");
        if(StringUtil.isNotBlank(match_url)){
            match_url = match_url.replaceFirst("www","m");
        }

        System.out.println(match_url);
        HtmlParserTool htmlParserTool = new HtmlParserTool();
        List<MatchUrl> matchUrlList = htmlParserTool.htmlParserVideo(match_url);
        Map map = new HashMap();
        map.put("list", matchUrlList);
        for(MatchUrl matchUrl : matchUrlList){
            if(matchUrl.getActive()){
                map.put("matchUrl", matchUrl);
            }
        }
        return new ModelAndView("/biz/hello", map);
    }

    @RequestMapping(value = "/gamelist", method = {RequestMethod.GET})
    @ResponseBody
    public JSONArray getGameList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HtmlParserTool htmlParserTool = new HtmlParserTool();
        List<MatchInfo> matchInfoList = htmlParserTool.htmlParser("http://www.leqiuba.cc");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for(MatchInfo matchInfo : matchInfoList){
            jsonObject = new JSONObject();
            jsonObject.put("match_time", matchInfo.getMatch_time());
            jsonObject.put("match_name", matchInfo.getMatch_name());
            jsonObject.put("home_team", matchInfo.getHome_team());
            jsonObject.put("guest_team", matchInfo.getGuest_team());
            jsonObject.put("home_logo_url", matchInfo.getHome_logo_url());
            jsonObject.put("guest_logo_url", matchInfo.getGuest_logo_url());
            jsonObject.put("match_url", matchInfo.getMatch_url());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
