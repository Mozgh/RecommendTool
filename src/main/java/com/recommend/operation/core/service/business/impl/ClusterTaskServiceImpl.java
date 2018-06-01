package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterAttrMapper;
import com.recommend.operation.core.dao.interfaces.ClusterObjMapper;
import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterAttrExample;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.model.ClusterTaskExample;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IClusterTaskSV;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feir4 on 2018/4/29.
 */
@Service
public class ClusterTaskServiceImpl implements IClusterTaskSV {

    @Resource
    private ClusterTaskMapper taskMapper;

    @Autowired
    private IKMeansSV kmeansSV;

    private Logger logger = Logger.getLogger(ClusterTaskServiceImpl.class);

    @Override
    public Integer createTask(ClusterTask task) throws Exception{
        Integer newId = null;
        if(null != task) {
            newId = taskMapper.insert(task);
        }
        return newId;
    }

    @Override
    public int updateTask(ClusterTask task) {
        if (null == task) {
            return 0;
        }
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public int deleteTask(Integer taskId) {
        if (null == taskId) {
            logger.error("taskId can not be empty");
            return 0;
        }
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setDelFlag(1);
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public ClusterTask queryTaskById(Integer taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public List<ClusterTask> queryTaskByUserId(Integer userId) {
        List<ClusterTask> taskList = new ArrayList<>();
        if (null == userId) {
            logger.error("userId can not be empty");
        } else {
            ClusterTaskExample example = new ClusterTaskExample();
            example.createCriteria().andUserIdEqualTo(userId);
            taskList = taskMapper.selectByExample(example);
        }
        return taskList;
    }

    @Override
    public void excuseTask(Integer taskId) {
        ClusterTask task = new ClusterTask();
        task = this.queryTaskById(taskId);
        kmeansSV.execute(task);
    }

    @Override
    public Integer openTask(Integer taskId) {
        if (null == taskId) {
            logger.error("taskId can not be empty");
            return null;
        }
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setState(1);
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public Integer closeTask(Integer taskId) {
        if (null == taskId) {
            logger.error("taskId can not be empty");
            return null;
        }
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setState(2);
        return taskMapper.updateByPrimaryKeySelective(task);
    }
}
