package com.kargo.common.constant;

/**
 * 
 * @author abner.zhang
 *
 */
public interface CacheKeyConstant {
	String CACHE_DEAFAULT = "default";
	String KEY_SEPERATOR = ":";

	String KEY_USER_INFO = "usr:info";
	String KEY_USER_INFO_H5 = "usr:info:h5";
	String KEY_USER_ACCOUNT_INFO = "usr:acc";
	String KEY_USER_SMS = "usr:sms";
	String KEY_POINT_TASK_LIST = "points:task:jrhd:list";
	String KEY_USER_CARD = "usr:card";

	String KEY_USER_SSID = "usr:ssid";

	// insurance
	String KEY_INSURANCE_BALANCE = "ins:bal";

	String KEY_INSURANCE_BALANCE_LOCK = "ins:bal:lock";

	// P2P
	String KEY_P2P_TOTAL_AMOUNT = "p2p:total:amount";
	String KEY_P2P_TOTAL_USER = "p2p:total:user";
	String KEY_P2PPRODUCT = "p2p:product";

	/** foss产品列表 "p2p:product:list":"[value1,value2]" **/
	String KEY_P2P_PRODUCT_LIST = "p2p:product:list";
	/** foss产品详细信息 "p2p:product:detail:codexxx":"value" **/
	String KEY_P2P_PRODUCT_DETAIL = "p2p:product:detail";

	// insurance

	String KEY_INSURANCE_FB = "ins:fb";

	String KEY_EUROPE_INFO = "act:europe:info";

	String KEY_EUROPE_PROD = "act:europe:prod";

	String KEY_TASK_ACTIVITY_COUNT_PERSISTENT = "task:activity:count:persistent";

	String KEY_TASK_TOP_TRANS_PERSISTENT = "task:top:trans:persistent";

	String KEY_ISOVER = "isover";

	String KEY_LOCK = "islock";

	/** ==================以下为公共部分===================== */
	/**
	 * 缓存键值分隔
	 */
	String CACHE_KEY_SEPARATOR = "_";
	/**
	 * 1小时的秒数
	 */
	long SECONDS_PER_HOUR = 3600;

	/**
	 * 10分钟的秒数
	 */
	long SECONDS_OF_10_MINUTES = 600;

	/**
	 * 半小时的秒数
	 */
	long SECONDS_HALF_HOUR = 1800;

	/**
	 * 按分钟，1天24小时
	 */
	Long DAY_OF_MINUTES = 24L * 60;

	/**
	 * 按秒钟，1天24小时
	 */
	Long DAY_OF_SECOND = 24L * 60 * 60;

	/**
	 * 按秒钟，1周
	 */
	Long WEEK_OF_SECOND = 7 * 24L * 60 * 60;

	Long DAY = 30L;

	Long HOUR = 24L;

	Long MINUTES = 5L;

	Long DEFAULT_INCE = 1L;

	String REDIS_KEY = "JINRONG:INSUREANCE:ACTIVITY:EUROPE:COUNT:";
}
