package com.test.service;

import com.suda.service.LiveService;
import com.suda.service.impl.LiveServiceImpl;
import com.suda.web.ContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ES-BF-IT-126 on 2018/2/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:sys/spring-base.xml","classpath:sys/spring-mvc-biz.xml"})
public class TestLiveService {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private LiveService liveServiceImpl;

    @Test
    public void testLiveService(){
        ContextHolder.getInstance().setApplicationContext(wac);
        ContextHolder.getInstance().setLocal(Locale.getDefault());
        LiveService liveService = new LiveServiceImpl();
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date();
        dateList.add(date);
        liveService.getMatchInfo(dateList);
    }
}
