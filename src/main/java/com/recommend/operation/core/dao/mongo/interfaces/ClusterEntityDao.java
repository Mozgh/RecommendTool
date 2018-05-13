package com.recommend.operation.core.dao.mongo.interfaces;

import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhanggh
 */
@Transactional(rollbackFor = {})
public interface ClusterEntityDao {

    /**
     * insert a entity into mongodb database
     * @param entity Cluster entity
     * @return new record's id
     * @author zhanggh
     */
    public String insertEntity(ClusterEntityBean entity);

    public void deleteEntity();

    public int updateEntity(ClusterEntityBean entity);

    /**
     * query a Cluster Entity by it's id.
     * @param id mongodb id
     * @return cluster entity bean
     * @author zhanggh
     */
    public ClusterEntityBean queryEntityById(String id);

    /**
     * query a List of Entity which is clusters' center by its' taskId
     * @param taskId task id
     * @return ClusterEntityBean list
     * @throws Exception
     * @author zhanggh
     */
    public List<ClusterEntityBean> queryClusterCenterList(String taskId) throws Exception;

    /**
     * query a List of Entity in a same cluster by center id
     * @param centerId clusterCenterId
     * @return ClusterEntityBean list
     * @author zhanggh
     */
    public List<ClusterEntityBean> queryEntityListByCenter(String centerId) throws Exception;

    public List<ClusterEntityBean> queryEntityListByTaskId(Integer taskId) throws Exception;

}
