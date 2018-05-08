package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import com.recommend.operation.core.util.Cluster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zhanggh
 */
public class KMeansServiceImpl implements IKMeansSV {

    private Logger logger = Logger.getLogger(KMeansServiceImpl.class);

    private List<Cluster> clusters = new ArrayList<>();

    private Map<String, ClusterEntityBean> entityMap = new HashMap<>();

    private Map<String, ClusterEntityBean> centers = new HashMap<>();

    private List<ClusterAttr> attrList;

    @Autowired
    ClusterEntityDao clusterEntityDao;

    /**
     * @param centerCount init center count
     * @param attrList    attribute list referenced
     * @param entityMap   entity Map, key-entityId, value-entity
     * @author zhanggh
     */
    public KMeansServiceImpl(int centerCount, List<ClusterAttr> attrList, Map<String, ClusterEntityBean> entityMap) {
        this.attrList = attrList;
        this.entityMap = entityMap;
        this.initCenter(centerCount);
    }

    @Override
    public void initCenter(int count) {
        logger.info("[INIT] init cluster center : count = " + count);
        for (int i = 0; i < count; i++) {
            ClusterEntityBean entity = entityMap.get(entityMap.size() / count * (i + 1));
            Map<String, Double> entityId = new HashMap<>();
            entityId.put(entity.getId(), 0D);
            entity.setCenterId(entity.getId());
            entity.setIsCenter(1);
            Cluster cluster = new Cluster();
            cluster.setCenterEntity(entity);
            cluster.setEntityMap(entityId);

            centers.put(entity.getId(), entity);
            clusters.add(cluster);
            logger.info("cluster[" + (i + 1) + "] centerId: " + entity.getId());
        }
        logger.info("[INIT] init centers end");
    }

    @Override
    public double calcDistence(ClusterEntityBean entity, ClusterEntityBean base, List<ClusterAttr> attrList) {
        double distance = 0F;
        Map<String, Float> entityAttrValues = new HashMap<>();
        double entityMod = 0F;
        double baseMod = 0F;
        double crossProduct = 0F;
        double entityValue = 0F;
        double baseValue = 0F;
        for (ClusterAttr attr : attrList) {
            try {
                entityValue = entity.getAttrValue().get(attr.getCode());
                baseValue = base.getAttrValue().get(attr.getCode());
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
            if (null != minDistance || distance < minDistance) {
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
                if (centerId.equals(cluster.getCenterEntity().getId())) {
                    cluster.getEntityMap().put(entity.getId(), distance);
                }
                Iterator<String> attrCodes = cluster.getAttrValueMap().keySet().iterator();

                Double value;
                for (ClusterAttr attr : attrList) {
                    switch (attr.getType()) {
                        case 1:
                            value = cluster.getAttrValueMap().get(attr.getCode());
                            value += entity.getAttrValue().get(attr.getCode());
                            value = value / cluster.getEntityMap().size();
                            cluster.getAttrValueMap().put(attr.getCode(), value);
                            break;
                        case 2:
                            Boolean value_b = Boolean.parseBoolean(cluster.getAttrValueMap().get(attr.getCode()).toString());
                            Boolean value_v = Boolean.parseBoolean(entity.getAttrValue().get(attr.getCode()).toString());
                            if (value_b) {
                                value_v = !value_v;
                            }
                            if (value_v) {
                                cluster.getAttrValueMap().put(attr.getCode(), 1D);
                            } else {
                                cluster.getAttrValueMap().put(attr.getCode(), 0D);
                            }
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
        this.initCenter(task.getCenter());
        boolean notFinish = true;
        while (notFinish) {
            this.assignPoints();
            notFinish = false;
            //当所有聚类更新中心都返回false时，notFinish为false
            for (Cluster cluster : clusters) {
                boolean update = this.updateCenter(cluster);
                update = !update;
                notFinish = notFinish | update;
            }
        }
        this.syncEntityData();
    }

    public int syncEntityData() {
        logger.info("[sync] sync entity data begin.");
        int updateCount = 0;

        Set<String> entityIdSet = entityMap.keySet();
        for (String entityId : entityIdSet) {
            updateCount += clusterEntityDao.updateEntity(entityMap.get(entityId));
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
