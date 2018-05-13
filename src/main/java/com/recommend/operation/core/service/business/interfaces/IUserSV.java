package com.recommend.operation.core.service.business.interfaces;

import com.recommend.web.response.BaseResponse;

/**
 * Created by feir4 on 2018/5/13.
 */
public interface IUserSV {

    public BaseResponse login(Integer userId, String password);

}
