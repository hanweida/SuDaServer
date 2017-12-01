package com.suda.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ES-BF-IT-126 on 2017/11/30.
 */
public class PattenUtil {
    public static void main(String[] args) {
        String regex = "player\\('(.*?)','(.*?)'\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("player('hello','say')");


        Pattern pattern2 = Pattern.compile("player((.+?))");
        Matcher matcher2 = pattern2.matcher("player('jrsnba','4')");

        if(matcher.find())
            System.out.println(matcher.group(1));
        }
    }
