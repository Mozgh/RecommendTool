package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import com.recommend.operation.core.util.Cluster;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zhanggh
 */
public class KMeansServiceImpl implements IKMeansSV {

    private Logger logger = Logger.getLogger(KMeansServiceImpl.class);

    private List<Cluster> clusters = new ArrayList<>();

    private List<ClusterEntityBean> entityList = new ArrayList<>();

    private Map<String, ClusterEntityBean> centers = new HashMap<>();

    private List<ClusterAttr> attrList;


    /**
     * @author zhanggh
     * @param centerCount   init center count
     * @param attrList      attribute list referenced
     * @param entityList    cluster entity list
     */
    public KMeansServiceImpl(int centerCount, List<ClusterAttr> attrList, List<ClusterEntityBean> entityList) {
        this.attrList = attrList;
        this.entityList = entityList;
        this.initCenter(centerCount);
    }

    @Override
    public void initCenter(int count) {
        logger.info("[INIT] init cluster center : count = " + count);
        for (int i = 0; i < count; i++) {
            ClusterEntityBean entity = entityList.get(entityList.size() / count * (i + 1));
            Map<String, Double> entityId = new HashMap<>();
            entityId.put(entity.getId(), 0D);
            entity.setCenterId(entity.getId());
            entity.setIsCenter(1);
            Cluster cluster = new Cluster();
            cluster.setCenterEntity(entity);
            cluster.setEntityId(entityId);

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
                entityValue = (Float)entity.getAttrValue().get(attr.getCode());
                baseValue = (Float)base.getAttrValue().get(attr.getCode());
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
    public void pointsCenter() {

    }

    @Override
    public boolean updateCenter() {
        for (Cluster cluster : clusters) {
            Double min_distance = null;

            ClusterEntityBean nextCenter = new ClusterEntityBean();
//            Double nextCenterDistance = calcDistence(, cluster.getCenterEntity(), attrList);

            Iterator<String> entityIds = cluster.getEntityId().keySet().iterator();
            for (Iterator<String> it = entityIds; it.hasNext(); ) {
                String entityId = it.next();
                for (ClusterAttr attr: attrList) {

                }


            }
        }

        return false;
    }

    @Override
    public void assignPoints() {

        logger.info("[ASSIGN] assign entities start");
        for (ClusterEntityBean entity: entityList) {
            Double distance = null;
            String center = null;

            for (Cluster cluster: clusters) {
                Double newDistance = calcDistence(entity, cluster.getCenterEntity(), attrList);
                if (null == distance || newDistance < distance) {
                    distance = newDistance;
                    center = cluster.getCenterEntity().getId();
                }
            }

            entity.setCenterId(center);
            entity.setDissimilarity(distance);
            for (Cluster cluster: clusters) {
                if (center.equals(cluster.getCenterEntity().getId())) {
                    cluster.getEntityId().put(entity.getId(), distance);
                }
                Iterator<String> attrCodes = cluster.getAttrValueMap().keySet().iterator();

                Double value;
                for (ClusterAttr attr: attrList) {
                    switch (attr.getType()) {
                        case 1:
                            value = cluster.getAttrValueMap().get(attr.getCode());
                            value += Double.parseDouble(entity.getAttrValue().get(attr.getCode()).toString());
                            value = value / cluster.getEntityId().size();
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

            logger.info("assign entity(" + entity.getId() + ") to cluster(center:" + center + ")");
        }

        logger.info("[ASSIGN] assign entities end");
    }

    public List<ClusterEntityBean> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ClusterEntityBean> entityList) {
        this.entityList = entityList;
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
