package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;

import java.util.List;

/**
 * Created by feir4 on 2018/4/29.
 */
public interface IClusterSV {

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

    /**
     * excuse a task by id
     * @author zhanggh
     * @param taskId task's id
     */
    public void excuseTask(Integer taskId);

    /**
     * 导入参考属性
     * @author zhanggh
     * @param attrList ArrayList of Cluster Attribute
     * @return import records count
     */
    public int importAttribute(List<ClusterAttr> attrList) throws Exception;

    /**
     * create entity batch
     * @param entityList List of Cluster Entity
     * @return entity count imported
     * @author zhanggh
     */
    public int importEntity(List<ClusterEntityBean> entityList);

    /**
     * 获取推荐结果，根据传入ID获取推荐结果
     * @author zhanggh
     */
    public void getRecommendResult();
}
