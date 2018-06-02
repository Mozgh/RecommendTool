package com.recommend.operation.core.dao.interfaces;

import com.recommend.operation.core.dao.model.ClusterObj;
import com.recommend.operation.core.dao.model.ClusterObjExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
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