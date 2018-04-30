package com.recommend.operation.core.dao.interfaces;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterAttrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ClusterAttrMapper {
    long countByExample(ClusterAttrExample example);

    int deleteByExample(ClusterAttrExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClusterAttr record);

    int insertSelective(ClusterAttr record);

    List<ClusterAttr> selectByExample(ClusterAttrExample example);

    ClusterAttr selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClusterAttr record, @Param("example") ClusterAttrExample example);

    int updateByExample(@Param("record") ClusterAttr record, @Param("example") ClusterAttrExample example);

    int updateByPrimaryKeySelective(ClusterAttr record);

    int updateByPrimaryKey(ClusterAttr record);
}