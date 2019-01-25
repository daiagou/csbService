package com.kargo.domain.csb.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by abner.zhang on 2019/1/17.
 */
@Getter
@Setter
public class QueryOrderInfosReq {


    private Integer id;

    private String orderNo;

    private String goodsName;

    private BigDecimal price;

    private String picUrl;

    private String goodsType;

    private Date createTime;

    private Date updateTime;


}
