package com.kargo.controller.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by abner.zhang on 2017/2/15.
 */
@RestController
public class BaseControl {
	private Logger logger = LoggerFactory.getLogger(BaseControl.class);

//	@ExceptionHandler
//	public BaseResponse handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
//		logger.info(e.getMessage(), e);
//		return new BaseResponse();
//	}

	public void writerToCrossDomain(Object resp, HttpServletResponse response, String domainName) {
		response.setHeader("Access-Control-Allow-Origin", domainName);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		this.writerToJson(resp, response);
	}

	public void writerToCrossDomain2(Object resp, HttpServletResponse response, String domainName) {
		response.setHeader("Access-Control-Allow-Origin", domainName);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "0");
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("XDomainRequestAllowed","1");
		this.writerToJson(resp, response);
	}
	public void writerToJson(Object resp, HttpServletResponse response) {
		responseString(JSON.toJSONString(resp), response);
		logger.info("interceptor response:" + JSON.toJSONString(resp));
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
