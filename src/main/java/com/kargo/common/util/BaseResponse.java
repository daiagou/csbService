package com.kargo.common.util;


import com.kargo.common.constant.SystemConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * event,service基础返回bean
 *
 * @author abner.zhang
 *
 */
@Getter
@Setter
public class BaseResponse {

	private Integer status = 1;
	private String message = "fail";
	private Object data;
	private String code;


	public BaseResponse() {
		this.status = SystemConstant.FAIL;
		this.message = SystemConstant.FAIL_STR;
	}


	public BaseResponse(Object data) {
		this.status = SystemConstant.OK;
		this.message = SystemConstant.SUCCESS_STR;
		this.data = data;
	}

	public BaseResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public BaseResponse genSuccessResp() {
		this.status = SystemConstant.OK;
		this.message = SystemConstant.SUCCESS_STR;
		return this;
	}
	public BaseResponse genFail(String message) {
		this.status = SystemConstant.FAIL;
		this.message = message;
		return this;
	}
	public BaseResponse genSuccessResp(Object data) {
		this.status = SystemConstant.OK;
		this.message = SystemConstant.SUCCESS_STR;
		this.data = data;
		return this;
	}

	public boolean isSuccess(){
		return this.status== SystemConstant.OK;
	}

}