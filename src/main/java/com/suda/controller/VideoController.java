package com.suda.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ES-BF-IT-126 on 2017/11/10.
 */
@Controller
@RequestMapping(value="/video")
public class VideoController {
    @RequestMapping(value = "/geturl", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("invoke");
        return new ModelAndView("/biz/hello");
    }

    @RequestMapping(value = "/gamelist", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getGameList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("invoke");
        return new ModelAndView("/biz/hello");
    }
}
