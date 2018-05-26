package com.recommend.operation.core.service.business.interfaces;

import com.recommend.operation.core.dao.model.ClusterAttr;

import java.util.List;

/**
 * @author zhanggh
 */
public interface IClusterAttrSV {
    /**
     * 导入参考属性, 创建一系列attr
     * @author zhanggh
     * @param attrList ArrayList of Cluster Attribute
     * @return import records count
     */
    public Integer importAttribute(List<ClusterAttr> attrList) throws Exception;

    /**
     * 修改参考属性
     * @author zhanggh
     * @param attr ClusterAttr的实体类，其中不需要修改的字段对应的属性为空，attr id 不能为空
     * @return count be updated
     * @throws Exception
     */
    public Integer updateAttribute(ClusterAttr attr) throws Exception;

    /**
     * 根据attr id 删除Cluster attr
     * @author zhanggh
     * @param attrId attr id
     * @return count be deleted
     * @throws Exception
     */
    public Integer deleteAttributeById(Integer attrId) throws Exception;

    /**
     * query Cluster attr list by Task Id
     * @author zhanggh
     * @param taskID task id
     * @return cluster attr list
     * @throws Exception
     */
    public List<ClusterAttr> queryAttrListByTaskId(Integer taskID) throws Exception;
}
