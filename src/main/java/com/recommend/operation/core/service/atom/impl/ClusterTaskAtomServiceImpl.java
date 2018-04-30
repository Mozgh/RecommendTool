package com.recommend.operation.core.service.atom.impl;

import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.service.atom.interfaces.IClusterTaskAtomSV;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhanggh
 */
@Service
public class ClusterTaskAtomServiceImpl implements IClusterTaskAtomSV {

    @Resource
    ClusterTaskMapper taskMapper;

    Logger logger = Logger.getLogger(ClusterTaskAtomServiceImpl.class);
    @Override
    public int updateTaskState(Integer taskId, Integer state) {
        if (null == taskId) {
            logger.info("参数不能为空");
            return 0;
        }
        int updateCount;
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setState(state);
        updateCount = taskMapper.updateByPrimaryKeySelective(task);
        return updateCount;
    }
}
