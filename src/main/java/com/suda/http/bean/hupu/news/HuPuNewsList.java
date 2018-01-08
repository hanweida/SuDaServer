package com.suda.http.bean.hupu.news;

import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2018/1/5.
 */
public class HuPuNewsList {
    public Result result;
    public Integer is_login;
    public Long crt;

    public class Result{
        public Integer nextDataExists;
        public List<Data> data;
        public class Data{
            public String nid;
            public String title;
            public String summary;
            public Long uptime;
            public String img;
            public Integer type;
            public Integer lights;
            public Integer replies;
            public Integer read;
        }
    }
}
