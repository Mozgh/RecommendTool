package com.recommend.operation.core.dao.mongo.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author zhanggh
 */
@Document(collection = "clusterEntity")
public class ClusterEntityBean {

    /**
     * mongodb id
     */
    @Id
    private String id;

    /**
     * cluster task id
     */
    private int taskId;

    /**
     * entity name
     */
    private String name;

    /**
     * if this entity is a center
     * 1--is center
     * 0--is not
     */
    private Integer isCenter;

    /**
     * the center id which this entity belong to
     */
    private String centerId;

    /**
     * a map of attributes' code and value
     * key--attribute's code
     * value--attribute's value
     */
    private Map<String, Object> attrValue;

    /**
     * a map of attributes' code and type
     * key--attribute's code
     * value--attribute's type 1--double 2--boolean
     */
    private Map<String, String> attrType;

    /**
     * dissimilarity degree of this entity
     */
    private Double dissimilarity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(Integer isCenter) {
        this.isCenter = isCenter;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public Map<String, Object> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(Map<String, Object> attrValue) {
        this.attrValue = attrValue;
    }

    public Map<String, String> getAttrType() {
        return attrType;
    }

    public void setAttrType(Map<String, String> attrType) {
        this.attrType = attrType;
    }

    public Double getDissimilarity() {
        return dissimilarity;
    }

    public void setDissimilarity(Double dissimilarity) {
        this.dissimilarity = dissimilarity;
    }
}
