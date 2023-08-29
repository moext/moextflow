package com.moext.flowservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 应用优雅退出方法
 * 
 * @author PengPeng
 */
@Component
public class AppExitGracefully implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(AppExitGracefully.class);

	// 当前程序是否停止运行
	private volatile static boolean stop;

	public static boolean isStop() {
		return stop;
	}

	public static void setStop(boolean stop) {
		AppExitGracefully.stop = stop;
	}

	@Override
	public void run() {
		AppExitGracefully.setStop(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
		logger.info("Exit app success");
	}
}
