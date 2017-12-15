package com.suda.utils;

import com.suda.pojo.MatchInfo;

import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/12/15.
 */
public interface HtmlPaser {
    List<MatchInfo> paserHtml(String html, String baseUrl);
}
