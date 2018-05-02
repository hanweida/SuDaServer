package com.suda.utils;

import com.suda.pojo.MatchInfo;
import com.suda.web.enum_const.LiveSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/12/15.
 */
public class JsoupUtils implements HtmlPaser {


    private MatchInfo parseTeamInfo(Elements elements, MatchInfo matchInfo, int type){
        if(elements.size() >0){
            Element team_element = elements.get(0);
            Elements elements1 = team_element.children();
            String img_src = elements1.tagName("img").attr("src");
            String p_text = elements1.tagName("p").text();
            if(type == 0){
                matchInfo.setHome_team(p_text);
                matchInfo.setHome_logo_url(matchInfo.getBase_url()+img_src);
            } else {
                matchInfo.setGuest_team(p_text);
                matchInfo.setGuest_logo_url(matchInfo.getBase_url()+img_src);
            }
        } else {
            System.out.println("No team-left");
        }
        return matchInfo;
    }

    private List<MatchInfo> kuwanJsoupParse(String html, String baseUrl){
        return kuwanJsoupParseByTeamName(html, baseUrl, null, null);
    }

    private List<MatchInfo> kuwanJsoupParseByTeamName(String html, String baseUrl, String homeTeam, String guestTeam){
        List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByAttributeValue("class", "game-list");
        if(elements.size() > 0){
            Element game_list_element = elements.get(0);
            Elements a_elements = game_list_element.getElementsByTag("a");
            if(a_elements.size() > 0){
                Iterator<Element> elementIterator = a_elements.iterator();
                while (elementIterator.hasNext()){
                    MatchInfo matchInfo = new MatchInfo();
                    matchInfo.setBase_url(baseUrl);
                    Element a_element = elementIterator.next();
                    String href = a_element.attr("href");
                    matchInfo.setMatch_url(baseUrl+href);
                    //客队
                    parseTeamInfo(a_element.getElementsByAttributeValue("class", "team-left"), matchInfo, 0);
                    //主队
                    parseTeamInfo(a_element.getElementsByAttributeValue("class", "team-right"), matchInfo, 1);

                    String match_name = a_element.getElementsByAttributeValue("class", "type").text();
                    String match_time = a_element.getElementsByAttributeValue("class", "time").text();
                    matchInfo.setMatch_name(match_name);
                    matchInfo.setMatch_time(match_time);
                    if(StringUtil.isNotBlank(homeTeam) && StringUtil.isNotBlank(guestTeam)
                            && homeTeam.equals(matchInfo.getHome_team())
                            && guestTeam.equals(matchInfo.getGuest_team())
                            ){
                        //如果参数homeTeam与guestTeam不为空，则返回的是指定比赛信息
                        matchInfoList.add(matchInfo);
                        break;
                    } else if(null == homeTeam && null == guestTeam){
                        //如果参数homeTeam与guestTeam为空，则返回的是所有比赛信息列表
                        matchInfoList.add(matchInfo);
                    }
                }
            } else {
                System.out.println("No Tag a");
            }
        } else {
            System.out.println("No class=game-list");
        }
        return matchInfoList;
    }
    /**
     * 解析html页面，根据直播源枚举类型，分别解析页面
     * @author:ES-BF-IT-126
     * @method:paserHtml
     * @date:Date 2018/2/7
     * @params:[html, baseUrl, liveSource]
     * @returns:java.util.List<com.suda.pojo.MatchInfo>
     */
    @Override
    public List<MatchInfo> paserHtml(String html, String baseUrl, LiveSource liveSource) {
        return paserHtml(html, baseUrl, liveSource, null, null);
    }

    @Override
    public List<MatchInfo> paserHtml(String html, String baseUrl, LiveSource liveSource, String homeTeam, String guestTeam) {
        List<MatchInfo> matchInfoList = null;
        switch (liveSource){
            case KUWAN_Source: matchInfoList=kuwanJsoupParseByTeamName(html, baseUrl, homeTeam, guestTeam);break;
            default:break;
        }
        return matchInfoList;
    }

    /**
     * 返回didiaokanJs代码
     * @author:ES-BF-IT-126
     * @method:didiaoParseJs
     * @date:Date 2018/2/28
     * @params:[str]
     * @returns:java.lang.String
     */
    public String didiaoParseJs(String str){
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("script");
        if(elements.size() > 0){
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                Element element = iterator.next();
                if(element.hasAttr("src")){
                    String value = element.attr("src");
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 返回didiaokanQQ直播Src代码
     * @author:ES-BF-IT-126
     * @method:didiaoParseJs
     * @date:Date 2018/2/28
     * @params:[str]
     * @returns:java.lang.String
     */
    public String didiaoParseQQ(String str){
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("iframe");
        if(elements.size() > 0){
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                Element element = iterator.next();
                if(element.hasAttr("src")){
                    String value = element.attr("src");
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 根据js获得比赛列表 信息，包括比赛队名与比赛直播初步地址
     * @author:ES-BF-IT-126
     * @method:didiaoParsejsMatch
     * @date:Date 2018/3/9
     * @params:[str, baseUrl]
     * @returns:java.util.List<com.suda.pojo.MatchInfo>
     */
    public List<MatchInfo> didiaoParsejsMatch(String str, String baseUrl){
        return didiaoParsejsMatchByName(str, baseUrl, null, null);
    }

    /**
     * 根据js获得比赛列表 信息，包括比赛队名与比赛直播初步地址
     * @author:ES-BF-IT-126
     * @method:didiaoParsejsMatch
     * @date:Date 2018/3/9
     * @params:[str, baseUrl]
     * @returns:java.util.List<com.suda.pojo.MatchInfo>
     */
    public List<MatchInfo> didiaoParsejsMatchByName(String str, String baseUrl, String homeTeamName, String guestTeamName){
        List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("a");
        if(elements.size() > 0){
            //去掉第一个didiaokan 非比赛项
            elements.remove(0);
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setBase_url(baseUrl);
                Element element = iterator.next();
                //客队
                parseDidiaoTeamInfo(element.getElementsByAttributeValue("class", matchReplacedStr("team-left")), matchInfo, 0);
                //主队
                parseDidiaoTeamInfo(element.getElementsByAttributeValue("class", matchReplacedStr("team-right")), matchInfo, 1);
                String matchUrl = element.attr("href");
                matchUrl = getReplacedStr(matchUrl);
                matchInfo.setMatch_url(matchUrl);
                Elements elements1 = element.getElementsByAttributeValue("class", matchReplacedStr("score-content"));
                if(elements1.size() > 0){
                    Element element1 = elements1.get(0);
                    String text = element1.text();
                    matchInfo.setMatch_time(text);
                }
                if(matchInfo.getGuest_team().contains(guestTeamName) && matchInfo.getHome_team().contains(homeTeamName)){
                    //如果参数homeTeam与guestTeam不为空，则返回的是指定比赛信息
                    matchInfoList.add(matchInfo);
                    break;
                } else if(null == guestTeamName && null == homeTeamName){
                    //如果参数homeTeam与guestTeam不为空，则返回的是所有比赛信息列表
                    matchInfoList.add(matchInfo);
                }
            }
        }
        return matchInfoList;
    }

    private MatchInfo parseDidiaoTeamInfo(Elements elements, MatchInfo matchInfo, int type){
        if(elements.size() >0){
            Element team_element = elements.get(0);
            Elements elements1 = team_element.children();
            String img_src = elements1.tagName("img").attr("src");
            ////img_src = img_src.replaceAll("\\\"","");
            //替换\" 为 ""
            img_src = getReplacedStr(img_src);

            String p_text = elements1.tagName("font").text();
            if(type == 0){
                matchInfo.setGuest_team(p_text);
                matchInfo.setGuest_logo_url(img_src);
            } else {
                matchInfo.setHome_team(p_text);
                matchInfo.setHome_logo_url(img_src);
            }
        } else {
            //System.out.println("No team-left");
        }
        return matchInfo;
    }

    /**
     * 根据 cctv,或者qq 源，得到Map队列<源名，源地址>
     * @author:ES-BF-IT-126
     * @method:matchReplacedStr
     * @date:Date 2018/3/1
     * @params:[replaceStr]
     * @returns:java.lang.String
     */
    public Map<String, String> parseSourceChoose(String html, String baseUrl){
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByAttributeValue("class", "mv_action");
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Element> iterator = elements.iterator();
        //MapKey 不允许重复，所以如果有重复key 时，数字加1
        int qqFlag = 0;
        while (iterator.hasNext()){
            String href = null;
            Element element = iterator.next();
            if(element.childNodeSize() > 0){
                href = element.child(0).attr("href");
                String text = element.text();
                //MapKey 不允许重复，所以如果有重复key 时，数字加1
                //例如: QQ，QQ1
                if(text.contains("QQ")){
                    if(qqFlag != 0){
                        text = text+qqFlag;
                    }
                    qqFlag += 1;
                }

                //过滤英文源，因为英文源不能播放
                if(!text.contains("英文") && !text.toLowerCase().contains("cctv")){
                    map.put(text, baseUrl+href);
                }
                //map.put(text, baseUrl+href);
            }
        }
        return map;
    }

    /**
     * 替换 didiaokan 链接内的 \" 无用转义符
     * 例如：href=\"http://m.didiaokan.com\" 替换为 href="http://m.didiaokan.com"
     * @author:ES-BF-IT-126
     * @method:getReplaced
     * @date:Date 2018/3/1
     * @params:[replaceStr]
     * @returns:java.lang.String
     */
    private String getReplacedStr(String replaceStr){
        if(StringUtil.isNotBlank(replaceStr)){
            replaceStr = replaceStr.replace("\\\"", "");
        }
        return replaceStr;
    }

    /**
     * 插入 didiaokan 链接内的 \" 为转义符
     * 例如:<div class=\"team-left\">
     * @author:ES-BF-IT-126
     * @method:matchReplacedStr
     * @date:Date 2018/3/1
     * @params:[replaceStr]
     * @returns:java.lang.String
     */
    private String matchReplacedStr(String matchStr){
        StringBuilder stringBuilder = new StringBuilder(matchStr);
        stringBuilder.insert(0, "\\\"");
        stringBuilder.append("\\\"");
        return stringBuilder.toString();
    }

}
