package com.suda.utils;

import com.suda.pojo.MatchInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                matchInfo.setGuest_team(p_text);
                matchInfo.setGuest_logo_url(matchInfo.getBase_url()+img_src);
            } else {
                matchInfo.setHome_team(p_text);
                matchInfo.setHome_logo_url(matchInfo.getBase_url()+img_src);
            }
        } else {
            System.out.println("No team-left");
        }
        return matchInfo;
    }

    private List<MatchInfo> jsoupParse(String html, String baseUrl){
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
                    matchInfoList.add(matchInfo);
                }
            } else {
                System.out.println("No Tag a");
            }
        } else {
            System.out.println("No class=game-list");
        }
        return matchInfoList;
    }

    public static void main(String[] args) {
        String html = "\n" +
                "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=utf8>\n" +
                "<title>  酷玩直播</title>\n" +
                "<link rel=\"shortcut icon\" href=\"http://www.kuwantiyu.com/favicon.ico\" />\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge,chrome=1\">\n" +
                "<meta name=\"renderer\" content=\"webkit\">\n" +
                "<meta name=\"description\" content=\"酷玩直播免费观看足球、NBA、中超、英超、西甲、意甲、德甲、法甲和世界杯直播!\">\n" +
                "<meta name=\"keywords\" content=\"酷玩直播\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no\">\n" +
                "<meta name=\"applicable-device\" content=\"mobile\" />\n" +
                "<link href=\"https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css\" rel=\"stylesheet\">\n" +
                "<script src=\"https://cdn.bootcss.com/jquery/1.8.3/jquery.min.js\" charset=\"utf-8\"></script>\n" +
                "<link href=\"/static/mobile/style/style.css\" rel=\"stylesheet\">\n" +
                "<script src=\"/static/play.js?v=280433a363394d3a259d0d1044b86afc\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
                "<meta HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" +
                "<meta HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\n" +
                "<meta HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\n" +
                "<script type=\"text/javascript\">\n" +
                "\t(function(Switch){\n" +
                "\tvar switch_mob = window.location.hash;\n" +
                "\tvar pathname = window.location.pathname;\n" +
                "\tif(switch_mob != \"#mobile\"){\n" +
                "\tif(!/iphone|ipod|ipad|ipad|Android|nokia|blackberry|webos|webos|webmate|bada|lg|ucweb|skyfire|sony|ericsson|mot|samsung|sgh|lg|philips|panasonic|alcatel|lenovo|cldc|midp|wap|mobile/i.test(navigator.userAgent.toLowerCase())){\n" +
                "\t\t//Switch.location.href='http://www.kanbisai.tv'+pathname+'';\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\t})(window);\n" +
                "\t</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "\tvar _hmt = _hmt || [];\n" +
                "\t(function() {\n" +
                "\t  var hm = document.createElement(\"script\");\n" +
                "\t  hm.src = \"https://hm.baidu.com/hm.js?af0cc1060196e78360bd49de46d9b704\";\n" +
                "\t  var s = document.getElementsByTagName(\"script\")[0]; \n" +
                "\t  s.parentNode.insertBefore(hm, s);\n" +
                "\t})();\n" +
                "\t</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "<div class=\"wrap\">\n" +
                "<div class=\"header\">\n" +
                "<a href=\"/\"><img src=\"/static/mobile/logo/m_logo.png\" /></a>\n" +
                "</div>\n" +
                "<style>\n" +
                ".weixin {\n" +
                "    margin: 0;\n" +
                "    color: yellow;\n" +
                "    font-size: 18px;\n" +
                "    font-weight: bold;\n" +
                "}\n" +
                ".qun {\n" +
                "   color: cyan;\n" +
                "    font-size: 18px;\n" +
                "    font-weight: bold;\n" +
                "}\n" +
                "</style>\n" +
                "<div class=\"focus\">\n" +
                "<p class=\"weixin\">关注公众号: kuwantiyu 免费会员场</p>\n" +
                "<p class=\"qun\" style=\"line-height:24px\">球迷交流微信号：511852 红包免费领</p>\n" +
                "</div>\n" +
                "<div class=\"tips\">\n" +
                "<a href=\"#\">\n" +
                "<i class=\"fa fa-bolt\"></i> 公告：关注我们，不迷路 </a>\n" +
                "</div>\n" +
                "<div class=\"tvs\" style=\"display:none\">\n" +
                "<a href=\"/tv/2.html\" target=\"_blank\">\n" +
                "<div class=\"chanel\">\n" +
                "<img src=\"/upload/20170915/59bc0f3f857d1.jpg\">\n" +
                "<p>CCTV5</p>\n" +
                "</div>\n" +
                "</a>\n" +
                "<a href=\"/tv/3.html\" target=\"_blank\">\n" +
                "<div class=\"chanel\">\n" +
                "<img src=\"/upload/20170915/59bc0f5e72252.jpg\">\n" +
                "<p>CCTV5+</p>\n" +
                "</div>\n" +
                "</a>\n" +
                "<a href=\"/tv/9.html\" target=\"_blank\">\n" +
                "<div class=\"chanel\">\n" +
                "<img src=\"/upload/20171006/59d7040b998fc.png\">\n" +
                "<p>NBA百事通</p>\n" +
                "</div>\n" +
                "</a>\n" +
                "</div>\n" +
                "<div class=\"game-list\">\n" +
                "<h2 class=\"today\">今日直播</h2>\n" +
                "<a href=\"/zhibo/1913.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e1e6c28ab.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'>老鹰</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">NBA常规赛</p>\n" +
                "<p class=\"live\">正在直播 </p>\n" +
                "<p class=\"time\"> 12-15 08:30</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e1ebea319.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'> 活塞</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1912.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31df56687b3.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'>篮网</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">NBA常规赛</p>\n" +
                "<p class=\"live\">正在直播 </p>\n" +
                "<p class=\"time\"> 12-15 08:30</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31df6068d21.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'> 尼克斯</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1914.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e28451df1.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'>森林狼</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">NBA常规赛</p>\n" +
                "<p class=\"live\">正在直播 </p>\n" +
                "<p class=\"time\"> 12-15 09:00</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e28a09a55.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'> 国王</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1915.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e324ac58f.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'>骑士</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">NBA常规赛</p>\n" +
                "<p class=\"live\">正在直播 </p>\n" +
                "<p class=\"time\"> 12-15 09:00</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e32951d52.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'> 湖人</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1916.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e3fb883f7.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'>勇士</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">NBA常规赛</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 11:30</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e40024fdc.png\">\n" +
                "<p style='font-weight:bold;color:#ff0000'> 小牛</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1917.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e5445a83a.png\">\n" +
                "<p>韩国女足</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">东亚杯</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 15:10</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e548c25c0.png\">\n" +
                "<p> 中国女足</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1918.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e5a520111.png\">\n" +
                "<p>上海</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e5a9ab6a1.png\">\n" +
                "<p> 辽宁</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1921.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e69870f59.png\">\n" +
                "<p>四川</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e69f826eb.png\">\n" +
                "<p> 北京</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1922.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e6ed15c3b.png\">\n" +
                "<p>广州</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e6f0c100d.png\">\n" +
                "<p> 浙江</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1919.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e5feac2b9.png\">\n" +
                "<p>同曦</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e604cc785.png\">\n" +
                "<p> 山东</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1923.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e7451e009.png\">\n" +
                "<p>深圳</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e74888377.png\">\n" +
                "<p> 吉林</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<a href=\"/zhibo/1920.html\" class=\"game-item\">\n" +
                "<div class=\"team-left\">\n" +
                "<img src=\"/upload/20171214/5a31e64e083a6.png\">\n" +
                "<p>广东</p>\n" +
                "</div>\n" +
                "<div class=\"game-info\">\n" +
                "<p class=\"type\">CBA第16轮</p>\n" +
                "<p class=\"live\">未开始 </p>\n" +
                "<p class=\"time\"> 12-15 19:35</p>\n" +
                "</div>\n" +
                "<div class=\"team-right\">\n" +
                "<img src=\"/upload/20171214/5a31e6522fac7.png\">\n" +
                "<p> 福建</p>\n" +
                "</div>\n" +
                "<div class=\"clear\"></div>\n" +
                "</a>\n" +
                "<h2 class=\"tomorrow\">明日直播</h2>\n" +
                "<p class=\"nodata\">纳尼，明日直播暂未更新</p>\n" +
                "<h2 class=\"today\">近期直播</h2>\n" +
                "<p class=\"nodata\">纳尼，近期直播暂未更新</p>\n" +
                "</div>\n" +
                "<p id=\"back-to-top\"><a href=\"#top\"></a></p>\n" +
                "<script type=\"text/javascript\">\n" +
                "\t\t\t  $(document).ready(function() {\n" +
                "\t\t\t\t$(\"#back-to-top\").hide();\n" +
                "\t\t\t\t$(function() {\n" +
                "\t\t\t\t  $(window).scroll(function() {\n" +
                "\t\t\t\t\tif ($(window).scrollTop() > 200) {\n" +
                "\t\t\t\t\t  $(\"#back-to-top\").fadeIn(500);\n" +
                "\t\t\t\t\t} else {\n" +
                "\t\t\t\t\t  $(\"#back-to-top\").fadeOut(500);\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t  });\n" +
                "\t\t\t\t  $(\"#back-to-top\").click(function() {\n" +
                "\t\t\t\t\t$('body,html').animate({\n" +
                "\t\t\t\t\t  scrollTop:0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t1000);\n" +
                "\t\t\t\t\treturn false;\n" +
                "\t\t\t\t  });\n" +
                "\t\t\t\t});\n" +
                "\t\t\t  });\n" +
                "\t</script>\n" +
                "<div class=\"footer-bar\">\n" +
                "<a href=\"/zhibo\" class=\"active\"><i class=\"fa fa-video-camera\"></i><p>直播</p></a>\n" +
                "<a href=\"/tv\"><i class=\"fa fa-signal\"></i><p>频道</p></a>\n" +
                "<a href=\"/zixun\"><i class=\"fa fa-refresh\"></i><p>动态</p></a>\n" +
                "<a href=\"/about\"><i class=\"fa fa-wechat\"></i><p>关注</p></a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"display:none\">\n" +
                "<script language='javascript' type='text/javascript' src='//js.users.51.la/19291800.js'></script>\n" +
                "<script src='https://s22.cnzz.com/z_stat.php?id=1264605686&web_id=1264605686' language='JavaScript'></script>\n" +
                "<script async src='//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js'></script>\n" +
                "<script>\n" +
                "  (adsbygoogle = window.adsbygoogle || []).push({\n" +
                "    google_ad_client: 'ca-pub-5444453449619259',\n" +
                "    enable_page_level_ads: true\n" +
                "  });\n" +
                "</script></div>\n" +
                "</body>\n" +
                "</html>";
        JsoupUtils jsoupUtils = new JsoupUtils();
        List<MatchInfo> matchInfoList = jsoupUtils.jsoupParse(html, "http://www.kuwantiyu.com");
        System.out.println();
    }

    public List<MatchInfo> paserHtml(String html, String baseUrl) {
        List<MatchInfo> matchInfoList = jsoupParse(html, baseUrl);
        return matchInfoList;
    }
}