//package com.kargo.common.enums;
//
//import lombok.Getter;
//
//public interface ScaleEnum {
//
//
//
//
//    /**
//     * 订单状态枚举
//     */
//    @Getter
//    public enum OrderStatusEnum {
//        CREATED("CREATED"), WAIT_APPROVE("WAIT_APPROVE"), APPROVE("APPROVE"),
//        REJECT("REJECT"),PROCESSING("PROCESSING"), ASSIGNED("ASSIGNED"), SENT_MQ("SENT_MQ"), ACTIVATED("ACTIVATED"), ACTIVATION_FAILED("ACTIVATION_FAILED"),;
//        private String value;
//
//        private OrderStatusEnum(String value) {
//            this.value = value;
//        }
//    }
//
//
//    /**
//     * 订单明细状态枚举
//     */
//    @Getter
//    public enum OrderDetailStatusEnum {
//        CREATED("CREATED"), WAIT_ACTIVE("WAIT_ACTIVE"), ASSIGNED("ASSIGNED"), SENT_MQ("SENT_MQ"), ACTIVATED("ACTIVATED"), ACTIVATION_FAILED("ACTIVATION_FAILED"),;
//        private String value;
//        private OrderDetailStatusEnum(String value) {
//            this.value = value;
//        }
//    }
//
//}
