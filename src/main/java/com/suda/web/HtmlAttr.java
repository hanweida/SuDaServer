package com.suda.web;

/**
 * Created by ES-BF-IT-126 on 2017/11/28.
 */
public enum HtmlAttr {
    MATCH_TIME("match_time"),
    MATCH_NAME("match_name"),
    MATCH_INFO("match_info"),
    HOME_TEAM("home_team"),
    HOME_LOGO("home_logo"),
    GUEST_LOGO("guest_logo"),
    GUEST_TEAM("guest_team");

    String value;
    HtmlAttr(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
