package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import com.recommend.operation.core.util.Cluster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zhanggh
 */
@Service
public class KMeansServiceImpl implements IKMeansSV {

    private Logger logger = Logger.getLogger(KMeansServiceImpl.class);

    private Map<String, Cluster> clusters = new HashMap<>();

    private Map<String, ClusterEntityBean> entityMap = new HashMap<>();

    @Autowired
    ClusterEntityDao clusterEntityDao;


    private boolean loadTaskData(Integer taskId) {
        if (null == taskId) {
            logger.error("task id can not be null!");
        }

        try {
            List<ClusterEntityBean> entityBeanList = clusterEntityDao.queryEntityListByTaskId(taskId);
            for (ClusterEntityBean entity: entityBeanList) {
                entityMap.put(entity.getId(), entity);
            }
            logger.info("entity data load success, count:" + entityMap.size());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void initCenter(Integer count) {
        logger.info("[INIT] init cluster center : count = " + count);
        for (int i = 0; i < count; i++) {
            int index = entityMap.size() / count * (i + 1);
            String key = (String) entityMap.keySet().toArray()[index];
            ClusterEntityBean entity = entityMap.get(key);
            Map<String, Double> entityMap = new HashMap<>();
            entityMap.put(entity.getId(), 0D);
            entity.setCenterId(entity.getId());
            entity.setIsCenter(1);
            Cluster cluster = new Cluster();
            cluster.setCenterEntity(entity);
            cluster.setEntityMap(entityMap);

            clusters.put(entity.getId(), cluster);
            logger.info("cluster[" + (i + 1) + "] centerId: " + entity.getId());
        }
        logger.info("[INIT] init centers end");
    }

    @Override
    public double calcDistence(ClusterEntityBean entity, ClusterEntityBean base) {
        double distance = 0F;
        double entityMod = 0F;
        double baseMod = 0F;
        double crossProduct = 0F;
        double entityValue = 0F;
        double baseValue = 0F;

        Map<String, Object> entityAttrValue = entity.getAttrValue();
        Map<String, Object> baseAttrValue = base.getAttrValue();

        Set<String> entityAttrSet = entityAttrValue.keySet();
        for (String key: entityAttrSet) {
            entityValue = Double.parseDouble(String.valueOf(entityAttrValue.get(key)));
            if (baseAttrValue.containsKey(key)) {
                baseValue = Double.parseDouble(String.valueOf(baseAttrValue.get(key)));
                crossProduct += entityValue * baseValue;
            }
            entityMod += entityValue * entityValue;
        }

        List<Object> baseAttrValueList = new ArrayList<>(baseAttrValue.values());
        for (Object value : baseAttrValueList) {
            baseMod += Double.parseDouble(String.valueOf(value)) * Double.parseDouble(String.valueOf(value));
        }

        entityMod = Math.sqrt(entityMod);
        baseMod = Math.sqrt(entityMod);
        distance = crossProduct / (baseMod * entityMod);
        return distance;
    }

    @Override
    public boolean updateCenter(Cluster cluster) {
        logger.info("[update] update clusters' center begin.");
        logger.info("cluster center current: " + cluster.getCenterEntity().getId());
        Double minDistance = null;
        String nextCenterId = null;

        //计算下一中心点到本中心点的距离
        Double nextCenterDistance = 0D;

        for (Double distance: cluster.getEntityMap().values()) {
            nextCenterDistance += distance;
        }
        nextCenterDistance = nextCenterDistance / cluster.getEntityMap().size();

        //寻找与本中心点距离和nextCenterDistance最接近的点
        Set<String> idSet = cluster.getEntityMap().keySet();
        for (String id : idSet) {
            Double distance = Math.abs(cluster.getEntityMap().get(id) - nextCenterDistance);
            if (null == minDistance || distance < minDistance) {
                minDistance = distance;
                nextCenterId = id;
            }
        }
        //最接近的点即为下一个聚类中心，修改聚类的中心实体，entityMap清空，等待下次分配
        if (StringUtils.isEmpty(nextCenterId)) {
            logger.error("error!");
            return false;
        } else {
            //如果计算得到的下一个聚类中心和当前中心相同，说明已不能再修改，返回false
            if (nextCenterId.equals(cluster.getCenterEntity().getCenterId())) {
                logger.info("final center: " + nextCenterId);
                return false;
            } else {
                //如果聚类中心发生了变换，返回true
                clusters.remove(cluster.getCenterEntity().getId());
                entityMap.get(cluster.getCenterEntity().getId()).setCenterId(nextCenterId);
                entityMap.get(cluster.getCenterEntity().getId()).setIsCenter(0);
                entityMap.get(nextCenterId).setIsCenter(1);
                entityMap.get(nextCenterId).setCenterId(nextCenterId);
                cluster.setCenterEntity(entityMap.get(nextCenterId));
//                cluster.getEntityMap().clear();
                clusters.put(cluster.getCenterEntity().getId(), cluster);
                logger.info("new center: " + nextCenterId);
                return true;
            }
        }
    }

    @Override
    public void assignPoints() {

        logger.info("[ASSIGN] assign entities start");

        Set<String> entityIdIter = entityMap.keySet();

        //分配结点时清空之前的分配结果
        for(Cluster cluster: clusters.values()) {
            cluster.getEntityMap().clear();
        }

        for (String entityId : entityIdIter) {
            ClusterEntityBean entity = entityMap.get(entityId);
            Double distance = null;
            String centerId = null;

            for (Cluster cluster : new ArrayList<>(clusters.values())) {
                Double newDistance = calcDistence(entity, cluster.getCenterEntity());
                if (null == distance || newDistance < distance) {
                    distance = newDistance;
                    centerId = cluster.getCenterEntity().getId();
                }
            }

            entity.setCenterId(centerId);
            entity.setDissimilarity(distance);
            clusters.get(centerId).getEntityMap().put(entity.getId(), distance);

            logger.info("assign entity(" + entity.getId() + ") to cluster(center:" + centerId + ")");
        }

        logger.info("[ASSIGN] assign entities end");
    }

    @Override
    public void execute(ClusterTask task) {
        if (null == task) {
            logger.error("task can not be null!");
            return;
        }
        boolean loaded = this.loadTaskData(task.getId());
        if (!loaded) {
            logger.error("load task data error!");
            return;
        }
        this.initCenter(task.getCenter());
        boolean notFinish = true;
        while (notFinish) {
            this.assignPoints();
            //当所有聚类更新中心都返回false时，notFinish为false
            boolean update = false;
            for (Cluster cluster : new ArrayList<>(clusters.values())) {
                update = update | this.updateCenter(cluster);
            }
            notFinish = update;
        }
        this.syncEntityData();
    }

    public int syncEntityData() {
        logger.info("[sync] sync entity data begin.");
        int updateCount = 0;

        Set<String> entityIdSet = entityMap.keySet();
        for (String entityId : entityIdSet) {
            try {
                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("_id", entityId);
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("isCenter", entityMap.get(entityId).getIsCenter());
                updateMap.put("centerId", entityMap.get(entityId).getCenterId());
                updateCount += clusterEntityDao.updateEntity(queryMap, updateMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("[sync] sync data end : " + updateCount + "/" + entityMap.size());
        return updateCount;
    }

    public Map<String, ClusterEntityBean> getEntityList() {
        return entityMap;
    }

    public void setEntityList(Map<String, ClusterEntityBean> entityList) {
        this.entityMap = entityList;
    }

}
