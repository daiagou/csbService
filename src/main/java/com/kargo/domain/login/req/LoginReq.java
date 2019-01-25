package com.kargo.domain.login.req;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abner.zhang on 2018/9/3.
 */
@Getter
@Setter
public class LoginReq {
    private String phone;
    private String code;
}
