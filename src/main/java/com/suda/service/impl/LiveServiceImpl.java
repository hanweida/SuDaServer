package com.suda.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suda.pojo.MatchInfo;
import com.suda.service.LiveService;
import com.suda.utils.CharacterConvert;
import com.suda.utils.HtmlPaser;
import com.suda.utils.HttpClientUtil;
import com.suda.utils.JsoupUtils;
import com.suda.utils.PropertiesUtil;
import com.suda.utils.StringUtil;
import com.suda.web.enum_const.LiveSource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.suda.web.enum_const.LiveSource.KUWAN_Source;

/**
 * Created by ES-BF-IT-126 on 2018/2/7.
 */
@Service
public class LiveServiceImpl extends BaseService implements LiveService {

    final String kuwan_url = PropertiesUtil.getProperties("kuwan_url");
    final String didiaokan_url = PropertiesUtil.getProperties("didiaokan_url");
    final String leqiuba_url = PropertiesUtil.getProperties("leqiuba_url");
    final HttpClientUtil httpClientUtil = new HttpClientUtil();

    @Override
    public JSONArray getMatchInfo(List<Date> date) {
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        //酷玩直播源信息
        List<MatchInfo> kuwan_matchInfoList = getMatchInfoList(KUWAN_Source);
        //didiaokan直播源信息
        List<MatchInfo> didiaokan_matchInfoList = getMatchInfoList(LiveSource.DIDIAOKAN_Source);

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

            for(MatchInfo matchInfo : kuwan_matchInfoList){
                if(matchInfo.getMatch_name().contains("NBA")){
                    if(leftName.contains(matchInfo.getGuest_team())
                            && rightName.contains(matchInfo.getHome_team()
                    )){
                        jsonObject.put("match_url", matchInfo.getMatch_url());
                        jsonObject.put("match_time", matchInfo.getMatch_time());
                        jsonObject.put("match_name", matchInfo.getMatch_name());
                    }
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private List<MatchInfo> getMatchInfoList(LiveSource liveSource){
        HtmlPaser htmlPaser = new JsoupUtils();
        String url = "";
        switch (liveSource){
            case KUWAN_Source:url = kuwan_url; break;
            case DIDIAOKAN_Source:url = didiaokan_url; break;
            case LEQIUBA_Source:url = leqiuba_url; break;
            default:break;
        }
        if(StringUtil.isNotBlank(url)){
            String html = httpClientUtil.sendDataGet(url);
            if(StringUtil.isNotBlank(html)){
                List<MatchInfo> matchInfoList = htmlPaser.paserHtml(html, url, liveSource);
                return matchInfoList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
