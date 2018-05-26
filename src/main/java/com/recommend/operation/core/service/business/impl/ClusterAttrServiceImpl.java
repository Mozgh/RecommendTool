package com.recommend.operation.core.service.business.impl;

import com.recommend.operation.core.dao.interfaces.ClusterAttrMapper;
import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterAttrExample;
import com.recommend.operation.core.service.business.interfaces.IClusterAttrSV;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhanggh
 */
@Service
public class ClusterAttrServiceImpl implements IClusterAttrSV {

    private static final Logger logger = Logger.getLogger(ClusterAttrServiceImpl.class);

    @Resource
    private ClusterAttrMapper attrMapper;

    @Override
    public Integer importAttribute(List<ClusterAttr> attrList) throws Exception{
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
    public Integer updateAttribute(ClusterAttr attr) throws Exception {
        if (null == attr || null == attr.getId()) {
            logger.error("param can not be empty");
            return 0;
        }
        return attrMapper.updateByPrimaryKeySelective(attr);
    }

    @Override
    public Integer deleteAttributeById(Integer attrId) throws Exception {
        if (null == attrId) {
            logger.error("attrId can not be empty");
            return 0;
        }
        return attrMapper.deleteByPrimaryKey(attrId);
    }

    @Override
    public List<ClusterAttr> queryAttrListByTaskId(Integer taskID) throws Exception {
        if (null == taskID) {
            logger.error("param taskId can not be empty");
            return null;
        }
        List<ClusterAttr> attrList = null;
        ClusterAttrExample example = new ClusterAttrExample();
        example.createCriteria().andTaskIdEqualTo(taskID);
        attrList = attrMapper.selectByExample(example);
        return attrList;
    }

}
