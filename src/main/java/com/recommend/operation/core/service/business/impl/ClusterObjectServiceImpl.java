package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterObjMapper;
import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterObjExample;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.service.business.interfaces.IClusterAttrSV;
import com.recommend.operation.core.service.business.interfaces.IClusterObjectSV;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;

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

    @Autowired
    private IClusterAttrSV attrSV;

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

            logger.info("[insert] " + entityList.indexOf(entity) + 1 + " / " + entityList.size());
        }
        logger.info("import entity count: " + importCount);

        return importCount;
    }

    @Override
    public List<Object> getRecommendResult(String entityCode) throws Exception{
        if (StringUtils.isEmpty(entityCode)) {
            logger.error("entityCode can not be empty");
            return null;
        }
        List<Object> result = new ArrayList<>();

        Map<String, KeyCount> keyCountMap = new HashMap<>();

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
                for (String key : bean.getAttrValue().keySet()) {
                    Object value = bean.getAttrValue().get(key);
                    if (Integer.parseInt(value.toString()) > 4) {
                        if(!keyCountMap.containsKey(key)) {
                            keyCountMap.put(key, new KeyCount(key, 1));
                        } else {
                            keyCountMap.get(key).setCount(keyCountMap.get(key).getCount() + 1);
                        }
                    }
                }
            }
        }

        List<KeyCount> keyCountList = new ArrayList<>(keyCountMap.values());
        Collections.sort(keyCountList);
        for (int i = 0; i < 10; i++) {
            result.add(keyCountList.get(i).getKey());
        }
        return result;
    }

    @Override
    public Integer importEntity(Integer taskId, String host, String port, String username, String password, String database, String sql) {
        if (null == taskId) {
            logger.error("param taskId can not be empty");
            return null;
        }

        String code = "";
        String key = "";
        String value = "";

        List<ClusterAttr> attrList = null;

        try {
            attrList = attrSV.queryAttrListByTaskId(taskId);
            if (!CollectionUtils.isEmpty(attrList)) {
                for (ClusterAttr attr: attrList) {
                    switch (attr.getType()) {
                        case 1:
                            code = attr.getCode();
                            break;
                        case 2:
                            key = attr.getCode();
                            break;
                        case 3:
                            value = attr.getCode();
                            break;
                    }
                }
            } else {
                logger.error("no cluster attr of this task : taskId = " + taskId);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ClusterEntityBean> entityList = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=UTF-8";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rst = pst.executeQuery();
            Map<String, ClusterEntityBean> entityMap = new HashMap<>();
            while(rst.next()) {
                if (!entityMap.containsKey(rst.getObject(code).toString())) {
                    ClusterEntityBean bean = new ClusterEntityBean();
                    String entityCode = rst.getObject(code).toString();
                    bean.setCode(entityCode);
                    bean.setTaskId(taskId);
                    bean.setIsCenter(0);
                    Map<String, Object> attrValue = new HashMap<>();
                    attrValue.put(rst.getObject(key).toString(), rst.getObject(value));
                    bean.setAttrValue(attrValue);
                    entityMap.put(rst.getObject(code).toString(), bean);
                } else {
                    entityMap.get(rst.getObject(code).toString()).getAttrValue().put(rst.getObject(key).toString(), rst.getObject(value));
                }
            }
            entityList = new ArrayList<ClusterEntityBean>(entityMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Integer importCount;

        importCount = this.importEntity(entityList);
        return importCount;
    }

    @Override
    public Integer updateObjByMongodbId(ClusterObj obj) throws Exception{
        ClusterObjExample example = new ClusterObjExample();
        example.createCriteria().andMongoIdEqualTo(obj.getMongoId());

        return objMapper.updateByExampleSelective(obj, example);
    }


    private static class KeyCount implements Comparable<KeyCount> {
        private String key;
        private Integer count;

        public KeyCount(String key, Integer count) {
            this.key = key;
            this.count = count;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public int compareTo(KeyCount o) {
            return o.getCount().compareTo(this.getCount());
        }
    }
}
