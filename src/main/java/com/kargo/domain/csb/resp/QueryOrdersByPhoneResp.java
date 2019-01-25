package com.kargo.domain.csb.resp;

import com.kargo.model.OrderGoodsInfo;
import com.kargo.model.Orders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by abner.zhang on 2019/1/25.
 */
@Getter
@Setter
public class QueryOrdersByPhoneResp {

    private Orders orders;

    private List<OrderGoodsInfo> orderGoodsInfoList;

    private String orderDetails;


}
