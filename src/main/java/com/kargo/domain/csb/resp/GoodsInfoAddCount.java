package com.kargo.domain.csb.resp;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by abner.zhang on 2019/1/24.
 */
@Getter
@Setter
public class GoodsInfoAddCount {


    private Integer id;

    private Integer goodsCount = 0;

    private String goodsName;

    private BigDecimal price;

    private String picUrl;

    private String goodsType;

    private Date createTime;

    private Date updateTime;


    private String detailPicUrl1;

    private String detailPicUrl2;

    private String detailPicUrl3;

    private String detailPicUrl4;

    private String detailPicUrl5;


}
