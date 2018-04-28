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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
    Logger logUtil = LogUtil.getInfoLog();

    @Autowired
    private LiveService liveService;

    @RequestMapping(value = "/getnbaurl", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getNBAUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                               @RequestParam(value = "url", defaultValue = "") String match_url,
                                  @RequestParam(value = "mid", defaultValue = "") String mid,
                                  @RequestParam(value = "liveSource", defaultValue = "2") String liveSourceValue,
                                  @RequestParam(value = "sourceName", defaultValue = "") String sourceName
                                  ){
        List<MatchUrl> matchUrlList = null;
        Map map = new HashMap(16);
        if(StringUtil.isNotBlank(match_url) && StringUtil.isNotBlank(mid) && StringUtil.isNotBlank(liveSourceValue)
                && StringUtil.isNotBlank(sourceName) && !"undefined".equals(match_url)){
            if((LiveSourceConst.DIDIAOKAN_Source.getIndex()+"").equals(liveSourceValue) && !sourceName.contains("CCTV")){
                String playSrc = liveService.getMatchLiveUrl(match_url);
                playSrc = playSrc.substring(playSrc.indexOf("?id=")+"?id=".length());
                try {
                    playSrc = URLDecoder.decode(playSrc, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put("matchUrl", playSrc);
                logUtil.info("[getNBAUrl-didiaokan-QQ-playSrc] "+ playSrc);
                return new ModelAndView("/biz/play_didiaokan", map);
            } else if((LiveSourceConst.KUWAN_Source.getIndex()+"").equals(liveSourceValue)){
                if(match_url.contains("__")){
                    String[] liveValue = match_url.split("__");
                    if(liveValue.length > 1){
                        map.put("vid", liveValue[0]);
                        map.put("player", liveValue[1]);
                        logUtil.info("[getNBAUrl-KUWAN-vid-player] "+ liveValue[0]+" "+liveValue[1]);
                    } else {
                        logUtil.info("[getNBAUrl-KUWAN-vid-player] 缺少 vid-player");
                    }
                }
                return new ModelAndView("/biz/play_kuwan", map);
            } else {
                logUtil.info("[getNBAUrl-didiaokan-CCTV5]");
                return new ModelAndView("/biz/cctv5");
            }
        } else {
            logUtil.info("[getNBAUrl] 参数不全");
            return new ModelAndView("/biz/cctv5");
        }
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
            MatchInfo matchInfo = liveService.getMatchSource(match_url, LiveSource.DIDIAOKAN_Source);
        }
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        JSONArray jsonArray = null;
        return jsonArray;
    }



}
