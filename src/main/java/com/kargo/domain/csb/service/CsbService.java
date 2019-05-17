package com.kargo.domain.csb.service;


import com.kargo.common.util.BaseResponse;
import com.kargo.domain.csb.req.*;
import com.kargo.model.GoodsInfo;
import com.kargo.model.Orders;

/**
 * @author abner.zhang
 *
 */
public interface CsbService {
    String getOpenId(String code);

    BaseResponse cancelOrder(String orderNo);

    BaseResponse queryOrdersByPhone(String phone, String orderStatus);

    BaseResponse buy(BuyReq req);

    BaseResponse queryGoodsInfosList();

    BaseResponse deleteGoods(Integer id);

    BaseResponse saveOrUpdateGoods(GoodsInfo req);

    BaseResponse saveGoods(SaveGoodsInfoReq req);

    BaseResponse updateGoods(UpdateGoodsInfoReq req);


    BaseResponse queryGoodsInfo(QueryGoodsInfoReq req);

    BaseResponse queryGoodsInfosLike(QueryGoodsInfosLikeReq req);

    BaseResponse updateOrder(Orders req);

    BaseResponse queryOrdersLike(Orders req);

    BaseResponse queryOrders(Orders req);

    BaseResponse queryOrderAndInfosByOrderNo(String orderNo);

    BaseResponse queryOrderInfos(QueryOrderInfosReq req);
}
