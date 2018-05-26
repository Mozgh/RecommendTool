package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterObjMapper;
import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IClusterObjectSV;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggh
 */
@Service
public class ClusterObjectServiceImpl implements IClusterObjectSV {
    private static final Logger logger = Logger.getLogger(ClusterObjectServiceImpl.class);

    @Resource
    private ClusterObjMapper objMapper;

    @Autowired
    private ClusterEntityDao entityDao;

    @Override
    public int importEntity(List<ClusterEntityBean> entityList) {
        int importCount = 0;

        for (ClusterEntityBean entity : entityList) {
            ClusterObj obj = new ClusterObj();
            int insert = 0;
            try {
                //insert into mongodb
                String mongoId =  entityDao.insertEntity(entity);
                logger.info("insert into mongodb successed, mongodbId:" + mongoId);
                if (!StringUtils.isEmpty(mongoId)) {
                    obj.setMongoId(mongoId);
                }
            } catch(Exception e) {
                logger.error("Entity info insert into mongodb failed Name:" + entity.getCode());
                logger.error(e.getMessage());
            }

            try {
                //insert into mysql
                obj.setCenterId(entity.getCenterId());
                obj.setIsCenter(entity.getIsCenter());
                obj.setCode(entity.getCode());
                obj.setTaskId(entity.getTaskId());

                insert = objMapper.insert(obj);
                logger.info("insert into mysql successed, id:" + obj.getId());
            } catch(Exception e) {
                logger.error("Entity info insert into mysql failed MongoID:" + obj.getId());
                e.printStackTrace();
            }
            importCount += insert;

            logger.info("[insert] " + entityList.indexOf(entity) + " / " + entityList.size());
        }
        logger.info("import entity count: " + importCount);

        return importCount;
    }

    @Override
    public List<Object> getRecommendResult(String entityCode, String attrCode) throws Exception{
        if (StringUtils.isEmpty(entityCode) || StringUtils.isEmpty(attrCode)) {
            logger.error("entityCode and attrCode can not be empty");
            return null;
        }
        List<Object> result = new ArrayList<>();

        List<ClusterEntityBean> entityList = entityDao.queryEntityListByCode(entityCode);
        if (CollectionUtils.isEmpty(entityList)) {
            logger.info("can not find entity with code :" + entityCode);
            return null;
        }
        for (ClusterEntityBean entity: entityList) {
            String centerId = entity.getCenterId();
            List<ClusterEntityBean> entityWithSameCenter = entityDao.queryEntityListByCenter(centerId);
            if (CollectionUtils.isEmpty(entityWithSameCenter)) {
                logger.info("no entity in this center: " + centerId);
                return null;
            }
            for (ClusterEntityBean bean: entityWithSameCenter) {
                Object attrValue = bean.getAttrValue().get(attrCode);
                if (!result.contains(attrValue)) {
                    result.add(attrValue);
                }
            }

        }
        return result;
    }
}
