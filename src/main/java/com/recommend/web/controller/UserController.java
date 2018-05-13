package com.recommend.web.controller;

import com.recommend.operation.core.service.business.interfaces.IUserSV;
import com.recommend.web.response.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by feir4 on 2018/5/13.
 */
@RequestMapping("/")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    IUserSV userSV;

    @RequestMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestParam(value = "userId", required = true)Integer userId,
                              @RequestParam(value = "password", required = true)String password) {
        BaseResponse response = new BaseResponse();
        response = userSV.login(userId, password);

        if (response.getSuccess()) {
            logger.info("登录成功 userId:" + userId);
        }
        return response;
    }
}
