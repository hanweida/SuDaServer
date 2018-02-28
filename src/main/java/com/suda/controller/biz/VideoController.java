package com.suda.controller.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.utils.CharacterConvert;
import com.suda.utils.HtmlParserTool;
import com.suda.utils.HtmlPaser;
import com.suda.utils.HttpClientUtil;
import com.suda.utils.JsoupUtils;
import com.suda.utils.LogUtil;
import com.suda.utils.PropertiesUtil;
import com.suda.utils.StringUtil;
import com.suda.web.enum_const.LiveSource;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ModelAndView getUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                               @RequestParam(value = "url") String match_url){
//        if(StringUtil.isNotBlank(match_url)){
//            match_url = match_url.replaceFirst("www","m");
//        }
        //System.out.println(match_url);
        //System.out.println(match_url);
        //HTML解析
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

    @RequestMapping(value = "/getnbaurl", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getNBAUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                               @RequestParam(value = "url", defaultValue = "") String match_url, @RequestParam(value = "mid", defaultValue = "") String mid){
        List<MatchUrl> matchUrlList = null;
        Map map = new HashMap();
        if(StringUtil.isNotBlank(match_url) && StringUtil.isNotBlank(mid)  && !"undefined".equals(match_url)){
            //match_url = match_url.replaceFirst("www","m");
            HtmlParserTool htmlParserTool = new HtmlParserTool();
            matchUrlList = htmlParserTool.htmlParserVideo(match_url);
            //HTML解析
            map.put("list", matchUrlList);
            for(MatchUrl matchUrl : matchUrlList){
                if(matchUrl.getActive()){
                    map.put("matchUrl", matchUrl);
                }
            }
        }
        return new ModelAndView("/biz/hello", map);
    }

    @RequestMapping(value = "/gamelist", method = {RequestMethod.GET})
    @ResponseBody
    public JSONArray getGameList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Logger logger = LogUtil.getInfoLog();
        logger.info("Test Info Log");
        String baseUrl = "http://www.kuwantiyu.com";
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String html = httpClientUtil.sendDataGet(baseUrl);
        HtmlPaser htmlPaser = new JsoupUtils();
        List<MatchInfo> matchInfoList = htmlPaser.paserHtml(html, baseUrl, LiveSource.KUWAN_Source);
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

    @RequestMapping(value = "/gamenbalist", method = {RequestMethod.GET})
    @ResponseBody
    public JSONArray getGameNBAList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        HtmlPaser htmlPaser = new JsoupUtils();
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        //酷玩直播源信息
        //String kuwan_html = httpClientUtil.sendDataGet(kuwan_url);
        //List<MatchInfo> kuwan_matchInfoList = htmlPaser.paserHtml(kuwan_html, kuwan_url, LiveSource.KUWAN_Source);
        //didiaokan直播源信息
        //String didiaokan_html = httpClientUtil.sendDataGet(didiaokan_url);
        //List<MatchInfo> didiaokan_matchInfoList = htmlPaser.paserHtml(didiaokan_html, didiaokan_url, LiveSource.DIDIAOKAN_Source);

        List<MatchInfo> kuwan_matchInfoList = null;
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
        JSONArray tomorrowmatchJsonArray = (JSONArray)(((JSONObject)matchJsonObject.get("data")).get("matches"));

        map.put("date", simpleDateFormat.format(todayDate));
        //比赛列表：http://sportsnba.qq.com/match/listByDate?date=2017-12-08&appver=1.0.2.2&appvid=1.0.2.2&network=wifi
        String todayStr = httpClientUtil.sendDataGet("http://sportsnba.qq.com/match/listByDate", map);
        JSONObject todaymatchJsonObject = JSON.parseObject(todayStr);
        JSONArray todaymatchJsonArray = (JSONArray)(((JSONObject)todaymatchJsonObject.get("data")).get("matches"));

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for(int i=0; i<todaymatchJsonArray.size(); i++){
            jsonObject = new JSONObject();
            JSONObject matchObject = (JSONObject)todaymatchJsonArray.get(i);
            String leftName =((JSONObject)matchObject.get("matchInfo")).getString("leftName");
            String rightName =((JSONObject)matchObject.get("matchInfo")).getString("rightName");
            String mid =((JSONObject)matchObject.get("matchInfo")).getString("mid");
            Date startTime =((JSONObject)matchObject.get("matchInfo")).getDate("startTime");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM-dd hh:mm");
            leftName = CharacterConvert.unicodeToString(leftName);
            rightName = CharacterConvert.unicodeToString(rightName);
            jsonObject.put("home_team", rightName);
            jsonObject.put("guest_team", leftName);
            jsonObject.put("home_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("rightGoal"));
            jsonObject.put("guest_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("leftGoal"));
            jsonObject.put("match_quarter", ((JSONObject)matchObject.get("matchInfo")).getString("quarter"));
            jsonObject.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
            jsonObject.put("home_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("rightBadge"));
            jsonObject.put("guest_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("leftBadge"));
            jsonObject.put("match_desc", ((JSONObject)matchObject.get("matchInfo")).getString("matchDesc"));
            jsonObject.put("mid", mid);
            jsonObject.put("start_time", simpleDateFormat1.format(startTime));

//            for(MatchInfo matchInfo : kuwan_matchInfoList){
//                if(matchInfo.getMatch_name().contains("NBA")){
//                    if(leftName.contains(matchInfo.getGuest_team())
//                            && rightName.contains(matchInfo.getHome_team()
//                    )){
//                        jsonObject.put("match_url", matchInfo.getMatch_url());
//                        jsonObject.put("match_time", matchInfo.getMatch_time());
//                        jsonObject.put("match_name", matchInfo.getMatch_name());
//                    }
//                }
//            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }




}
