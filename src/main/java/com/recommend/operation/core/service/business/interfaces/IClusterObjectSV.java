package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;

import java.util.List;

/**
 * Created by feir4 on 2018/5/25.
 */
public interface IClusterObjectSV {

    /**
     * create entity batch
     * @param entityList List of Cluster Entity
     * @return entity count imported
     * @author zhanggh
     */
    public int importEntity(List<ClusterEntityBean> entityList);

    /**
     * 获取推荐结果，通过传入的entitycode和attrCode，
     * @author zhanggh
     * @param entityCode entity的标识符，获取关于此entity的推荐结果
     * @param attrCode 要获取的attr,以此code作为key，在entity的attrValue中查找值
     */
    public List<Object> getRecommendResult(String entityCode, String attrCode) throws Exception;
}
