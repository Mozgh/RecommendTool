package com.recommend.operation.core.dao.interfaces;

import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.model.ClusterTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterTaskMapper {
    long countByExample(ClusterTaskExample example);

    int deleteByExample(ClusterTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClusterTask record);

    int insertSelective(ClusterTask record);

    List<ClusterTask> selectByExample(ClusterTaskExample example);

    ClusterTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClusterTask record, @Param("example") ClusterTaskExample example);

    int updateByExample(@Param("record") ClusterTask record, @Param("example") ClusterTaskExample example);

    int updateByPrimaryKeySelective(ClusterTask record);

    int updateByPrimaryKey(ClusterTask record);
}