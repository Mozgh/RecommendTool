package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterTask;

/**
 * Created by feir4 on 2018/4/29.
 */
public interface IClusterSV {

    /**
     * 新建任务
     * @author zhanggh
     */
    public Integer createTask(ClusterTask task) throws Exception;

    /**
     * 修改任务
     * @author zhanggh
     */
    public void updateTask(ClusterTask task);

    /**
     * 删除任务
     * @author zhanggh
     */
    public void deleteTask(Integer taskId);

    /**
     * 执行任务计算过程
     * @author zhanggh
     */
    public void excuseTask(Integer taskId);

    /**
     * 导入参考属性
     */
    public void importAttribute();

    /**
     * 新增实例
     * @author zhanggh
     */
    public void createObject();

    /**
     * 获取推荐结果，根据传入ID获取推荐结果
     * @author zhanggh
     */
    public void getRecommendResult();
}
