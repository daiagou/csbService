package com.kargo.common.util;

import com.kargo.common.annotations.NotBlank;
import com.kargo.common.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查参数
 */
public class CheckArgsUtil {

    /**
     * 检查notnull的属性是否有值，（如果没有提供get方法，出现异常，也视为属性值为空）
     *
     * @return true(所有@notnull的值都不为空)
     */
    public static boolean checkArgs(Object target) {
        Field[] fields = target.getClass().getDeclaredFields();
        String className = target.getClass().getName();
        for (Field field : fields) {
            if (field.getAnnotation(NotNull.class) != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) == null) {
                        return false;
                    }
                    //List
                    if (field.get(target) instanceof List) {
                        for (Object obj : (List) field.get(target)) {
                            if (!checkArgs(obj)) {
                                return false;
                            }
                        }
                    }
                    //如果不是java自带的类型，需要递归
                    if (!field.getType().getName().startsWith("java.")) {
                        if (!checkArgs(field.get(target))) {
                            return false;
                        }
                    }

                } catch (Exception e) {
                    return false;
                }
            }

            if (field.getAnnotation(NotBlank.class) != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) == null) {
                        return false;
                    }
                    if ((field.get(target) instanceof String) && field.get(target).toString().trim().equals("")) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }



    /**
     * 检查notnull的属性是否有值，（如果没有提供get方法，出现异常，也视为属性值为空）
     *
     * @return true(所有@notnull的值都不为空)
     */
    public static Map<String,String> checkArgsMap(Object target) {
        Map<String,String> map = new HashMap<>();
        Field[] fields = target.getClass().getDeclaredFields();
        String className = target.getClass().getName();
        for (Field field : fields) {
            if (field.getAnnotation(NotNull.class) != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) == null) {
                        map.put(field.getName(),field.getName()+"不能为空");
                        return map;
                    }
                    //List
                    if (field.get(target) instanceof List) {
                        for (Object obj : (List) field.get(target)) {
                            if (!checkArgs(obj)) {
                                map.put(field.getName(),field.getName()+"不能为空");
                                return map;
                            }
                        }
                    }
                    //如果不是java自带的类型，需要递归
                    if (!field.getType().getName().startsWith("java.")) {
                        if (!checkArgs(field.get(target))) {
                            map.put(field.getName(),field.getName()+"不能为空");
                            return map;
                        }
                    }

                } catch (Exception e) {
                    map.put(field.getName(),field.getName()+"参数异常");
                    return map;
                }
            }

            if (field.getAnnotation(NotBlank.class) != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) == null) {
                        map.put(field.getName(),field.getName()+"不能为空");
                        return map;
                    }
                    if ((field.get(target) instanceof String) && field.get(target).toString().trim().equals("")) {
                        map.put(field.getName(),field.getName()+"不能为空");
                        return map;
                    }
                } catch (Exception e) {
                    map.put(field.getName(),field.getName()+"参数异常");
                    return map;
                }
            }
        }
        return null;
    }
}
