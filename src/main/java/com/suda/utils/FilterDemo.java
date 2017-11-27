package com.suda.utils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.htmlparser.visitors.ObjectFindingVisitor;

/**
 * Created by ES-BF-IT-126 on 2017/11/27.
 */
public class FilterDemo {

    /**
     * 使用TagNameFilter过滤所有table标签结点。
     * @param parser
     */
    public void filter(Parser parser) {


        NodeList nodelist;
        try {
            /*
             * 过滤所有table标签结点。
             */
            NodeFilter filter = new TagNameFilter("tr");
            nodelist = parser.parse(filter);
            Node[] nodes = nodelist.toNodeArray();

            NodeVisitor visitor2 = new NodeVisitor() {
                boolean filter = false;
                public void visitTag(Tag tag) {
                    //System.out.println(tag.getAttribute("class"));
//                    System.out.println("正在访问的tag：" + tag.getTagName());
//                    System.out.println("正在访问的RawTagName：" + tag.getRawTagName());
//                    System.out.println("正在访问的Text：" + tag.getText());
//                    System.out.println("正在访问的Html：" + tag.toHtml());
//                    System.out.println("正在访问的EndTag：" + tag.getEndTag());
//                    System.out.println("---------------------------");
                }
                public void visitStringNode(Text text) {
                    //System.out.println("2");
                    //System.out.println("Text ："+(text.getText()));
                    //System.out.println("Html ："+(text.toHtml()));
                    //System.out.println("Children ："+(text.getChildren()));
                    //System.out.println("PlainTextString ："+(text.toPlainTextString()));
                    //System.out.println("getPage ："+ text.getPage().getText());
                }

                public void visitRemarkNode(Remark remark) {
//                    System.out.println("Html ："+(remark.toHtml()));
//                    System.out.println("Text ："+(remark.getText()));
//                    System.out.println("PlainTextString ："+(remark.toPlainTextString()));
                }


            };

            for(Node node1 : nodes){
                NodeList nodeList = node1.getChildren();
                nodeList.visitAllNodesWith(visitor2);
            }
            nodelist.visitAllNodesWith(visitor2);

//            Node[] nodes = nodelist.toNodeArray();
//            for(Node node : nodes){
//                //System.out.println("------"+node.toHtml());
//                NodeList nodeList1 = node.getChildren();
//                for(Node node1 : nodeList1.toNodeArray()){
//                    System.out.println("------"+node1.toHtml());
//                };
//
//            }



            Node node = nodelist.elementAt(0);
            Node firstChild = node.getFirstChild();
            Node secondChild = firstChild.getNextSibling();
            Node thirdChild = secondChild.getNextSibling();
            Node forthChild = thirdChild.getNextSibling();

            //System.out.println("node-->" + node.getText() + "-->" + node.toHtml());
            //System.out.println("firstChild-->" + firstChild.getText() + "-->" + firstChild.toHtml());//注意换行符，此结点为换行符。
            //System.out.println("secondChild-->" + secondChild.getText() + "-->" + secondChild.toHtml());
            //System.out.println("thirdChild-->" + thirdChild.getText() + "-->" + thirdChild.toHtml());
            //System.out.println("forthChild-->" + forthChild.getText() + "-->" + forthChild.toHtml());

        } catch (ParserException e1) {
            e1.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String urlStr = "http://www.leqiuba.cc/zhibo/";
        Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");
        FilterDemo filter = new FilterDemo();
        filter.filter(parser);
    }

}
