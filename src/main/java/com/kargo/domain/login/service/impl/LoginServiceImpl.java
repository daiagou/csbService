package com.kargo.domain.login.service.impl;

import com.kargo.common.util.BaseResponse;
import com.kargo.common.util.MD5HashUtil;
import com.kargo.common.util.UtilLocalDate;
import com.kargo.dao.UserMapper;
import com.kargo.domain.login.service.LoginService;
import com.kargo.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Autowired
    private UserMapper userMapper;





    @Override
    public BaseResponse getLoginCode(String phone) {
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isBlank(phone)){
            return baseResponse.genFail("手机号码不能为空");
        }
        //生成4位随机数，放到缓存中，30分钟有效
//        String code = "1234";
        String code = UtilLocalDate.getRandomStr(4);
//        SendSmsResp sendSmsResp = smsService.sendSms(phone, MessageFormat.format(smsContent, code));
//        if(!sendSmsResp.getRespCode().equals("000")){
//            return baseResponse.genFail("短信发送失败");
//        }
//        RedisUtil.saveCache(redisTemplate, AdsConstant.ADV_LOGIN_PHONE+phone,code,AdsConstant.ADV_LOGIN_PHONE_TIME);
        logger.info("getLoginCode phone:[{}],code:[{}]",phone,code);
        return baseResponse.genSuccessResp();
    }


    @Override
    public BaseResponse login(User loginReq, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isBlank(loginReq.getPassword())){
            return baseResponse.genFail("手机号码不能为空");
        }
        if(StringUtils.isBlank(loginReq.getPassword())){
            return baseResponse.genFail("验证码不能为空");
        }

        loginReq.setPassword(MD5HashUtil.md5(loginReq.getPassword()));

        int i = userMapper.selectCount(loginReq);
        if(i==0){
            return baseResponse.genFail("账号密码不正确");
        }
        //保存到session
        User user = new User();
        user.setUsername(loginReq.getUsername());
        request.getSession().setAttribute("user",user);
        return baseResponse.genSuccessResp();
    }



}
