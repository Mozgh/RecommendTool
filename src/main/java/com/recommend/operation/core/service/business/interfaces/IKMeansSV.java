package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.util.Cluster;

import java.util.List;

/**
 * @author zhanggh
 */
public interface IKMeansSV {

    /**
     * init cluster centers
     * @param count count of cluster
     * @author zhanggh
     */
    public void initCenter(Integer count);

    /**
     * 计算距离
     * @author zhanggh
     */
    public double calcDistence(ClusterEntityBean entity1, ClusterEntityBean entity2, List<ClusterAttr> attrList);

    /**
     * 修改聚类中心
     * @author zhanggh
     */
    public boolean updateCenter(Cluster cluster);

    /**
     * 将各点分配到各聚类中心
     * @author zhanggh
     */
    public void assignPoints();

    /**
     * execute k-means
     * @param task cluster task
     * @author zhanggh
     */
    public void execute(ClusterTask task);
}
