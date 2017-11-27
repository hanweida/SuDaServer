package com.suda.utils;

/**
 * Created by ES-BF-IT-126 on 2017/11/27.
 */
import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;

public class VisitorDemo extends NodeVisitor{
    //记录Remark Node数量
    private int remark_node_count;
    //记录Text Node数量
    private int tag_node_count;
    //记录Tag Node数量
    private int text_node_count;

    public void visitRemarkNode(Remark remark) {
        //System.out.println("正在访问第 "+(remark)+" 个Remark Node ");
    }

    public void visitStringNode(Text text) {
        //System.out.println("正在访问第 "+(text.getText())+" 个Text Node ");
        //System.out.println("Text ："+(text.getText()));
        //System.out.println("Html ："+(text.toHtml()));
        //System.out.println("Children ："+(text.getChildren()));
    }

    public void visitTag(Tag tag) {
        //System.out.println("RawTagName "+(tag.getRawTagName()));
        //System.out.println("Text "+(tag.getText()));
        //System.out.println("TagName "+(tag.getTagName()));
    }

    public static void main(String[] args) {
        try{
            //方式一：
            String urlStr = "http://www.leqiuba.cc/zhibo/";
            Parser parser = HtmlParserTool.getParserWithUrlConn2(urlStr, "utf-8");

            NodeVisitor visitor = new VisitorDemo ();
            parser.visitAllNodesWith (visitor);

            System.out.println("=========================================");
            //方式二（常用）：
            parser.reset();
            NodeVisitor visitor2 = new NodeVisitor() {
                public void visitTag(Tag tag) {
                    System.out.println("正在访问的tag：" + tag.getTagName());
                }
            };
            //parser.visitAllNodesWith(visitor2);


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}