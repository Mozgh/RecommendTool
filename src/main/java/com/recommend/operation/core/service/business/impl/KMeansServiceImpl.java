package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterAttrMapper;
import com.recommend.operation.core.dao.interfaces.ClusterTaskMapper;
import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterAttrExample;
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

    private List<Cluster> clusters = new ArrayList<>();

    private Map<String, ClusterEntityBean> entityMap = new HashMap<>();

    private Map<String, ClusterEntityBean> centers = new HashMap<>();

    private List<ClusterAttr> attrList;

    @Autowired
    ClusterEntityDao clusterEntityDao;

    @Autowired
    ClusterAttrMapper attrMapper;

    private boolean loadTaskData(Integer taskId) {
        if (null == taskId) {
            logger.error("task id can not be null!");
        }

        ClusterAttrExample example = new ClusterAttrExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        this.attrList = attrMapper.selectByExample(example);

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
            Map<String, Double> entityId = new HashMap<>();
            entityId.put(entity.getId(), 0D);
            entity.setCenterId(entity.getId());
            entity.setIsCenter(1);
            Cluster cluster = new Cluster();
            cluster.setCenterEntity(entity);
            cluster.setEntityMap(entityId);
            Map<String, Object> attrMap = new HashMap<>();
            for (ClusterAttr attr: attrList) {
                switch (attr.getType().toString()) {
                    case "1":
                        attrMap.put(attr.getCode(), 0D);
                        break;
                    case "2":
                        attrMap.put(attr.getCode(), false);
                        break;
                    case "3":
                        attrMap.put(attr.getCode(), "");
                        break;
                    default:
                }

            }
            cluster.setAttrValueMap(attrMap);
            centers.put(entity.getId(), entity);
            clusters.add(cluster);
            logger.info("cluster[" + (i + 1) + "] centerId: " + entity.getId());
        }
        logger.info("[INIT] init centers end");
    }

    @Override
    public double calcDistence(ClusterEntityBean entity, ClusterEntityBean base, List<ClusterAttr> attrList) {
        double distance = 0F;
        double entityMod = 0F;
        double baseMod = 0F;
        double crossProduct = 0F;
        double entityValue = 0F;
        double baseValue = 0F;
        for (ClusterAttr attr : attrList) {
            try {
                switch (attr.getType()) {
                    case 1:
                        entityValue = Double.parseDouble(entity.getAttrValue().get(attr.getCode()).toString());
                        baseValue = Double.parseDouble(base.getAttrValue().get(attr.getCode()).toString());
                        break;
                    case 2:
                    case 3:
                        baseValue = 0D;
                        if(entity.getAttrValue().get(attr.getCode()).equals(base.getAttrValue().get(attr.getCode()))) {
                            entityValue = 1D;
                        } else {
                            entityValue = 0D;
                        }
                        break;
                    default:
                        logger.error("attr type error: entityId=" + entity.getId() + " baseId:" + base.getId() + " attribute name:" + attr.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            crossProduct += entityValue * baseValue;
            entityMod += entityValue * entityValue;
            baseMod += baseValue * baseValue;
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
        logger.info("");
        Double minDistance = null;
        String nextCenterId = null;

        ClusterEntityBean nextCenter = new ClusterEntityBean();
        nextCenter.setAttrValue(cluster.getAttrValueMap());
        //计算下一中心点到本中心点的距离
        Double nextCenterDistance = calcDistence(nextCenter, cluster.getCenterEntity(), attrList);

        //寻找与本中心点距离和nextCenterDistance最接近的点
        Set<String> idSet = cluster.getEntityMap().keySet();
        for (String id : idSet) {
            Double distance = cluster.getEntityMap().get(id) - nextCenterDistance;
            if (null == minDistance || distance < minDistance) {
                minDistance = distance;
                nextCenterId = id;
            }
        }
        //最接近的点即为下一个聚类中心，修改聚类的中心实体，entityMap清空，等待下次分配
        if (StringUtils.isEmpty(nextCenterId)) {
            logger.error("error!");
        } else {
            //如果计算得到的下一个聚类中心和当前中心相同，说明已不能再修改，返回false
            if (nextCenterId.equals(cluster.getCenterEntity().getCenterId())) {
                logger.info("final center: " + nextCenterId);
                return false;
            } else {
                //如果聚类中心发生了变换，返回true
                cluster.setCenterEntity(entityMap.get(nextCenterId));
                cluster.getEntityMap().clear();
                logger.info("new center: " + nextCenterId);
                return true;
            }
        }

        return true;
    }

    @Override
    public void assignPoints() {

        logger.info("[ASSIGN] assign entities start");

        Set<String> entityIdIter = entityMap.keySet();

        for (String entityId : entityIdIter) {
            ClusterEntityBean entity = entityMap.get(entityId);
            Double distance = null;
            String centerId = null;

            for (Cluster cluster : clusters) {
                Double newDistance = calcDistence(entity, cluster.getCenterEntity(), attrList);
                if (null == distance || newDistance < distance) {
                    distance = newDistance;
                    centerId = cluster.getCenterEntity().getId();
                }
            }

            entity.setCenterId(centerId);
            entity.setDissimilarity(distance);
            for (Cluster cluster : clusters) {
                if (null != centerId && centerId.equals(cluster.getCenterEntity().getId())) {
                    cluster.getEntityMap().put(entity.getId(), distance);
                }

                Map<String, Integer> stringAttrCounter = new HashMap<>();
                for (ClusterAttr attr : attrList) {
                    switch (attr.getType()) {
                        case 1:
                            Double valueD = 0D;
                            if (null != cluster.getAttrValueMap().get(attr.getCode())) {
                                valueD = Double.parseDouble(cluster.getAttrValueMap().get(attr.getCode()).toString());
                            }
                            valueD += Double.parseDouble(entity.getAttrValue().get(attr.getCode()).toString());
                            valueD = valueD / cluster.getEntityMap().size();
                            cluster.getAttrValueMap().put(attr.getCode(), valueD);
                            break;
                        case 2:
                            Boolean valueB = false;
                            if (null != cluster.getAttrValueMap().get(attr.getCode())) {
                                valueB = Boolean.parseBoolean(cluster.getAttrValueMap().get(attr.getCode()).toString());
                            }
                            Boolean valueV = Boolean.parseBoolean(entity.getAttrValue().get(attr.getCode()).toString());
                            if (valueB) {
                                valueV = !valueV;
                            }
                            if (valueV) {
                                cluster.getAttrValueMap().put(attr.getCode(), true);
                            } else {
                                cluster.getAttrValueMap().put(attr.getCode(), false);
                            }
                            break;
                        case 3:
                            //TODO:字符类型的属性值计算下一中心时如何处理
//                            String valueS = entity.getAttrValue().get(attr.getCode()).toString();
//                            if (stringAttrCounter.containsKey(valueS)) {
//                                Integer count = stringAttrCounter.get(valueS);
//                                stringAttrCounter.put(valueS, count + 1);
//                            } else {
//                                stringAttrCounter.put(valueS, 1);
//                            }
//
                            break;
                        default:
                            logger.error("attr type error!");
                    }
                }
            }

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
            for (Cluster cluster : clusters) {
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
                updateMap.put("centerId", entityMap.get(entityId).getIsCenter());
                clusterEntityDao.updateEntity(queryMap, updateMap);
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

    public Map<String, ClusterEntityBean> getCenters() {
        return centers;
    }

    public void setCenters(Map<String, ClusterEntityBean> centers) {
        this.centers = centers;
    }

    public List<ClusterAttr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<ClusterAttr> attrList) {
        this.attrList = attrList;
    }

}
