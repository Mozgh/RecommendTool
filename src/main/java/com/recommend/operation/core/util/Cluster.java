package com.recommend.operation.core.util;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggh
 */
public class Cluster {

    /**
     * key-value Map of entities in this cluster
     * key-entityId, value-distance from last center
     */
    private Map<String, Double> entity;

    private ClusterEntityBean centerEntity;

    private Map<String, Double> attrValueMap;

    public Map<String, Double> getEntityId() {
        return entity;
    }

    public void setEntityId(Map<String, Double> entity) {
        this.entity = entity;
    }

    public ClusterEntityBean getCenterEntity() {
        return centerEntity;
    }

    public void setCenterEntity(ClusterEntityBean centerEntity) {
        this.centerEntity = centerEntity;
    }

    public Map<String, Double> getAttrValueMap() {
        return attrValueMap;
    }

    public void setAttrValueMap(Map<String, Double> attrValueMap) {
        this.attrValueMap = attrValueMap;
    }
}
