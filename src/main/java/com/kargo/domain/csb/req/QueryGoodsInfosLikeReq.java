package com.kargo.domain.csb.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by abner.zhang on 2019/1/16.
 */
@Getter
@Setter
public class QueryGoodsInfosLikeReq {


    private String goodsName;

    private BigDecimal price;

    private String goodsType;

}
