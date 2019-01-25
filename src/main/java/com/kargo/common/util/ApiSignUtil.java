package com.kargo.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.kargo.common.annotations.ApiSignFieldNoEncrypt;

/**
 * 请求参数加密,验证
 * 
 * @author abner.zhang
 *
 */
public abstract class ApiSignUtil {

	private static Logger logger = LoggerFactory.getLogger(ApiSignUtil.class);

	/**
	 * 获取OBJ的所有private字段 包括父类作为加密验签值（重要）
	 * 同时API_SIGN不再加密范围内，字段必须包括API_SIGN，API_KEY
	 * 
	 * @param obj
	 * @param secretKey
	 * @return 验签成功
	 */
	public static boolean checkSign(Object obj, String secretField, String secretKey) {
		try {
			String value = getApiSignByObjPrivate(obj, secretKey);
			if (StringUtils.isBlank(value)) {
				return false;
			}
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), secretField);
			Method readMethod = pd.getReadMethod();
			Object readValue = readMethod.invoke(obj);
			if (value.equalsIgnoreCase(readValue.toString())) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 获取OBJ的所有private字段 包括父类作为加密验签值（重要）
	 * 同时API_SIGN不再加密范围内，字段必须包括API_SIGN，API_KEY
	 * 比上面加密的时候多增加了字段名
	 * @param obj
	 * @param secretKey
	 * @return 验签成功
	 */
	public static boolean checkSignWithField(Object obj, String secretField, String secretKey) {
		try {
			String value = getApiSignByObjPrivateWithFiled(obj, secretKey);
			if (StringUtils.isBlank(value)) {
				return false;
			}
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), secretField);
			Method readMethod = pd.getReadMethod();
			Object readValue = readMethod.invoke(obj);
			if (value.equalsIgnoreCase(readValue.toString())) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	

	/**
	 * 获取OBJ的所有private字段 包括父类作为加密验签值（重要）
	 * 同时API_SIGN不再加密范围内，字段必须包括API_SIGN，API_KEY
	 * 
	 * @param obj
	 * @param secretKey
	 * @return md5(value)
	 */
	public static String getApiSignByObjPrivate(Object obj, String secretKey) {
		if (obj == null) {
			logger.error("加密验签-obj为空");
			return null;
		}
		if (StringUtils.isBlank(secretKey)) {
			logger.error("加密验签-secretKey为空");
			return null;
		}
		Map<String, String> data = getMapByProperties(obj);
		if (data != null) {
			String value = getApiSignWithUsefulData(data, secretKey);
			logger.info("加密验签-对象转换结果为[{},{}]", JSONObject.toJSONString(obj), value);
			return value;
		}
		return null;
	}
	
	
	/**
	 * 获取OBJ的所有private字段 包括父类作为加密验签值（重要）
	 * 同时API_SIGN不再加密范围内，字段必须包括API_SIGN，API_KEY
	 * 比上面加密的时候多增加了字段名
	 * @param obj
	 * @param secretKey
	 * @return md5(value)
	 */
	public static String getApiSignByObjPrivateWithFiled(Object obj, String secretKey) {
		if (obj == null) {
			logger.error("加密验签-obj为空");
			return null;
		}
		if (StringUtils.isBlank(secretKey)) {
			logger.error("加密验签-secretKey为空");
			return null;
		}
		Map<String, String> data = getMapByProperties(obj);
		if (data != null) {
			String value = getApiSignWithUsefulDataWithFiled(data, secretKey);
			logger.info("加密验签-对象转换结果为[{},{}]", JSONObject.toJSONString(obj), value);
			return value;
		}
		return null;
	}
	

	public static Map<String, String> getMapByProperties(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<Field> list = new ArrayList<Field>();
		Class<?> clazz = obj.getClass();
		for (; clazz != Object.class;) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					list.add(fields[i]);
				}
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		for (Field field : list) {
			if (field.getModifiers() == Modifier.PRIVATE && !(field.isAnnotationPresent(ApiSignFieldNoEncrypt.class))) {
				field.setAccessible(true);
				try {
					Object value = field.get(obj);
					if (value != null) {
						map.put(field.getName(), String.valueOf(value));
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return map;
	}

	/**
	 * 获取 api_sign MD5值
	 * 
	 * @param obj
	 *            Object
	 * @param keys
	 *            需要追加到加密的字段名称数组
	 * @return
	 */
	public static String getApiSign(Object obj, String... keys) {
		try {
			Map<String, Object> data = getMapFromObj(obj);
			return getApiSign(data, keys);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取 api_sign MD5值
	 * 
	 * @param obj
	 *            Object
	 * @param keys
	 *            不需要追加到加密的字段名称数组
	 * @return
	 */
	public static String getApiSignDes(Object obj, String... keys) {
		try {
			Map<String, Object> data = getMapFromObj(obj);
			return getApiSignDes(data, keys);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将对象中的属性映射到 map 中
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> getMapFromObj(Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class clazz = obj.getClass();
		PropertyDescriptor[] clazzProperties = BeanUtils.getPropertyDescriptors(clazz);
		Map<String, Object> data = new HashMap<String, Object>();
		for (int i = 0; i < clazzProperties.length; i++) {
			String key = clazzProperties[i].getName();
			if (key.equals("class")) { // 去除构造方法属性
				continue;
			}
			Method readMethod = clazzProperties[i].getReadMethod();
			Object readValue = readMethod.invoke(obj);
			data.put(key, readValue.toString());
		}
		return data;
	}

	/**
	 * 获取 api_sign MD5值
	 * 
	 * @param data
	 * @param keys
	 *            需要追加到加密的字段名称数组
	 * @return
	 */
	public static String getApiSign(Map<String, Object> data, String... keys) {
		Map<String, String> usefulData = getUsefulParam(data, keys);
		return getApiSignWithUsefulData(usefulData, null);
	}

	/**
	 * 获取 api_sign MD5值
	 * 
	 * @param data
	 * @param keys
	 *            不需要追加到加密的字段名称数组
	 * @return
	 */
	public static String getApiSignDes(Map<String, Object> data, String... keys) {
		Map<String, String> usefulData = getUsefulParamDes(data, keys);
		return getApiSignWithUsefulData(usefulData, null);
	}

	/**
	 * 获取所有 usefulData 中 value值相加的 MD5
	 * 
	 * @param usefulData
	 * @param secretKey
	 * @return
	 */
	private static String getApiSignWithUsefulData(Map<String, String> usefulData, String secretKey) {
		List<String> keySet = new ArrayList<String>(usefulData.keySet());
		Collections.sort(keySet);

		StringBuffer sb = new StringBuffer();
		for (String key : keySet) {
			String value = usefulData.get(key);
			if (!StringUtils.isEmpty(value)) {
				sb.append(value);
			}
		}
		sb.append(secretKey);
		logger.info("md5加密前:[{}]",sb.toString());
		return MD5HashUtil.md5(sb.toString());
	}
	
	/**
	 * 获取所有 usefulData 中 filed value值相加加上secretKey的 MD5
	 * 
	 * @param usefulData
	 * @param secretKey
	 * @return
	 */
	private static String getApiSignWithUsefulDataWithFiled(Map<String, String> usefulData, String secretKey) {
		List<String> keySet = new ArrayList<String>(usefulData.keySet());
		Collections.sort(keySet);

		StringBuffer sb = new StringBuffer();
		for (String key : keySet) {
			String value = usefulData.get(key);
			if (!StringUtils.isEmpty(value)) {
				sb.append(key);//比上面的函数多了key
				sb.append(value);
			}
		}
		sb.append(secretKey);
		logger.info("md5加密前:[{}]",sb.toString());
		return MD5HashUtil.md5(sb.toString());
	}
	

	/**
	 * 将有用的参数提取出来
	 * 
	 * @param signParams
	 * @param signParamList
	 *            需要的字段
	 * @return
	 */
	private static Map<String, String> getUsefulParam(Map<String, Object> data, String... signParamList) {
		Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < signParamList.length; i++) {
			String key = signParamList[i];
			String value = data.get(key).toString();
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 将排除不需要的字段之后的有用的参数提取出来
	 * 
	 * @param signParams
	 * @param signParamDesList
	 *            不需要的字段
	 * @return
	 */
	private static Map<String, String> getUsefulParamDes(Map<String, Object> data, String... signParamDesList) {
		Map<String, String> map = new TreeMap<String, String>(); // data 复制 map
		Set<String> set = data.keySet();
		for(String item:set){
			map.put(item, data.get(item).toString());
		}
		for (int i = 0; i < signParamDesList.length; i++) {
			String key = signParamDesList[i];
			if (map.containsKey(key)) {
				map.remove(key);
			}
		}
		return map;
	}

	public static void main(String[] args) {

	}
}
