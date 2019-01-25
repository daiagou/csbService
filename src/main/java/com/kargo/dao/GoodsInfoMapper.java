package com.kargo.dao;

import com.kargo.model.GoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    List<GoodsInfo> selectPage(@Param("record") GoodsInfo record, @Param("pageable") Pageable pageable);

    int selectCount(@Param("record") GoodsInfo record);

    List<GoodsInfo> queryGoodsInfosLike(@Param("record") GoodsInfo record, @Param("pageable") Pageable pageable);

    List<GoodsInfo> queryGoodsInfosList();

    List<GoodsInfo> selectByIds(List<Integer> ids);
}