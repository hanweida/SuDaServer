package com.suda.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.suda.http.api.RequestCallBack;
import com.suda.http.api.tencent.TencentService;
import com.suda.http.bean.match.MatchStat;
import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.service.LiveService;
import com.suda.utils.CharacterConvert;
import com.suda.utils.HtmlParserTool;
import com.suda.utils.HttpClientUtil;
import com.suda.utils.JsoupUtils;
import com.suda.utils.StringUtil;
import com.suda.web.enum_const.LiveSource;
import com.suda.web.enum_const.LiveSourceConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.suda.web.enum_const.LiveSource.DIDIAOKAN_Source;
import static com.suda.web.enum_const.LiveSource.KUWAN_Source;

/**
 * Created by ES-BF-IT-126 on 2018/2/7.
 */
@Service
public class LiveServiceImpl extends BaseService implements LiveService {
    @Value("${kuwan_url}")
    private String kuwan_url;
    @Value("${didiaokan_url}")
    private String didiaokan_url;
    //低调看移动设备二级链接
    @Value("${didiaokan_murl}")
    private String didiaokan_murl;
    @Value("${leqiuba_url}")
    private String leqiuba_url;

    final HttpClientUtil httpClientUtil = new HttpClientUtil();

    @Override
    public JSONArray getMatchInfo(List<Date> dateList) {
        long totalStartTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat startTimeFormat = new SimpleDateFormat("MM-dd hh:mm");
        JSONArray jsonArray = null;
        JSONArray totalArray = new JSONArray();
        JSONObject josnData = null;

        //Map最好设置默认值，防止成倍扩大，占内存
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("appver", "1.0.2.2");
        map.put("appvid", "1.0.2.2");
        map.put("network", "wifi");

        for(Date date : dateList){
            JSONObject totalData = new JSONObject();
            String dateStr = simpleDateFormat.format(date);
            map.put("date", dateStr);
            //比赛列表：http://sportsnba.qq.com/match/listByDate?date=2017-12-08&appver=1.0.2.2&appvid=1.0.2.2&network=wifi
            String listNBAStr = httpClientUtil.sendDataGet("http://sportsnba.qq.com/match/listByDate", map);
            JSONObject matchJsonObject = JSON.parseObject(listNBAStr);
            JSONArray matchJsonArray = (JSONArray)(((JSONObject)matchJsonObject.get("data")).get("matches"));
            int compareDate = date.compareTo(new Date());
            if(compareDate < 0){
                totalData.put("key", dateStr+"  今天比赛");
            } else if(compareDate == 1){
                totalData.put("key", dateStr+"  明天比赛");
            }
            jsonArray = new JSONArray();
            for(int i=0; i < matchJsonArray.size(); i++){
                josnData = new JSONObject();
                JSONObject matchObject = (JSONObject)matchJsonArray.get(i);
                String leftName =((JSONObject)matchObject.get("matchInfo")).getString("leftName");
                String rightName =((JSONObject)matchObject.get("matchInfo")).getString("rightName");
                String mid =((JSONObject)matchObject.get("matchInfo")).getString("mid");
                leftName = CharacterConvert.unicodeToString(leftName);
                rightName = CharacterConvert.unicodeToString(rightName);
                Date startTime =((JSONObject)matchObject.get("matchInfo")).getDate("startTime");
                josnData.put("home_team", rightName);
                josnData.put("guest_team", leftName);
                josnData.put("home_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("rightGoal"));
                josnData.put("guest_team_score", ((JSONObject)matchObject.get("matchInfo")).getString("leftGoal"));
                josnData.put("match_quarter", ((JSONObject)matchObject.get("matchInfo")).getString("quarter"));
                josnData.put("match_quarterTime", ((JSONObject)matchObject.get("matchInfo")).getString("quarterTime"));
                josnData.put("home_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("rightBadge"));
                josnData.put("guest_logo_url", ((JSONObject)matchObject.get("matchInfo")).getString("leftBadge"));
                josnData.put("match_desc", ((JSONObject)matchObject.get("matchInfo")).getString("matchDesc"));
                josnData.put("mid", mid);
                josnData.put("start_time", startTimeFormat.format(startTime));
                jsonArray.add(josnData);
            }
            totalData.put("data", jsonArray);
            totalArray.add(totalData);
        }
        System.out.println("总请求时间："+ (System.currentTimeMillis() - totalStartTime)+"ms");
        return totalArray;
    }

    /**
     * 根据比赛地址，获得比赛源 如CCTV5、QQ
     * @author:ES-BF-IT-126
     * @method:getMatchSource
     * @date:Date 2018/3/2
     * @params:[url]
     * @returns:com.alibaba.fastjson.JSONArray
     */
    @Override
    public MatchInfo getMatchSource(String matchurl, LiveSource liveSource) {
        //请求比赛地址，放到 用户选择比赛后，再解析，节省请求次数与响应时间
        long matchStartTime = System.currentTimeMillis();
        String str = httpClientUtil.sendDataGet(matchurl);
        MatchInfo matchInfo = null;
        if(StringUtil.isNotBlank(str)){
            if (liveSource.equals(LiveSource.DIDIAOKAN_Source)){
                matchInfo = getMatchSourcePlayerByDidiaokan(str, matchurl);
            } else if(liveSource.equals(LiveSource.KUWAN_Source)){
                matchInfo = getMatchSourcePlayerByKuwan(str, matchurl);
            }
        }
        System.out.println(liveSource.name()+"请求比赛时间 getMatchSource："+ (System.currentTimeMillis() - matchStartTime)+"ms");
        return matchInfo;
    }

    /**
     * 获得比赛直播源与直播页面地址（didiaokan）
     * @author:ES-BF-IT-126
     * @method:getMatchSourcePlayerByDidiaokan
     * @date:Date 2018/3/15
     * @params:[htmlStr, matchurl]
     * @returns:com.suda.pojo.MatchInfo
     */
    private MatchInfo getMatchSourcePlayerByDidiaokan(String htmlStr, String matchurl){
        MatchInfo matchInfo = null;
        //存在nba列表重定向
        if(htmlStr.indexOf("w.html") > 0){
            matchInfo = new MatchInfo();
            JsoupUtils jsoupUtils = new JsoupUtils();
            String redirectStr = matchurl.substring(0, matchurl.indexOf('?'));
            String redirectUrl = redirectStr + "/w.html";
            String sourceChoose = httpClientUtil.sendDataGet(redirectUrl);
            //获得播放源与播放页面url如：cctv5-url、QQ-url
            Map<String , String> sourceUrlMap = new HashMap<String, String>();
            sourceUrlMap = jsoupUtils.parseSourceChoose(sourceChoose, didiaokan_url);
            matchInfo.setSourcePlayer(sourceUrlMap);

            //解析QQ视频的 iframe 的src 地址
//                for(Map.Entry<String,String> entry : sourceUrlMap.entrySet()){
//                    if(entry.getKey().contains("QQ")){
//                        //TODO 此处没有根据播放源（cctv/qq）请求直播src，每次请求节省3秒时间，可以放到单独根据请求直播src页面中
//                        String valueHtml = httpClientUtil.sendDataGet(entry.getValue());
//                        String playUrl = jsoupUtils.didiaoParseQQ(valueHtml);
//                        sourceUrlMap.put(entry.getKey(), playUrl);
//                        matchInfo.setSourcePlayer(sourceUrlMap);
//                    }
//                }
            //设置比赛直播源标识
            matchInfo.setMatchLiveSource(LiveSourceConst.DIDIAOKAN_Source.getIndex());
        }
        return matchInfo;
    }

    /**
     * 获得比赛直播源与直播页面地址（kuwan）
     * @author:ES-BF-IT-126
     * @method:getMatchSourcePlayerByDidiaokan
     * @date:Date 2018/3/15
     * @params:[htmlStr, matchurl]
     * @returns:com.suda.pojo.MatchInfo
     */
    private MatchInfo getMatchSourcePlayerByKuwan(String htmlStr, String matchurl){
        MatchInfo matchInfo = null;
        //存在nba列表重定向
        if(StringUtil.isNotBlank(htmlStr)){
            matchInfo = new MatchInfo();
            HtmlParserTool htmlParserTool = new HtmlParserTool();
            List<MatchUrl> matchUrlList = htmlParserTool.htmlParserVideo(matchurl);
            Map<String , String> sourceUrlMap = new LinkedHashMap<String , String>();
            for(MatchUrl matchUrl : matchUrlList){
                //value：vid__player
                if(StringUtil.isNotBlank(matchUrl.getName())){
                    if(!"篮球直播".equals(matchUrl.getName())){
                        sourceUrlMap.put(matchUrl.getName(), matchUrl.getVid()+"__"+matchUrl.getPlayer());
                    }
                }
            }
            matchInfo.setSourcePlayer(sourceUrlMap);
            matchInfo.setMatchLiveSource(LiveSourceConst.KUWAN_Source.getIndex());
        }
        return matchInfo;
    }

    /**
     * 获得比赛播放地址、比赛数据
     * @author:ES-BF-IT-126
     * @method:getMatchSource
     * @date:Date 2018/3/2
     * @params:[tabTMidpel] 腾讯Mid,比赛id
     * @params:[tabTypel] 	1:本场最佳、球队统计 	2：球员统计 	3：球队数据王、历史对阵
     *
     * @returns:com.alibaba.fastjson.JSONArray
     */
    @Override
    public JSONObject getMatchState(final String mid, String tabType, final String homeTeamName,final  String guestTeamName) {
        long totalStartTime = System.currentTimeMillis();
        TencentService tencentService = new TencentService();
        String gson = tencentService.getMatchStat(mid, tabType, new RequestCallBack<MatchStat>() {
                    @Override
                    public String onSuccess(MatchStat matchStat) {
                        //酷玩直播源信息
                        List<MatchInfo> kuwanMatchInfoList = getMatchInfoList(LiveSource.KUWAN_Source, homeTeamName, guestTeamName);
                        //低调看直播源信息
                        List<MatchInfo> didiaokanMatchInfoList = getMatchInfoList(DIDIAOKAN_Source, homeTeamName, guestTeamName);
                        Gson gson = new Gson();
                        matchStat.data.mid=mid;
                        //根据比赛列表信息获得比赛url
                        matchStat.data.matchSourceList = new ArrayList<MatchStat.MatchSource>();
                        getMatchSourceLive(didiaokanMatchInfoList, matchStat, homeTeamName, guestTeamName, mid, LiveSource.DIDIAOKAN_Source);
                        getMatchSourceLive(kuwanMatchInfoList, matchStat, homeTeamName, guestTeamName, mid, LiveSource.KUWAN_Source);
                        System.out.println(gson.toJson(matchStat));
                        return gson.toJson(matchStat) ;
                    }
                    @Override
                    public void onFailure(String message) {

                    }
        });
        System.out.println("总请求时间："+ (System.currentTimeMillis() - totalStartTime)+"ms");
                return JSON.parseObject(gson);
    }

    @Override
    public String getMatchLiveUrl(String sourceUrl) {
        String valueHtml = httpClientUtil.sendDataGet(sourceUrl);
        JsoupUtils jsoupUtils = new JsoupUtils();
        String playUrl = jsoupUtils.didiaoParseQQ(valueHtml);
//                        sourceUrlMap.put(entry.getKey(), playUrl);
//                        matchInfo.setSourcePlayer(sourceUrlMap);
        return playUrl;
    }

    /**
     * 根据比赛列表信息，用其中 比赛的url获得比赛的直播src
     * "data": {
 "          matchSourceList": [{
     "          liveSource": 2,
     "          sourceName": "QQ高清",
     "          sourceValue": "https://d5a5b3dd1ccb90d30360f0c068fd43fc.oss-cn-shanghai.aliyuncs.com/w.html"},
     "       {
                liveSource": 2,
     "          sourceName": "QQ高清1",
     "          sourceValue": "https://d5a5b3dd1ccb90d30360f0c068fd43fc.oss-cn-shanghai.aliyuncs.com/w.html"
            }],
     * @author:ES-BF-IT-126
     * @method:getMatchSourceLive
     * @date:Date 2018/3/9
     * @params:[list, matchStat, homeTeamName, guestTeamName]
     * @returns:void
     */
    private void getMatchSourceLive(List<MatchInfo> list, MatchStat matchStat, String homeTeamName, String guestTeamName, String mid, LiveSource liveSource){
        long jsStartTime = System.currentTimeMillis();
        if(null != list){
            for(MatchInfo matchInfo : list){
                if(null != matchInfo && null != matchInfo.getGuest_team() && null != matchInfo.getHome_team() && guestTeamName.contains(matchInfo.getGuest_team())
                        && homeTeamName.contains(matchInfo.getHome_team()
                )){
                    //根据比赛地址。请求获得比赛源与比赛直播地址
                    MatchInfo matchInfo1 = getMatchSource( matchInfo.getMatch_url(), liveSource);
                    for(Map.Entry<String, String> entry : matchInfo1.getSourcePlayer().entrySet()){
                        MatchStat.MatchSource matchSource = new MatchStat.MatchSource();
                        matchSource.sourceName = entry.getKey();
                        matchSource.sourceValue = entry.getValue();
                        //低调看、酷玩
                        matchSource.liveSource = matchInfo1.getMatchLiveSource();
                        matchStat.data.matchSourceList.add(matchSource);
                    }
                    break;
                }
            }
        }
        System.out.println("请求"+liveSource.name()+"getMatchSourceLive 获得直播源与直播地址："+ (System.currentTimeMillis() - jsStartTime)+"ms");
    }
    /**
     * 根据直播源获得直播数据
     * @author:weida
     * @method:getMatchInfoList
     * @date:Date 2018/2/27
     * @params:[liveSource]
     * @returns:java.util.List<com.suda.pojo.MatchInfo>
     */
    private List<MatchInfo> getMatchInfoList(LiveSource liveSource, String homeTeamName, String guestTeamName){
        long jsStartTime = System.currentTimeMillis();
        String url = "";
        switch (liveSource){
            case KUWAN_Source:url = kuwan_url; break;
            case DIDIAOKAN_Source:url = didiaokan_url; break;
            case LEQIUBA_Source:url = leqiuba_url; break;
            default:break;
        }
        if(StringUtil.isNotBlank(url)){
            //解析页面获得比赛信息
            List<MatchInfo> matchInfoList = resolveMatchBySourceTeamName(liveSource, url, homeTeamName, guestTeamName);
            System.out.println("请求"+liveSource.name()+"MatchInfoList："+ (System.currentTimeMillis() - jsStartTime)+"ms");
            return matchInfoList;
        } else {
            return null;
        }
    }

    /**
     * 解析比赛视频地址，视频地址通过Map的 <source、url>显示
     * @author:ES-BF-IT-126
     * @method:resolveMatchBySource
     * @date:Date 2018/3/1
     * @params:[liveSource, url]
     * @returns:java.util.List
     */
    private List resolveMatchBySource(LiveSource liveSource, String matchurl){
        JsoupUtils jsoupUtils = new JsoupUtils();
        //获得页面代码
        List<MatchInfo> matchInfoList = null;
        String html = null;
        if(DIDIAOKAN_Source == liveSource){
            html = httpClientUtil.sendDataGet(matchurl+didiaokan_murl);
            String jsSrc = jsoupUtils.didiaoParseJs(html);
            if(StringUtil.isNotBlank(jsSrc)){
                //获得页面代码
                String jsMatch = httpClientUtil.sendDataGet(matchurl+jsSrc);
                //解析didiaokan 直播赛程比赛列表
                matchInfoList = jsoupUtils.didiaoParsejsMatch(jsMatch, matchurl);
            }
        } else if(KUWAN_Source == liveSource){
            html = httpClientUtil.sendDataGet(kuwan_url);
            matchInfoList = jsoupUtils.paserHtml(html, kuwan_url, KUWAN_Source);
        }
        return matchInfoList;
    }

    /**
     * 解析比赛视频地址，视频地址通过Map的 <source、url>显示
     * @author:ES-BF-IT-126
     * @method:resolveMatchBySourceTeamName
     * @date:Date 2018/3/1
     * @params:[liveSource, url]
     * @returns:java.util.List
     */
    private List resolveMatchBySourceTeamName(LiveSource liveSource, String matchurl, String homeTeamName, String guestTeamName){
        long jsStartTime = System.currentTimeMillis();
        JsoupUtils jsoupUtils = new JsoupUtils();
        //获得页面代码
        List<MatchInfo> matchInfoList = null;
        String html = null;
        if(DIDIAOKAN_Source == liveSource){
            html = httpClientUtil.sendDataGet(matchurl+didiaokan_murl);
            String jsSrc = jsoupUtils.didiaoParseJs(html);
            if(StringUtil.isNotBlank(jsSrc)){
                //获得页面代码
                String jsMatch = httpClientUtil.sendDataGet(matchurl+jsSrc);
                //解析didiaokan 直播赛程比赛列表
                matchInfoList = jsoupUtils.didiaoParsejsMatchByName(jsMatch, matchurl,homeTeamName,guestTeamName);
            }
        } else if(KUWAN_Source == liveSource){
            html = httpClientUtil.sendDataGet(kuwan_url);
            matchInfoList = jsoupUtils.paserHtml(html, kuwan_url, KUWAN_Source,homeTeamName,guestTeamName);
        }
        System.out.println("请求"+liveSource.name()+"resolveMatchBySourceTeamName 第一层页面："+ (System.currentTimeMillis() - jsStartTime)+"ms");
        return matchInfoList;
    }


}
