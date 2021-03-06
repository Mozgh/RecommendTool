package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.util.Cluster;

import java.util.List;
import java.util.Map;

/**
 * Created by feir4 on 2018/4/29.
 */
public interface IClusterTaskSV {

    /**
     * create a task
     * @author zhanggh
     * @param task task with it's information
     * @return new task's ID
     */
    public Integer createTask(ClusterTask task) throws Exception;

    /**
     * 修改任务
     * @author zhanggh
     */
    public int updateTask(ClusterTask task);

    /**
     * delete a task by taskId
     * @author zhanggh
     * @param taskId task's Id
     * @return del count
     */
    public int deleteTask(Integer taskId);

    public ClusterTask queryTaskById(Integer taskId);

    public List<ClusterTask> queryTaskByUserId(Integer userId);
    /**
     * excuse a task by id
     * @author zhanggh
     * @param taskId task's id
     */
    public void excuseTask(Integer taskId);

    public Integer openTask(Integer taskId);

    public Integer closeTask(Integer taskId);
}
