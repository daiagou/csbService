package com.kargo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by abner.zhang on 2018/8/9.
 */
public class MyBeanUtil {

    /**
     *
     * @param bean
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void convertEmptyToNull(Object bean)  {
        try {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo
                    (type);
            PropertyDescriptor[] propertyDescriptors =
                    beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length;
                 i++) {
                PropertyDescriptor descriptor =
                        propertyDescriptors[i];
                String propertyName = descriptor.getName
                        ();
                if (!propertyName.equals("class")) {
                    Method readMethod =
                            descriptor.getReadMethod();
                    Object result = readMethod.invoke
                            (bean, new Object[0]);
                    if ((result instanceof  String) && StringUtils.isBlank((String)result)) {
                        descriptor.getWriteMethod().invoke
                                (bean, new String[]{null});
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
