package com.kargo.controller;

import com.alibaba.fastjson.JSONObject;
import com.kargo.common.exception.BusinessException;
import com.kargo.common.util.BaseResponse;
import com.kargo.controller.base.BaseControl;
import com.kargo.domain.login.service.LoginService;
import com.kargo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by abner.zhang on 2018/8/30.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:8888",allowCredentials = "true")
//@CrossOrigin
@CrossOrigin(origins="*",allowCredentials ="true" )
public class MainController extends BaseControl {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private LoginService loginService;


    @RequestMapping(value = "/getLoginCode", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login( String phone) {
        logger.info("getLoginCode phone:[{}]", phone);
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse = loginService.getLoginCode(phone);
        } catch (BusinessException e) {
            baseResponse.genFail(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String respStr = JSONObject.toJSONString(baseResponse);
        logger.info("getLoginCode respStr:[{}]", respStr);
        return respStr;
    }


    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(@RequestBody User req, HttpServletRequest request) {
        logger.info("login req:[{}]", JSONObject.toJSONString(req));
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse = loginService.login(req,request);
        } catch (BusinessException e) {
            baseResponse.genFail(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String respStr = JSONObject.toJSONString(baseResponse);
        logger.info("login respStr:[{}]", respStr);
        return respStr;
//        writerToCrossDomain2(baseResponse,response,request.getHeader("Origin"));
    }


}
