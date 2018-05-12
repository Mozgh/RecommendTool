package com.recommend.operation.core.dao.mongo.impl;

import com.github.pagehelper.util.StringUtil;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public ClusterEntityBean queryEntityById(String id) {
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

    @Override
    public List<ClusterEntityBean> queryClusterCenterList(String taskId) throws Exception {
        List<ClusterEntityBean> result = null;
        if (StringUtils.isEmpty(taskId)) {
            logger.error("param taskId can not be empty!");
            return null;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("taskId").is(taskId).and("isCenter").is(1));
        result = mongoTemplate.find(query, ClusterEntityBean.class);

        if (CollectionUtils.isEmpty(result)) {
            logger.debug("task " + taskId + " don't have clusters!");
            return null;
        }
        return result;
    }

    @Override
    public List<ClusterEntityBean> queryEntityListByCenter(String centerId) throws Exception {
        List<ClusterEntityBean> result = null;
        if (StringUtils.isEmpty(centerId)) {
            logger.error("param centerId can not empty!");
            return null;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("centerID").is(centerId));
        result = mongoTemplate.find(query, ClusterEntityBean.class);

        if (CollectionUtils.isEmpty(result)) {
            logger.debug("cluster is empty -- centerId = " + centerId);
            return null;
        } else {
            return result;
        }
    }
}
