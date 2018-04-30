package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by feir4 on 2018/4/29.
 */
@Service
public class ClusterServiceImpl implements IClusterSV {

    @Resource
    ClusterTaskMapper taskMapper;
    @Override
    public Integer createTask(ClusterTask task) throws Exception{
        Integer newId = null;
        if(null != task) {
            newId = taskMapper.insert(task);
        }
        return newId;
    }

    @Override
    public void updateTask(ClusterTask task) {

    }

    @Override
    public void deleteTask(Integer taskId) {

    }

    @Override
    public void excuseTask(Integer taskId) {

    }

    @Override
    public void importAttribute() {

    }

    @Override
    public void createObject() {

    }

    @Override
    public void getRecommendResult() {

    }
}
