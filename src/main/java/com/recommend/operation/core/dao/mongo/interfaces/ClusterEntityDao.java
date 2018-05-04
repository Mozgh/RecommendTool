package com.recommend.operation.core.dao.mongo.interfaces;

import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhanggh
 */
@Transactional(rollbackFor = {})
@Repository
public interface ClusterEntityDao {

    /**
     * insert a entity into mongodb database
     * @param entity Cluster entity
     * @return new record's id
     */
    public String insertEntity(ClusterEntityBean entity);

    public void deleteEntity();

    public void updateEntity();
}
