package com.recommend.operation.core.dao.mongo.impl;

import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zhanggh
 */

@Component
@Repository
public class ClusterEntityDaoImpl implements ClusterEntityDao{

    private Logger logger = Logger.getLogger(ClusterEntityDaoImpl.class);

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public String insertEntity(ClusterEntityBean entity) {



        mongoTemplate.insert(entity, "clusterEntity");

        return entity.getId();
    }

    @Override
    public void deleteEntity() {

    }

    @Override
    public void updateEntity() {

    }
}
