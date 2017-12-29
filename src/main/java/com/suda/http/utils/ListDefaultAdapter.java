package com.suda.http.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.suda.http.bean.match.MatchStat.MaxPlayers.MatchPlayerInfo;


import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/12/29.
 */
public class ListDefaultAdapter implements JsonDeserializer<List<?>> {
    public List<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(MatchPlayerInfo.class, new MatchPlayerInfoAdapter())
                    .create();
            String jsonStr = jsonElement.toString();
            if(jsonStr.contains("\"playerStats\":{") && jsonStr.contains("\"type\":\"16\"")){
                jsonStr.replace("\"type\":\"16\"", "\"type\":\"160\"")
                        .replace("\"playerStats\":{", "\"groundStats\":{");
            }
            if(jsonElement.isJsonArray()){
                return gson.fromJson(jsonStr, type);
            } else if(jsonElement.getAsString() != null && !jsonElement.getAsString().equals("")){
                String newGson = "[" + jsonElement.getAsString() + "]";
                return gson.fromJson(newGson, type);
            }
        } catch (Exception e){

        }

        return null;
    }
}
