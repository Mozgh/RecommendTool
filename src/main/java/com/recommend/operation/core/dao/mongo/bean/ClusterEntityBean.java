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
    private Integer taskId;

    /**
     * entity code
     */
    private String code;

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
     * dissimilarity degree of this entity
     */
    private Double dissimilarity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Double getDissimilarity() {
        return dissimilarity;
    }

    public void setDissimilarity(Double dissimilarity) {
        this.dissimilarity = dissimilarity;
    }
}
