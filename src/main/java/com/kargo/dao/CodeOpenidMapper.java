package com.kargo.dao;

import com.kargo.model.CodeOpenid;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeOpenidMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CodeOpenid record);

    int insertSelective(CodeOpenid record);

    CodeOpenid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CodeOpenid record);

    int updateByPrimaryKey(CodeOpenid record);

    List<CodeOpenid> selectPage(@Param("record") CodeOpenid record, @Param("pageable") Pageable pageable);

    int selectCount(@Param("record") CodeOpenid record);
}