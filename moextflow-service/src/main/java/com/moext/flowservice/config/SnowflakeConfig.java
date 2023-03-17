package com.moext.flowservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Snowflake配置
 * @author PengPeng
 */
@Component
@ConfigurationProperties(prefix = "app.snowflake")
public class SnowflakeConfig {

	private Integer datacenterId;
	
	private Integer workerId;
	
	public Integer getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(Integer datacenterId) {
		this.datacenterId = datacenterId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
}
