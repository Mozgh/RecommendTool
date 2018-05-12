package com.recommend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhanggh
 */

@Controller
public class IndexController extends ApplicationController{
    @RequestMapping("/")
    public ModelAndView index() {
        return buildMAV("index.jsp");
    }
}
