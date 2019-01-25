package com.kargo.domain.login.service;


import com.kargo.common.util.BaseResponse;
import com.kargo.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author abner.zhang
 *
 */
public interface LoginService {

    BaseResponse getLoginCode(String phone);

    BaseResponse login(User loginReq, HttpServletRequest request);
}
