package com.moext.flowservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moext.flowservice.config.ServiceConfig;
import com.moext.flowservice.config.SnowflakeConfig;
import com.moext.flowservice.util.SnowFlakeUtils;

/**
 * 系统初始化类
 * 
 * @author PengPeng
 */
@Component
public class SystemInitBean implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(SystemInitBean.class);

	@Autowired
	private ServiceConfig serviceConfig;

	@Autowired
	private SnowflakeConfig snowflakeConfig;

	@Autowired
	private AppExitGracefully appExitGracefully;

	public void afterPropertiesSet() throws Exception {
		// 启用优雅停机
		if (serviceConfig.getShutdownHook()) {
			Runtime.getRuntime().addShutdownHook(new Thread(appExitGracefully));
		}
		logger.info("init shutdownHook success.");
		// 初始化SnowFlake
		SnowFlakeUtils.init(snowflakeConfig);
		logger.info("init snowflake success.");
	}
}
