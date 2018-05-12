package com.recommend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feir4 on 2018/5/12.
 */
public class ApplicationController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    protected ModelAndView buildMAV(String page) {
        return buildMAV(page, "index");
    }

    public ModelAndView buildMAV(String page, String layout) {
        ModelAndView mav = new ModelAndView(layout);
        mav.addObject("view", page);
        Map<String, String> requestContext = new HashMap<>();
        requestContext.put("contextPath", request.getContextPath());
        mav.addObject("rc", requestContext);
        mav.addObject("contextPath", request.getContextPath());
        return mav;
    }
}
