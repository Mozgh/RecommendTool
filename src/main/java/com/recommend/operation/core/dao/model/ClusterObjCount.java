package com.recommend.operation.core.dao.model;

import java.io.Serializable;

/**
 * Created by feir4 on 2018/6/1.
 */
public class ClusterObjCount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mongodbId;

    private Long count;

    public ClusterObjCount(String mongodbId, Long count) {
        this.mongodbId = mongodbId;
        this.count = count;
    }

    public String getMongodbId() {
        return mongodbId;
    }

    public void setMongodbId(String mongodbId) {
        this.mongodbId = mongodbId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
