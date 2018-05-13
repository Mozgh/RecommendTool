package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.SysUserMapper;
import com.recommend.operation.core.dao.model.SysUser;
import com.recommend.operation.core.service.business.interfaces.IUserSV;
import com.recommend.web.response.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by feir4 on 2018/5/13.
 */
@Service
public class UserServiceImpl implements IUserSV {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private SysUserMapper userMapper;

    @Override
    public BaseResponse login(Integer userId, String password) {
        BaseResponse result = new BaseResponse();
        try {
            SysUser user = userMapper.selectByPrimaryKey(userId);

            if (null != user) {
                if (StringUtils.isEmpty(user.getPassword()) && password.equals(user.getPassword())) {
                    result.success();
                } else {
                    result.fail();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.fail("查询出错");
        }
        return result;
    }
}
