package com.kargo.dao;

import com.kargo.model.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> selectPage(@Param("record") Orders record, @Param("pageable") Pageable pageable);

    int selectCount(@Param("record") Orders record);

    List<Orders> queryOrdersLike(@Param("record") Orders record, @Param("pageable") Pageable pageable);


    @Update("update orders set order_status='CANCEL' where order_no=#{orderNo} and order_status='CREATED'")
    @ResultType(Integer.class)
    int cancelOrder(String orderNo);

}