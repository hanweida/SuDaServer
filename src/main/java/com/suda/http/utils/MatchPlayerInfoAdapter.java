package com.suda.http.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.suda.http.bean.match.MatchStat;
import com.suda.http.bean.match.MatchStat.MaxPlayers.MatchPlayerInfo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by ES-BF-IT-126 on 2017/12/29.
 */
public class MatchPlayerInfoAdapter implements JsonDeserializer<MatchPlayerInfo>,JsonSerializer<MatchPlayerInfo> {

    public MatchPlayerInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            if(jsonElement.getAsString().equals("")){
                return null;
            }
        } catch (Exception e){

        }
        try {
            return new Gson().fromJson(jsonElement, MatchPlayerInfo.class);
        } catch (NumberFormatException e){
            throw new JsonSyntaxException(e);
        }
    }

    public JsonElement serialize(MatchPlayerInfo matchPlayerInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        Class<?> clz = matchPlayerInfo.getClass();
        Field[] fields = clz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            try {
                jsonObject.addProperty(field.getName(), (String)field.get(matchPlayerInfo));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
