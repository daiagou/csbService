package com.kargo.common.enums;

import lombok.Getter;

/**
 * 基础枚举
 *
 * @author abner.zhang
 */
public interface BaseEnum {


    /**
     * 角色枚举
     */
    @Getter
    public enum RoleEnum {
        ADMIN("ADMIN"), CLERK("CLERK"),;
        private String value;

        private RoleEnum(String value) {
            this.value = value;
        }
    }

    /**
     * 通用返回枚举 1是0否
     *
     * @author abner.zhang
     */
    @Getter
    public enum StringBooleaEnum {
        YES("Y"), NO("N"),;
        private String value;

        private StringBooleaEnum(String value) {
            this.value = value;
        }
    }


    /**
     * 通用返回枚举 1是0否
     *
     * @author abner.zhang
     */
    @Getter
    public enum IntegerBooleaEnum {
        FALSE(0), TRUE(1),;
        private Integer value;

        private IntegerBooleaEnum(Integer value) {
            this.value = value;
        }
    }


    /**
     * 通用返回枚举
     *
     * @author abner.zhang
     */
    @Getter
    public enum SimpleRespEnum {
        success(0, "success"), fail(99, "fail"), param_error(1, "param error"), sign_check_fail(2, "sign check fail"), can_not_get_code(3, "can't get card no");
        private Integer status;
        private String message;

        private SimpleRespEnum(Integer status, String message) {
            this.status = status;
            this.message = message;
        }
    }


    /**
     * 状态枚举
     *
     * @author abner.zhang
     */
    @Getter
    public enum StatusEnum {
        valid("valid"), invalid("invalid");
        private String value;

        private StatusEnum(String value) {
            this.value = value;
        }

        public static StatusEnum parse(String value) {
            if (value != null) {
                for (StatusEnum type : StatusEnum.values()) {
                    if (type.getValue().equals(value)) {
                        return type;
                    }
                }
            }
            return null;
        }
    }

    /**
     * 路由枚举
     *
     * @author abner.zhang
     */
    @Getter
    public enum RouteEnum {
        ALI("ALI"), WECHAT("WECHAT");
        private String value;

        private RouteEnum(String value) {
            this.value = value;
        }
    }

    /**
     * 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、 TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
     * TRADE_SUCCESS（交易支付成功）、 TRADE_FINISHED（交易结束，不可退款）
     *
     * @author abner.zhang
     */
    @Getter
    public enum BaseTradeStatusEnum {
        WAIT_BUYER_PAY("WAIT_BUYER_PAY"), TRADE_SUCCESS("TRADE_SUCCESS"), TRADE_CLOSED("TRADE_CLOSED"), TRADE_FINISHED(
                "TRADE_FINISHED");
        private String value;

        private BaseTradeStatusEnum(String value) {
            this.value = value;
        }
    }

}
