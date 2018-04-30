package com.recommend.operation.core.dao.interfaces;

import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterObjMapper {
    long countByExample(ClusterObjExample example);

    int deleteByExample(ClusterObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClusterObj record);

    int insertSelective(ClusterObj record);

    List<ClusterObj> selectByExample(ClusterObjExample example);

    ClusterObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClusterObj record, @Param("example") ClusterObjExample example);

    int updateByExample(@Param("record") ClusterObj record, @Param("example") ClusterObjExample example);

    int updateByPrimaryKeySelective(ClusterObj record);

    int updateByPrimaryKey(ClusterObj record);
}