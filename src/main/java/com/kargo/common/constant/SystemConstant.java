package com.kargo.common.constant;

import java.net.InetAddress;

public class SystemConstant {
    public static final int OK = 0;
    public static final int FAIL = 1;//系统异常
    public static final int ALERT = 10;
    public static final String SUCCESS_STR = "success";
    public static final String FAIL_STR = "failed";
    public static final String PARAM_ERROR = "param error";
    public static final String LOCAL_IP;

    static {
        String localIp = "127.0.0.1";

        try {
            localIp = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (Exception var2) {
            ;
        }

        LOCAL_IP = localIp;
    }

    public SystemConstant() {
    }
}