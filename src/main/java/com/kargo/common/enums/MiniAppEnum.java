package com.kargo.common.enums;

import lombok.Getter;

public interface MiniAppEnum {


    @Getter
    public enum BankTypeEnum {
        LARGE("LARGE"), SMALL("SMALL"),;
        private String value;

        private BankTypeEnum(String value) {
            this.value = value;
        }
    }


}
