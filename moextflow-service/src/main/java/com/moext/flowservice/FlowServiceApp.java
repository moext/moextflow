package com.moext.flowservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.moext.flowservice.config.ServiceConfig;
import com.moext.flowservice.inteceptor.TokenCheckInterceptor;
/**
 * 运行类
 * @author PengPeng
 *
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.moext.flowservice"})
@Configuration
@EnableWebMvc
@MapperScan(basePackages= {"com.moext.flowservice.data.gen.dao"})
public class FlowServiceApp implements WebMvcConfigurer {

	@Autowired
	private ServiceConfig serviceConfig;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		TokenCheckInterceptor tokenCheckInterceptor = new TokenCheckInterceptor(serviceConfig.getAccessToken());
		registry.addInterceptor(tokenCheckInterceptor).addPathPatterns("/**");
	}
	
	public static void main(String[] args){ 
		SpringApplication.run(FlowServiceApp.class, args);
	}
}

