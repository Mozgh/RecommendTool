package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggh
 */
public class KMeansServiceImpl implements IKMeansSV {

    private Logger logger = Logger.getLogger(KMeansServiceImpl.class);

    private Map<String, List<String>> clusters = new HashMap<>();

    private List<ClusterEntityBean> entityList = new ArrayList<>();

    private Map<String ,ClusterEntityBean> centers = new HashMap<>();

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
            List<String> entityIdList = new ArrayList<>();
            entityIdList.add(entity.getId());
            entity.setCenterId(entity.getId());
            entity.setIsCenter(1);
            clusters.put(entity.getId(), entityIdList);
            centers.put(entity.getId(), entity);
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
    public boolean updateCenter(String oldCenterId, String newCenterId) {


        return false;
    }

    @Override
    public void assignPoints() {

        logger.info("[ASSIGN] assign entities start");
        for (ClusterEntityBean entity: entityList) {
            Double distance = null;
            String center = null;
            for (String centerId: centers.keySet()) {
                Double newDistance = calcDistence(entity, centers.get(centerId), attrList);
                if (null == distance || newDistance < distance) {
                    distance = newDistance;
                    center = centerId;
                }
            }
            entity.setCenterId(center);
            entity.setDissimilarity(distance);
            clusters.get(center).add(entity.getId());
            logger.info("assign entity(" + entity.getId() + ") to cluster(center:" + center + ")");
        }

        logger.info("[ASSIGN] assign entities end");
    }

    public Map<String, List<String>> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, List<String>> clusters) {
        this.clusters = clusters;
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
