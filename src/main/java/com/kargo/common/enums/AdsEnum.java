package com.kargo.common.enums;

import lombok.Getter;

/**
 *
 * @author abner.zhang
 */
public interface AdsEnum {
    @Getter
    public enum CostTypeEnum {
        CPC("CPC"), CPM("CPM"),;
        private String value;

        private CostTypeEnum(String value) {
            this.value = value;
        }
    }

    @Getter
    public enum ExpiredTypeEnum {
        DATE("DATE"), FUND("FUND"),;
        private String value;

        private ExpiredTypeEnum(String value) {
            this.value = value;
        }
    }

}
