package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterAttrMapper;
import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by feir4 on 2018/4/29.
 */
@Service
public class ClusterServiceImpl implements IClusterSV {

    @Resource
    private ClusterTaskMapper taskMapper;

    @Resource
    private ClusterAttrMapper attrMapper;

    private Logger logger = Logger.getLogger(ClusterServiceImpl.class);

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
            return 0;
        }
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setDelFlag(1);
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public void excuseTask(Integer taskId) {

    }

    @Override
    public int importAttribute(List<ClusterAttr> attrList) throws Exception{
        int insertCount = 0;
        if (CollectionUtils.isEmpty(attrList)) {
            return 0;
        }
        for (ClusterAttr attr : attrList) {
            Integer newAttrId = attrMapper.insert(attr);
            if (null != newAttrId) {
                logger.info("insert new Cluster Attribute : " + newAttrId);
                insertCount ++;
            } else {
                logger.error("Cluster Attribute insert failed : " + attr.getName());
            }
        }
        return insertCount;
    }

    @Override
    public void createObject() {

    }

    @Override
    public void getRecommendResult() {

    }
}
