package com.recommend.operation.core.util;

import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;

import java.util.Map;

/**
 * @author zhanggh
 */
public class Cluster {

    /**
     * key-value Map of entities in this cluster
     * key-entityId, value-distance from last center
     */
    private Map<String, Double> entityMap;

    private ClusterEntityBean centerEntity;


    public Map<String, Double> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<String, Double> entityMap) {
        this.entityMap = entityMap;
    }

    public ClusterEntityBean getCenterEntity() {
        return centerEntity;
    }

    public void setCenterEntity(ClusterEntityBean centerEntity) {
        this.centerEntity = centerEntity;
    }
}
