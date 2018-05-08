package com.recommend.operation.core.dao.mongo.impl;

import com.mongodb.WriteResult;
import com.recommend.operation.core.dao.model.SysUserExample;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
    public int updateEntity(ClusterEntityBean entity) {
        Update update = new Update();
        update.addToSet("isCenter", entity.getIsCenter()).addToSet("dissimilarity", entity.getDissimilarity()).addToSet("centerId", entity.getCenterId());
        WriteResult result = mongoTemplate.updateFirst(new Query(Criteria.where("id").is(entity.getId())), update, entity.getClass());
        return result.getN();
    }

    @Override
    public ClusterEntityBean queryEntity(String id) {
        ClusterEntityBean entity;

        if (StringUtils.isEmpty(id)) {
            logger.debug("param could not be null or empty!");
        }
        entity = mongoTemplate.findById(id, ClusterEntityBean.class, "clusterEntity");

        if (null != entity) {
            return entity;
        } else {
            return null;
        }
    }
}
