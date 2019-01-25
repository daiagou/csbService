package com.kargo.common.annotations;

import com.kargo.common.enums.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 活动状态拦截的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginPermission {

    /**
     * 需要的角色，不填表示不需要
     * @return
     */
    BaseEnum.RoleEnum[] needRoles() default {};

}
