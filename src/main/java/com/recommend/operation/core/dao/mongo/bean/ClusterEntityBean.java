package com.recommend.operation.core.dao.mongo.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author zhanggh
 */
@Document(collection = "entity")
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
    private int isCenter;

    /**
     * the center id which this entity belong to
     */
    private String centerId;

    /**
     * a map of attributes' code and value
     * key--attribute's code
     * value--attribute's value
     */
    private Map<String, String> attrValue;

    /**
     * a map of attributes' code and type
     * key--attribute's code
     * value--attribute's type 1--float 2--boolean 3--string
     */
    private Map<String, String> attrType;

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

    public int getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(int isCenter) {
        this.isCenter = isCenter;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public Map<String, String> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(Map<String, String> attrValue) {
        this.attrValue = attrValue;
    }

    public Map<String, String> getAttrType() {
        return attrType;
    }

    public void setAttrType(Map<String, String> attrType) {
        this.attrType = attrType;
    }
}
