package com.recommend.operation.core.dao.mongo.interfaces;

import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public ClusterEntityBean queryEntity(String id);
}
