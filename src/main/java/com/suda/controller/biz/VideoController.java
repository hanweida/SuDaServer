package com.suda.controller.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.service.LiveService;
import com.suda.utils.CharacterConvert;
import com.suda.utils.HtmlParserTool;
import com.suda.utils.HtmlPaser;
import com.suda.utils.HttpClientUtil;
import com.suda.utils.JsoupUtils;
import com.suda.utils.LogUtil;
import com.suda.utils.PropertiesUtil;
import com.suda.utils.StringUtil;
import com.suda.web.enum_const.LiveSource;
import com.suda.web.enum_const.LiveSourceConst;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private LiveService liveService;

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
                               @RequestParam(value = "url", defaultValue = "") String match_url,
                                  @RequestParam(value = "mid", defaultValue = "") String mid,
                                  @RequestParam(value = "liveSource", defaultValue = "2") String liveSource,
                                  @RequestParam(value = "sourceName", defaultValue = "") String sourceName
                                  ){
        List<MatchUrl> matchUrlList = null;
        Map map = new HashMap();
        if(StringUtil.isNotBlank(match_url) && StringUtil.isNotBlank(mid) && StringUtil.isNotBlank(liveSource)
                && StringUtil.isNotBlank(sourceName) && !"undefined".equals(match_url)){
            if((LiveSourceConst.DIDIAOKAN_Source.getIndex()+"").equals(liveSource)){
                String playSrc = liveService.getMatchLiveUrl(match_url);
                map.put("matchUrl", match_url);
                return new ModelAndView("/biz/play_didiaokan", map);
            } else if((LiveSourceConst.KUWAN_Source.getIndex()+"").equals(liveSource)){
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
                return new ModelAndView("/biz/hello", map);
            } else {
                return new ModelAndView("/biz/hello", map);
            }
        } else {
            return null;
        }
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
    public JSONArray getGameNBAList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    @RequestParam(value = "pageNum", defaultValue = "1") String pageNum
                                    ){
        List<Date> dateList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt(pageNum));
        Date date = calendar.getTime();
        if(Integer.parseInt(pageNum) <= 1){
            Date todayDate = new Date();
            dateList.add(todayDate);
        }
        dateList.add(date);
        JSONArray jsonArray = liveService.getMatchInfo(dateList);
        return jsonArray;
    }

    /**
     * 根据比赛列表，选择比赛，进入此方法，核心功能是 带比赛地址参数，找到比赛视频
     * @author:ES-BF-IT-126
     * @method:getGameNBAMatch
     * @date:Date 2018/3/2
     * @params:[httpServletRequest, httpServletResponse]
     * @returns:com.alibaba.fastjson.JSONArray
     */
    @RequestMapping(value = "/gamenbamatch", method = {RequestMethod.GET})
    @ResponseBody
    public JSONArray getGameNBAMatch(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "matchurl", defaultValue = "") String match_url
    ){
        //比赛地址,如:jrs中的 http://m.didiaokan.com//e/pptv/pptvw.php?classid=3&id=17979
        if(StringUtil.isNotBlank(match_url)){
            //根据 比赛地址，获得直播源信息如： CCTV5、QQ
            MatchInfo matchInfo = liveService.getMatchSource(match_url);
        }
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        JSONArray jsonArray = null;
        return jsonArray;
    }



}
