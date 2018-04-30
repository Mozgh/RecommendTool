package com.recommend.operation.core.service.business.interfaces;

/**
 * Created by feir4 on 2018/4/29.
 */
public interface IClusterSV {

    /**
     * 新建任务
     * @author zhanggh
     */
    public void createTask();

    /**
     * 修改任务
     * @author zhanggh
     */
    public void updateTask();

    /**
     * 删除任务
     * @author zhanggh
     */
    public void deleteTask();

    /**
     * 执行任务计算过程
     * @author zhanggh
     */
    public void excuseTask();

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
     */
    public void getRecommendResult();
}
