package com.recommend.operation.core.service.business.interfaces;

/**
 * @author zhanggh
 */
public interface IKMeansSV {

    /**
     * 设置聚类中心数
     * @author zhanggh
     */
    public void setCenterCount();

    /**
     * 计算距离
     * @author zhanggh
     */
    public void calcDistence();

    /**
     * 计算距离/相异度
     */
    public void pointsCenter();

    /**
     * 修改聚类中心
     * @author zhanggh
     */
    public void updateCenter();

    /**
     * 将各点分配到各聚类中心
     * @author zhanggh
     */
    public void assignPoints();

    /**
     * 初始化聚类中心
     * @author zhanggh
     */
    public void initCenters();
}
