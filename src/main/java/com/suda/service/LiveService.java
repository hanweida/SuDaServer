package com.suda.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suda.pojo.MatchInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2018/2/7.
 */
public interface LiveService {
    JSONArray getMatchInfo(List<Date> date);
    MatchInfo getMatchSource(String url);
    JSONObject getMatchState(String mid, String tabType, String homeTeamName, String guestTeamName);
}
