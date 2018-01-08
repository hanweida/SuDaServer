package com.suda.http.bean.hupu.news;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 18-1-9
 * Time: 上午1:24
 * To change this template use File | Settings | File Templates.
 */
public class HuPuNewsReply extends HuPuNewsBase{
    public String count;
    public Integer hasNextPage;
    public News news;
    public List<Data> data;
    public List<LightComments> light_comments;
    public class News implements Serializable{
        public String title;
        public String origin;
        public String origin_url;
        public String m_url;
        public String addtime;
    }
    public class Data implements Serializable{
        public String audit_status;
        public String puid;
        public String from;
        public String ncid;
        public String light_count;
        public String unlight_count;
        public String uid;
        public String user_name;
        public String content;
        public String create_time;
        public String cid;
        public Integer lighted;
        public String format_time;
        public String header;
    }

    public class LightComments implements Serializable{
        public String audit_status;
        public String ncid;
        public String puid;
        public String light_count;
        public String unlight_count;
        public String uid;
        public String user_name;
        public String content;
        public String cid;
        public String format_time;
        public String header;
        public Integer lighted;
        public QuoteData quote_data;
        public class QuoteData implements Serializable{
            public String content;
            public String user_name;
        }
    }
}
