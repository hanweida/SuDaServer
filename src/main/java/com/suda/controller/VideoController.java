package com.suda.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.utils.CharacterConvert;
import com.suda.utils.HtmlParserTool;
import com.suda.utils.HttpClientUtil;
import com.suda.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @RequestMapping(value = "/gameNBAlist", method = {RequestMethod.GET})
    @ResponseBody
    public JSONArray getGameNBAList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HtmlParserTool htmlParserTool = new HtmlParserTool();
        List<MatchInfo> matchInfoList = htmlParserTool.htmlParser("http://www.leqiuba.cc");

        HttpClientUtil httpClientUtil = new HttpClientUtil();
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date todayDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();

        //System.out.println(simpleDateFormat.format(date));;
        map.put("date", simpleDateFormat.format(tomorrowDate));
        map.put("appver", "1.0.2.2");
        map.put("appvid", "1.0.2.2");
        map.put("network", "wifi");
        String str = httpClientUtil.sendDataGet("http://sportsnba.qq.com/match/listByDate", map);
        JSONObject matchJsonObject = JSON.parseObject(str);
        JSONArray matchJsonArray = (JSONArray)(((JSONObject)matchJsonObject.get("data")).get("matches"));

        map.put("date", simpleDateFormat.format(todayDate));
        String todayStr = httpClientUtil.sendDataGet("http://sportsnba.qq.com/match/listByDate", map);
        JSONObject todaymatchJsonObject = JSON.parseObject(todayStr);
        JSONArray todaymatchJsonArray = (JSONArray)(((JSONObject)todaymatchJsonObject.get("data")).get("matches"));

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for(MatchInfo matchInfo : matchInfoList){
            if(matchInfo.getMatch_name().contains("NBA")){
                jsonObject = new JSONObject();
                if(matchJsonArray.size() > 0){
                    for(int i=0; i<matchJsonArray.size(); i++){
                        JSONObject matchObject = (JSONObject)matchJsonArray.get(i);
                        String leftName =((JSONObject)matchObject.get("matchInfo")).getString("leftName");
                        String rightName =((JSONObject)matchObject.get("matchInfo")).getString("rightName");
                        String mid =((JSONObject)matchObject.get("matchInfo")).getString("mid");
                        leftName = CharacterConvert.unicodeToString(leftName);
                        rightName = CharacterConvert.unicodeToString(rightName);
                        if(leftName.contains(matchInfo.getGuest_team())
                                && rightName.contains(matchInfo.getHome_team()
                                )){
                            jsonObject.put("home_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("rightGoal"));
                            jsonObject.put("guest_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("leftGoal"));
                            jsonObject.put("match_quarter", ((JSONObject)matchObject.get("matchInfo")).getString("quarter"));
                            jsonObject.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
                            jsonObject.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
                            jsonObject.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
                            jsonObject.put("home_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("rightBadge"));
                            jsonObject.put("guest_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("leftBadge"));
                            jsonObject.put("match_desc", ((JSONObject)matchObject.get("matchInfo")).getString("matchDesc"));
                            jsonObject.put("mid", mid);
                        }
                    }
                }

                if(todaymatchJsonArray.size() > 0){
                    for(int i=0; i<todaymatchJsonArray.size(); i++){
                        JSONObject matchObject = (JSONObject)todaymatchJsonArray.get(i);
                        String leftName =((JSONObject)matchObject.get("matchInfo")).getString("leftName");
                        String rightName =((JSONObject)matchObject.get("matchInfo")).getString("rightName");
                        String mid =((JSONObject)matchObject.get("matchInfo")).getString("mid");
                        leftName = CharacterConvert.unicodeToString(leftName);
                        rightName = CharacterConvert.unicodeToString(rightName);
                        if(leftName.contains(matchInfo.getGuest_team())
                                && rightName.contains(matchInfo.getHome_team()
                        )){
                            jsonObject.put("home_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("rightGoal"));
                            jsonObject.put("guest_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("leftGoal"));
                            jsonObject.put("match_quarter", ((JSONObject)matchObject.get("matchInfo")).getString("quarter"));
                            jsonObject.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
                            jsonObject.put("home_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("rightBadge"));
                            jsonObject.put("guest_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("leftBadge"));
                            jsonObject.put("match_desc", ((JSONObject)matchObject.get("matchInfo")).getString("matchDesc"));
                            jsonObject.put("mid", mid);
                        }
                    }
                }
                jsonObject.put("match_time", matchInfo.getMatch_time());
                jsonObject.put("match_name", matchInfo.getMatch_name());
                jsonObject.put("home_team", matchInfo.getHome_team());
                jsonObject.put("guest_team", matchInfo.getGuest_team());
                //jsonObject.put("home_logo_url", matchInfo.getHome_logo_url());
                //jsonObject.put("guest_logo_url", matchInfo.getGuest_logo_url());
                jsonObject.put("match_url", matchInfo.getMatch_url());
                System.out.println(jsonObject.toString());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
