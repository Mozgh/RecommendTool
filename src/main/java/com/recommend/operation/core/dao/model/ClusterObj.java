package com.recommend.operation.core.dao.model;

public class ClusterObj {
    private Integer id;

    private String mongoId;

    private Integer taskId;

    private String name;

    private Integer isCenter;

    private Integer centerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
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

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }
}