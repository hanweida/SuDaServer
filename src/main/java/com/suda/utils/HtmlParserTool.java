package com.suda.utils;

import com.suda.pojo.MatchInfo;
import com.suda.pojo.MatchUrl;
import com.suda.web.HtmlAttr;
import com.suda.web.enum_const.LiveSource;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.htmlparser.visitors.NodeVisitor;
import org.springframework.beans.factory.support.ManagedList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ES-BF-IT-126 on 2017/11/27.
 */
public class HtmlParserTool implements HtmlPaser{

    /**
     * 解析HTML
     * 一级div : class=match_list
     * 二级div : class=match_time,match_name,match_info,home_team,guest_team
     * 三级div : class=home_logo,guest_logo
     * 三级img : img
     * @author:ES-BF-IT-126
     * @method:filter
     * @date:Date 2017/11/28
     * @params:[urlStr]
     * @returns:void
     */
    public List<MatchInfo> htmlParser(String urlStr) {
        Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");
        NodeList nodelist;
        ArrayList<MatchInfo> matchInfoArrayList = null;

        try {
            NodeFilter filter = new HasAttributeFilter("class","matches");//match_list
            nodelist = parser.parse(filter);
            if(nodelist.size() > 0){
                Node node = nodelist.elementAt(0);
                NodeList nodeList = node.getChildren();
                nodeList = nodeList.extractAllNodesThatMatch(new HasAttributeFilter("class","match_list"));
                Node[] nodes = nodeList.toNodeArray();
                matchInfoArrayList = new ManagedList<MatchInfo>();
                for(Node node1 : nodes){
                    MatchInfo matchInfo = getMatchInfo(node1.getChildren());
                    matchInfoArrayList.add(matchInfo);
                }
            }
        } catch (ParserException e1) {
            e1.printStackTrace();
        }
        return matchInfoArrayList;
    }


    private MatchInfo getMatchInfo(NodeList nodeList){
        final MatchInfo matchInfo = new MatchInfo();
        NodeVisitor visitor2 = new NodeVisitor(false, true) {
            boolean filter = false;
            String currentAttr = "";
            //第一层
            public void visitTag(Tag tag) {
                String attr = tag.getAttribute("class");
                if(StringUtil.isNotBlank(attr)){
                    if(attr.equals(HtmlAttr.MATCH_TIME.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.MATCH_NAME.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.MATCH_INFO.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.HOME_TEAM.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.HOME_LOGO.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.GUEST_TEAM.getValue())){
                        currentAttr = attr;
                    }
                    if(attr.equals(HtmlAttr.GUEST_LOGO.getValue())){
                        currentAttr = attr;
                    }
                }
                //第二层
                tag.accept(new NodeVisitor(true, true) {
                    @Override
                    public void visitTag(Tag tag) {
                        String attr = tag.getAttribute("class");
                        if(StringUtil.isNotBlank(attr)){
                            if(attr.equals(HtmlAttr.HOME_TEAM.getValue())){
                                currentAttr = attr;
                            }
                            if(attr.equals(HtmlAttr.GUEST_TEAM.getValue())){
                                currentAttr = attr;
                            }
                            if(attr.equals(HtmlAttr.HOME_LOGO.getValue())){
                                currentAttr = attr;
                            }
                            if(attr.equals(HtmlAttr.GUEST_LOGO.getValue())){
                                currentAttr = attr;
                            }
                            if("A".equals(tag.getTagName())){
                                matchInfo.setMatch_url(tag.getAttribute("href").trim());
                                filter = true;
                            }
                        }
                        //第三层
                        tag.accept(new NodeVisitor(false, false) {
                            @Override
                            public void visitTag(Tag tag) {
                                if("img".equals(tag.getRawTagName()) && currentAttr.equals(HtmlAttr.HOME_LOGO.getValue())){
                                    matchInfo.setHome_logo_url(tag.getAttribute("src").trim());
                                }
                                if("img".equals(tag.getRawTagName()) && currentAttr.equals(HtmlAttr.GUEST_LOGO.getValue())){
                                    matchInfo.setGuest_logo_url(tag.getAttribute("src").trim());
                                }
                            }
                        });
                    }

                    @Override
                    public void visitStringNode(Text string) {
                        //System.out.println("2");
                        if(StringUtil.isNotBlank(string.getText()) && !filter){
                            String attr = string.getText();
                            if(currentAttr.equals(HtmlAttr.MATCH_TIME.getValue())){
                                matchInfo.setMatch_time(attr.trim());
                            }
                            if(currentAttr.equals(HtmlAttr.MATCH_NAME.getValue())){
                                matchInfo.setMatch_name(attr.trim());
                            }
                            if(currentAttr.equals(HtmlAttr.HOME_TEAM.getValue()) && !(attr.trim().equals("vs"))){
                                matchInfo.setHome_team(attr.trim());
                            }if(currentAttr.equals(HtmlAttr.GUEST_TEAM.getValue())){
                                matchInfo.setGuest_team(attr.trim());
                            }
                        }
                        filter = false;
                    }
                });
            }
        };

        try {
            nodeList.visitAllNodesWith(visitor2);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        return matchInfo;
    }

    private MatchUrl getMatchUrl(Node node){
        final MatchUrl matchUrl = new MatchUrl();

        NodeVisitor visitor2 = new NodeVisitor(true, true) {
            String patternExp = "player\\('(.*?)','(.*?)'\\)";
            Pattern pattern = Pattern.compile(patternExp);
            Matcher matcher = null;
            String currentAttr = "";

            //第一层
            public void visitTag(Tag tag) {
                if("p".equals(tag.getRawTagName())){
                    if("active".equals(tag.getAttribute("class"))){
                        matchUrl.setActive(true);
                    }
                    String playerValue = tag.getAttribute("onClick");
                    if(StringUtil.isNotBlank(playerValue)){
                        matcher = pattern.matcher(playerValue);
                        int index = 0;
                        while(matcher.lookingAt()){
                            if(index != 0){
                                switch (index){
                                    case 1 : matchUrl.setPlayer(matcher.group(index));
                                        break;
                                    case 2 : matchUrl.setVid(matcher.group(index));
                                        break;
                                }
                            }
                            if(index > 1){
                                break;
                            }
                            index++;
                        }
                    }
                }
            }

            @Override
            public void visitStringNode(Text string) {
                matchUrl.setName(string.getText().trim());
                System.out.println(string.getText().trim());
//                /matchUrl.setName(string.getText());
            }
        };
        node.accept(visitor2);
        return matchUrl;
    }


    public List<MatchUrl> htmlParserVideo(String urlStr) {
        Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");
        NodeList nodelist;
        ArrayList<MatchUrl> matchUrlArrayList = null;

        try {
            NodeFilter filter = new HasAttributeFilter("class","signal");//match_list
            nodelist = parser.parse(filter);
            if(nodelist.size() > 0){
                Node node = nodelist.elementAt(0);
                NodeList nodeList = node.getChildren();
                Node[] nodes = nodeList.toNodeArray();
                matchUrlArrayList = new ArrayList<MatchUrl>();
                for(Node node1 : nodes){
                    if(StringUtil.isBlank(node1.toHtml())){
                        continue;
                    }
                    MatchUrl matchUrl = getMatchUrl(node1);
                    if(null != matchUrl){
                        System.out.println(matchUrl.toString());
                        matchUrlArrayList.add(matchUrl);
                    }
                }
            }
        } catch (ParserException e1) {
            e1.printStackTrace();
        }
        return matchUrlArrayList;
    }

    public static Parser getParserWithUrlConn2(String urlStr, String encoding) {
        Parser parser = null;
        try {
            ConnectionManager manager = Page.getConnectionManager();
            parser = new Parser(manager.openConnection(urlStr));
            parser.setEncoding(encoding);
            return parser;
        } catch (ParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 循环访问所有节点，输出包含关键字的值节点
    public static void extractKeyWordText(String url, String keyword) {
        try {
            //生成一个解析器对象，用网页的 url 作为参数
            Parser parser = new Parser(url);
            //设置网页的编码,这里只是请求了一个 gb2312 编码网页
            parser.setEncoding("gb2312");
            //迭代所有节点, null 表示不使用 NodeFilter
            NodeList list = parser.parse(null);
            //从初始的节点列表跌倒所有的节点
            processNodeList(list, keyword);
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    private static void processNodeList(NodeList list, String keyword) {
        //迭代开始
        SimpleNodeIterator iterator = list.elements();
        while (iterator.hasMoreNodes()) {
            Node node = iterator.nextNode();
            //得到该节点的子节点列表
            NodeList childList = node.getChildren();
            //孩子节点为空，说明是值节点
            if (null == childList)
            {
                //得到值节点的值
                String result = node.toPlainTextString();
                //若包含关键字，则简单打印出来文本
                if (result.indexOf(keyword) != -1)
                    System.out.println(result);
            } //end if
            //孩子节点不为空，继续迭代该孩子节点
            else
            {
                processNodeList(childList, keyword);
            }//end else
        }//end wile
    }

    //测试的 main 方法
    public static void main(String[] args) {
        String urlStr = "http://m.leqiuba.cc//zhibo/517.html";
        //Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");
        HtmlParserTool htmlParserTool = new HtmlParserTool();
        htmlParserTool.htmlParserVideo(urlStr);
    }

    public List<MatchInfo> paserHtml(String html, String baseUrl) {
        return htmlParser(html);
    }

    @Override
    public List<MatchInfo> paserHtml(String html, String baseUrl, LiveSource liveSource) {
        return htmlParser(html);
    }
}
