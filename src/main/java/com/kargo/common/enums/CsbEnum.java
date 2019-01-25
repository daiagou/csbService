package com.kargo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author abner.zhang
 */
public interface CsbEnum {

    @Getter
    @AllArgsConstructor
    enum OrderStatusEnum{
        CREADTED("CREATED","待处理"),DEALING("DEALING","处理中"),FINISHED("FINISHED","已完成"),CANCEL("CANCEL","已取消"),;
        private String value;
        private String desc;
    }


}
