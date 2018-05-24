package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterAttrMapper;
import com.recommend.operation.core.dao.interfaces.ClusterObjMapper;
import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by feir4 on 2018/4/29.
 */
@Service
public class ClusterServiceImpl implements IClusterSV {

    @Resource
    private ClusterTaskMapper taskMapper;

    @Resource
    private ClusterAttrMapper attrMapper;

    @Resource
    private ClusterObjMapper objMapper;

    @Autowired
    private ClusterEntityDao entityDao;

    private Logger logger = Logger.getLogger(ClusterServiceImpl.class);

    @Override
    public Integer createTask(ClusterTask task) throws Exception{
        Integer newId = null;
        if(null != task) {
            newId = taskMapper.insert(task);
        }
        return newId;
    }

    @Override
    public int updateTask(ClusterTask task) {
        if (null == task) {
            return 0;
        }
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public int deleteTask(Integer taskId) {
        if (null == taskId) {
            return 0;
        }
        ClusterTask task = new ClusterTask();
        task.setId(taskId);
        task.setDelFlag(1);
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public ClusterTask queryTaskById(Integer taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public void excuseTask(Integer taskId) {

    }

    @Override
    public int importAttribute(List<ClusterAttr> attrList) throws Exception{
        int insertCount = 0;
        if (CollectionUtils.isEmpty(attrList)) {
            return 0;
        }
        for (ClusterAttr attr : attrList) {
            Integer newAttrId = attrMapper.insert(attr);
            if (null != newAttrId) {
                logger.info("insert new Cluster Attribute : " + newAttrId);
                insertCount ++;
            } else {
                logger.error("Cluster Attribute insert failed : " + attr.getName());
            }
        }
        return insertCount;
    }

    @Override
    public int importEntity(List<ClusterEntityBean> entityList) {
        int importCount = 0;

        for (ClusterEntityBean entity : entityList) {
            ClusterObj obj = new ClusterObj();
            int insert = 0;
            try {
                //insert into mongodb
               String mongoId =  entityDao.insertEntity(entity);
               if (!StringUtils.isEmpty(mongoId)) {
                   obj.setMongoId(mongoId);
               }
            } catch(Exception e) {
                logger.error("Entity info insert into mongodb failed Name:" + entity.getName());
                logger.error(e.getMessage());
            }

            try {
                //insert into mysql
                obj.setCenterId(entity.getCenterId());
                obj.setIsCenter(entity.getIsCenter());
                obj.setName(entity.getName());
                obj.setTaskId(entity.getTaskId());

                insert = objMapper.insert(obj);
            } catch(Exception e) {
                logger.error("Entity info insert into mysql failed MongoID:" + obj.getId());
                e.printStackTrace();
            }
            importCount += insert;
        }
        logger.info("import entity count: " + importCount);

        return importCount;
    }

    @Override
    public void getRecommendResult() {

    }
}
