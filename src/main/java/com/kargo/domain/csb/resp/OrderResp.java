package com.kargo.domain.csb.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by abner.zhang on 2019/5/21.
 */
@Getter
@Setter
public class OrderResp {

    private Integer id;

    private String orderNo;

    private BigDecimal totalAmount;

    private String customer;

    private String openid;

    private String phone;

    private String address;

    private String comment;

    private String orderStatus;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
