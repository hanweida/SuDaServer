package com.suda.pojo;

/**
 * Created by ES-BF-IT-126 on 2017/11/30.
 */
public class MatchUrl {
    private String player;
    private String vid;
    private String name;
    //
    public boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MatchUrl{" +
                "player='" + player + '\'' +
                ", vid='" + vid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
