package com.ipanel.web.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author: lvchao
 * @mail: chao9038@hnu.edu.cn
 * @time: 2018年4月16日下午8:08:04
 *
 * @description: bean、map之间相互转换
 */
public class BeanUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);
	
	
    /**
     * Map --> Bean: 利用Introspector, PropertyDescriptor
     *  
     * @author lvchao
     * @createtime 2018年4月17日 上午9:05:03
     *
     * @param map
     * @param obj
     */
    public static void map2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    Method setter = property.getWriteMethod(); // 得到property对应的setter方法
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
        	LOGGER.error("Convert Map to Bean Error!", e);
        }
    }
    

    /**
     * Bean --> Map: 利用Introspector, PropertyDescriptor
     *
     * @author lvchao
     * @createtime 2018年4月17日 上午9:31:17
     *
     * @param obj
     * @param map
     */
    public static void bean2Map(Object obj, Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) { // 过滤class属性
                    Method getter = property.getReadMethod(); // 得到property对应的getter方法
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
        	LOGGER.error("Convert Bean To Map Error!", e);
        }
    }
    
}