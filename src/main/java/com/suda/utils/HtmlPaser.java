package com.suda.utils;

import com.suda.pojo.MatchInfo;
import com.suda.web.enum_const.LiveSource;

import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/12/15.
 */
public interface HtmlPaser {
    List<MatchInfo> paserHtml(String html, String baseUrl, LiveSource liveSource);
    List<MatchInfo> paserHtml(String html, String baseUrl, LiveSource liveSource, String homeTeam, String guestTeam);
}
