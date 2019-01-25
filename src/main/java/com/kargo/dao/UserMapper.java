package com.kargo.dao;

import com.kargo.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectPage(@Param("record") User record, @Param("pageable") Pageable pageable);

    int selectCount(@Param("record") User record);
}