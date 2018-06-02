package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterObjCount;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;

import java.util.List;
import java.util.Map;

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
     */
    public List<Object> getRecommendResult(String entityCode) throws Exception;

    public Integer importEntity(Integer taskId, String host, String port, String user, String password, String database, String sql);

    public Integer updateObjByMongodbId(ClusterObj obj) throws Exception;

    public List<ClusterObjCount> queryClusterResult(Integer taskId);
}
