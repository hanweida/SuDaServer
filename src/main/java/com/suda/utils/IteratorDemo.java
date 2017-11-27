package com.suda.utils;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

/**
 * Created by ES-BF-IT-126 on 2017/11/27.
 */
public class IteratorDemo {
    /**
     * 迭代一个Node结点的所有根子结点
     * @param parser
     */
    public void listAll(Parser parser) {
        try {
            NodeIterator nodeIterator = parser.elements();
            while( nodeIterator.hasMoreNodes()) {
                System.out.println("======================================");
                Node node = nodeIterator.nextNode();
                System.out.println("getText():" + node.getText());
                System.out.println("toHtml():" + node.toHtml());
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String urlStr = "http://www.leqiuba.cc/zhibo/";
        //HtmlParserUtils类为自己提炼的一个公用类，详细代码将会在后面提供。
        Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");
        IteratorDemo it = new IteratorDemo();
        it.listAll(parser);
    }
}
