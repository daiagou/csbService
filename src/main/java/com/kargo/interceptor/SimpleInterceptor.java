package com.kargo.interceptor;

import com.alibaba.fastjson.JSON;
import com.kargo.common.annotations.LoginPermission;
import com.kargo.common.constant.SystemConstant;
import com.kargo.common.enums.BaseEnum;
import com.kargo.common.util.BaseResponse;
import com.kargo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class SimpleInterceptor implements HandlerInterceptor {
	private final Logger logger = LoggerFactory.getLogger(SimpleInterceptor.class);


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否为ajax请求，默认不是
		boolean isAjaxRequest = false;
		if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
			isAjaxRequest = true;
		}
		BaseResponse baseResponse = checkLogin(request, response, handler);
		if(baseResponse.getStatus().intValue()!=SystemConstant.OK){
//			if(!isAjaxRequest){
//				response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/");
//			}else{
//				writerToJson(baseResponse,response);
//			}
			writerToCrossDomain(baseResponse,response,request.getHeader("Origin"));
			return false;
		}
		return true;
	}



	/**
	 * 校验是否登陆
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private BaseResponse checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod=null;
		BaseResponse baseResponse = new BaseResponse();
		try {
			if(handler instanceof HandlerMethod){
				handlerMethod = (HandlerMethod) handler;
			}
		} catch (Exception e) {
			//不能转换直接返回
			return baseResponse.genSuccessResp();
		}
		if(handlerMethod==null){
			//不能转换直接返回
			return baseResponse.genSuccessResp();
		}
		Method method = handlerMethod.getMethod();
		BaseEnum.RoleEnum[] permissionEnum = null;
		if (method != null && method.isAnnotationPresent(LoginPermission.class)) {
			permissionEnum = method.getAnnotation(LoginPermission.class).needRoles();
		}else {
			//如果不用检测登陆，则直接返回true
			return baseResponse.genSuccessResp();
		}

		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			return baseResponse.genFail("请先登录!");
		}

		boolean roleFail = false;
		if (permissionEnum!=null||permissionEnum.length>0) {
			for(BaseEnum.RoleEnum role:permissionEnum){
//				if(!role.getValue().equals(user.getRole())){
//					roleFail = true;
//					break;
//				}
			}
		}
		if(roleFail){
			return baseResponse.genFail("您没有权限操作!");
		}
		return baseResponse.genSuccessResp();
	}

	public void writerToJson(Object resp, HttpServletResponse response) {
		responseString(JSON.toJSONString(resp), response);
		logger.info("interceptor response:" + JSON.toJSONString(resp));
	}



	public void writerToCrossDomain(Object resp, HttpServletResponse response,String domainName) {
		response.setHeader("Access-Control-Allow-Origin", domainName);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		this.writerToJson(resp, response);
	}

	public void responseString(String data, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}


}
