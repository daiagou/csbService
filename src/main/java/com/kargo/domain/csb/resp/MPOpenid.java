package com.kargo.domain.csb.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abner.zhang on 2019/5/20.
 */

@Getter
@Setter
public class MPOpenid {

    private String session_key;
    private String openid;
}
