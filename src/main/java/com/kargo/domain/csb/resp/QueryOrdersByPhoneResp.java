package com.kargo.domain.csb.resp;

import com.kargo.model.OrderGoodsInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by abner.zhang on 2019/1/25.
 */
@Getter
@Setter
public class QueryOrdersByPhoneResp {

    private OrderResp orders;

    private List<OrderGoodsInfo> orderGoodsInfoList;

    private String orderDetails;


}
