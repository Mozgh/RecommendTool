package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IClusterObjectSV;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import com.recommend.operation.core.util.Cluster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    IClusterObjectSV objectSV;

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
    public Double calcDistence(ClusterEntityBean entity, ClusterEntityBean base) {
        Double distance = 0D;       //entity和base的余弦度量
        Double entityMod = 0D;      //entity的欧几里得范数
        Double baseMod = 0D;        //base的欧几里得范数
        Double crossProduct = 0D;   //entity和base的向量点积
        Double entityValue = 0D;    //存储entity计算时的中间值
        Double baseValue = 0D;      //存储base计算时的中间值

        Map<String, Object> entityAttrValue = entity.getAttrValue();    //存储entity的向量中的值
        Map<String, Object> baseAttrValue = base.getAttrValue();        //存储base向量中的值

        for (String key: entityAttrValue.keySet()) {
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
        if (!CollectionUtils.isEmpty(cluster.getEntityMap())) {
            for (Double distance : cluster.getEntityMap().values()) {
                nextCenterDistance += distance;
            }
            nextCenterDistance = nextCenterDistance / cluster.getEntityMap().size();
        }
        //判断计算出来的下一中心与当前中心的距离偏差值是否在设定范围内
        if (nextCenterDistance < 0.05) {
            logger.info("center do not need move. centerId: " + cluster.getCenterEntity().getId());
            return false;
        }
        //寻找与本中心点距离和nextCenterDistance最接近的点
        for (String id : cluster.getEntityMap().keySet()) {
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
                Map<String, Double> newClusterEntityMap = new HashMap<>();
                newClusterEntityMap.put(nextCenterId, 0D);
                cluster.setEntityMap(newClusterEntityMap);
                clusters.put(cluster.getCenterEntity().getId(), cluster);
                logger.info("new center: " + nextCenterId);
                return true;
            }
        }
    }

    @Override
    public void assignPoints() {
        logger.info("[ASSIGN] assign entities start");
        List<String> centerIdList = new ArrayList<>();
        for (Cluster cluster: clusters.values()) {
            centerIdList.add(cluster.getCenterEntity().getCenterId());
        }

        Set<String> entityIdIter = entityMap.keySet();

        //分配entity前清空之前的分配结果
        for(Cluster cluster: clusters.values()) {
            cluster.getEntityMap().clear();
        }

        for (String entityId : entityIdIter) {
            //如果
            if (centerIdList.contains(entityId)) {
                continue;
            }
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
                update =this.updateCenter(cluster) | update ;
            }
            notFinish = update;
        }
        int syncCount = this.syncEntityData();
        logger.info("entityData sync finished, sync count:" + syncCount);
        logger.info("k-means service execute success.");
    }

    public int syncEntityData() {
        logger.info("[sync] sync entity data begin.");
        int updateCount = 0;

        Set<String> entityIdSet = entityMap.keySet();
        for (String entityId : entityIdSet) {
            ClusterEntityBean entity = entityMap.get(entityId);
            try {
                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("_id", entityId);
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("isCenter", entity.getIsCenter());
                updateMap.put("centerId", entity.getCenterId());
                updateCount += clusterEntityDao.updateEntity(queryMap, updateMap);

                ClusterObj obj = new ClusterObj();
                obj.setIsCenter(entity.getIsCenter());
                obj.setCenterId(entity.getCenterId());
                obj.setMongoId(entity.getId());
                obj.setCode(entity.getCode());
                objectSV.updateObjByMongodbId(obj);
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
