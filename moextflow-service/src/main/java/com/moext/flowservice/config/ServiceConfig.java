package com.moext.flowservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用配置
 * @author PengPeng
 */
@Component
@ConfigurationProperties(prefix = "app.service")
public class ServiceConfig {

	private Boolean shutdownHook;
	
	private String accessToken;

	public Boolean getShutdownHook() {
		return shutdownHook;
	}

	public void setShutdownHook(Boolean shutdownHook) {
		this.shutdownHook = shutdownHook;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
