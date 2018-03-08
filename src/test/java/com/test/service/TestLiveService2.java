package com.test.service;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonObject;
import com.suda.pojo.MatchInfo;
import com.suda.service.LiveService;
import com.suda.web.ContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ES-BF-IT-126 on 2018/2/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:sys/spring-base.xml"})
public class TestLiveService2 {

    @Autowired
    protected ApplicationContext ctx;

    @Autowired
    private  LiveService liveServiceImpl;
    @Autowired
    private Environment env;

    @Test
    public void testLiveService(){
        //ystem.out.println(new PropertiesUtil().getProperties("kuwan_url"));
        //System.out.println(env.getProperty("kuwan_url"));
        ContextHolder.getInstance().setApplicationContext(ctx);
        ContextHolder.getInstance().setLocal(Locale.getDefault());
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        JSONArray jsonArray = liveServiceImpl.getMatchInfo(dateList);
        System.out.println(jsonArray.toString());
    }

    @Test
    public void testLiveService2(){
        //ystem.out.println(new PropertiesUtil().getProperties("kuwan_url"));
        //System.out.println(env.getProperty("kuwan_url"));
        ContextHolder.getInstance().setApplicationContext(ctx);
        ContextHolder.getInstance().setLocal(Locale.getDefault());
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        MatchInfo matchInfo = liveServiceImpl.getMatchSource("http://m.didiaokan.com//a/100209800?classid=1&id=4752");
        System.out.println(matchInfo.toString());
    }

    @Test
    public void testLiveService3(){
        //ystem.out.println(new PropertiesUtil().getProperties("kuwan_url"));
        //System.out.println(env.getProperty("kuwan_url"));
        ContextHolder.getInstance().setApplicationContext(ctx);
        ContextHolder.getInstance().setLocal(Locale.getDefault());
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        liveServiceImpl.getMatchState("100000:1471289", "3", "爵士", "步行者");
    }
}
