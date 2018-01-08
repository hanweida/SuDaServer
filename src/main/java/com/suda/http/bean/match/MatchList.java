package com.suda.http.bean.match;

import com.suda.http.bean.Base;

import java.io.Serializable;
import java.util.List;

/**
 * 比赛列表
 * Created by ES-BF-IT-126 on 2017/12/14.
 */
public class MatchList extends Base {
    public Matches data;


    public static class Matches implements Serializable{
        public List<MatchStats> matches;
        public String updateFrequency;
        public String today;

        public static class MatchStats implements Serializable{
            public String updateFrequency;
            public MatchInfo matchInfo;

            public static class MatchInfo implements Serializable{
                public String matchType;
                public String mid;
                public String leftId;
                public String leftName;
                public String leftBadge;
                public String leftGoal;
                public String leftHasUrl;
                public String leftDetailUrl;
                public String simpleLeftBadge;
                public String rightId;
                public String rightName;
                public String rightBadge;
                public String rightGoal;
                public String rightDetailUrl;
                public String rightHasUrl;
                public String simpleRightBadge;
                public String matchDesc;
                public String startTime;
                public String endTime;
                public String title;
                public String logo;
                public String matchPeriod;
                public String quarter;
                public String quarterTime;
                public String liveType;
            }
        }
    }
}
