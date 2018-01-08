package com.suda.http.utils;

import com.suda.http.constant.Constants;
import com.suda.utils.safe.MD5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public class RequestHelper {
    public static HashMap<String, String> getRequestMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("client", Constants.deviceId);
        map.put("night", "0");
        return map;
    }

    public static HashMap<String, String> getRequestNewsMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crt", "1512110779367");
        map.put("client", "990009265068844");
        map.put("night", "0");
        map.put("channel", "hupucom");
        map.put("cate_type", "news");
        map.put("nopic", "0");
        map.put("ft", "18");
        map.put("top_ncid", "-1");
        map.put("replies", "7");
        map.put("entrance", "1");
        map.put("android_id", "19341a43dbd6eb60");
        map.put("sign", "860772258ca733974bd1a989f8969bc0");
        map.put("time_zone", "Asia/Shanghai");
        return map;
    }

    /**
     * 虎扑url sign生成
     *
     * @param map url参数，按字典序拼接key和value
     * @return sign值
     */
    public static String getRequestSign(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> list = new ArrayList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() { // 字典序
            @Override
            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i = i + 1) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            Map.Entry<String, String> map1 = list.get(i);
            builder.append(map1.getKey()).append("=").append(map1.getValue());
        }
        builder.append("HUPU_SALT_AKJfoiwer394Jeiow4u309");
        return MD5.getMD5(builder.toString());
    }
}
