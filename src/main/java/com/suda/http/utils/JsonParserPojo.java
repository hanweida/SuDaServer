package com.suda.http.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suda.http.bean.match.MatchStat;

import java.util.List;


/**
 * Created by ES-BF-IT-126 on 2017/12/28.
 */
public class JsonParserPojo {
    static Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(MatchStat.MaxPlayers.MatchPlayerInfo.class, new MatchPlayerInfoAdapter())
            .registerTypeHierarchyAdapter(List.class, new ListDefaultAdapter())
            .create();
    public static <T> T parseWithGson(Class<T> tClass, String jsonStr){
        return gson.fromJson(jsonStr, tClass);
    }
}
