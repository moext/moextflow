package com.moext.flowservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moext.flowservice.config.SnowflakeConfig;

/**
 * SnowFlake工具类
 * @author PengPeng
 */
public class SnowFlakeUtils {

	private static final Logger logger = LoggerFactory.getLogger(SnowFlakeUtils.class);
	
	static SnowflakeIdWorker idWorker = null;
	
	private static int workerId;
	private static int datacenterId;
	

	public static int getWorkerId() {
		return workerId;
	}

	public static int getDatacenterId() {
		return datacenterId;
	}

	public static void init(SnowflakeConfig snowflakeConfig) {
		workerId = snowflakeConfig.getWorkerId();
		datacenterId = snowflakeConfig.getDatacenterId();
		idWorker = new SnowflakeIdWorker(workerId, datacenterId);
		logger.info("Snowflake workId is {}, datacenterId is {}", workerId, datacenterId);
	}
	
	/**
	 * 获取一个随机ID
	 * @return
	 */
	public static Long nextId() {
		return idWorker.nextId();
	}
	
	/**
	 * 获取一个随机ID
	 * @return
	 */
	public static String nextIdString() {
		return nextId() + "";
	}
}
