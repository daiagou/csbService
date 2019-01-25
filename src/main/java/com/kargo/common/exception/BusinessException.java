package com.kargo.common.exception;

import com.kargo.common.constant.SystemConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by abner.zhang on 18/4/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends  RuntimeException {

    private String code;

    private String message;
    public BusinessException(String message){
        this(String.valueOf(SystemConstant.FAIL),message);
    }

    public BusinessException(String code,String message){
        super(message);
        this.code=code;
        this.message = message;
    }

}
