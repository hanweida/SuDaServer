package com.suda.http.bean.hupu.news;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2018/1/5.
 */
public class HuPuNewsDetail extends HuPuNewsBase {
    public Result result;
    public class Result{
        public String url;
        public String title;
        public String summary;
        public String replies;
        public String img;
        public String lights;
        public String iscollected;
        public OfflineData offline_data;
        public class OfflineData  implements Serializable{
            public Integer status;
            public Data data;
            public class Data implements Serializable{
                public String projectId;
                public String version;
                public Integer nopic;
                public Integer ft;
                public String client;
                public String advId;
                public Integer night;
                public String uid;
                public String cid;
                public String leaguesEn;
                public String puid;
                public String user_name;
                public String header;
                public News news;
                public class News implements Serializable{
                    public String nid;
                    public String lights;
                    public String replies;
                    public String title;
                    public String type;
                    public String origin;
                    public String img;
                    public String img_m;
                    public String content;
                    public String addtime;
                    public String league;
                    public String summary;
                    public String origin_url;
                    public String media_desc;
                    public String replyurl;
                    public String app_url;
                    public String m_url;
                }
            }
        }

    }

}
