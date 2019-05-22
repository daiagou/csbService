package com.kargo.domain.csb.req;

import com.kargo.common.annotations.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by abner.zhang on 2019/1/24.
 */
@Getter
@Setter
public class BuyReq {



    @NotBlank
    private String customer;

    private String openid;


    @NotBlank
    private String code;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    private String comment;

    private String orderStatus;

    private Map<Integer,Integer> toBuyGoods;

}
