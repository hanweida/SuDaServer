package com.suda.pojo;

import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/11/28.
 */
public class MatchInfo {
    public String match_time;
    public String match_name;
    public String home_team;
    public String guest_team;
    public String home_logo_url;
    public String guest_logo_url;
    public String match_url;
    public String base_url;
    public Integer matchLiveSource; //比赛直播源
    public Map<String, String> sourcePlayer;

    public Integer getMatchLiveSource() {
        return matchLiveSource;
    }

    public void setMatchLiveSource(Integer matchLiveSource) {
        this.matchLiveSource = matchLiveSource;
    }

    public Map<String, String> getSourcePlayer() {
        return sourcePlayer;
    }

    public void setSourcePlayer(Map<String, String> sourcePlayer) {
        this.sourcePlayer = sourcePlayer;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getMatch_url() {
        return match_url;
    }

    public void setMatch_url(String match_url) {
        this.match_url = match_url;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getGuest_team() {
        return guest_team;
    }

    public void setGuest_team(String guest_team) {
        this.guest_team = guest_team;
    }

    public String getHome_logo_url() {
        return home_logo_url;
    }

    public void setHome_logo_url(String home_logo_url) {
        this.home_logo_url = home_logo_url;
    }

    public String getGuest_logo_url() {
        return guest_logo_url;
    }

    public void setGuest_logo_url(String guest_logo_url) {
        this.guest_logo_url = guest_logo_url;
    }

    public MatchInfo() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "match_time='" + match_time + '\'' +
                ", match_name='" + match_name + '\'' +
                ", home_team='" + home_team + '\'' +
                ", guest_team='" + guest_team + '\'' +
                ", home_logo_url='" + home_logo_url + '\'' +
                ", guest_logo_url='" + guest_logo_url + '\'' +
                ", match_url='" + match_url + '\'' +
                ", base_url='" + base_url + '\'' +
                ", sourcePlayer=" + sourcePlayer +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
