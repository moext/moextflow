package com.moext.flowservice.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 远程调用工具类
 * 
 * @author PengPeng
 */
public class RestTemplateUtils {

	private static final RestTemplate restTemplate = new RestTemplate();

	static {
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
	}

	// ----------------------------------GET-------------------------------------------------------

	/**
	 * GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, Class<T> responseType) {
		return restTemplate.getForEntity(url, responseType);
	}

	/**
	 * GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... uriVariables) {
		return restTemplate.getForEntity(url, responseType, uriVariables);
	}

	/**
	 * GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		return restTemplate.getForEntity(url, responseType, uriVariables);
	}

	/**
	 * 带请求头的GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType,
			Object... uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return get(url, httpHeaders, responseType, uriVariables);
	}

	/**
	 * 带请求头的GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		return exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return get(url, httpHeaders, responseType, uriVariables);
	}

	/**
	 * 带请求头的GET请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		return exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
	}

	// ----------------------------------POST-------------------------------------------------------

	/**
	 * POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @return
	 */
	public static <T> ResponseEntity<T> post(String url, Class<T> responseType) {
		return restTemplate.postForEntity(url, HttpEntity.EMPTY, responseType);
	}

	/**
	 * POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType) {
		return restTemplate.postForEntity(url, requestBody, responseType);
	}

	/**
	 * POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType,
			Object... uriVariables) {
		return restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
	}

	/**
	 * POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType,
			Map<String, ?> uriVariables) {
		return restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Object... uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return post(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return post(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return post(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的POST请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return post(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的POST请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables) {
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的POST请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Map<String, ?> uriVariables) {
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
	}

	// ----------------------------------PUT-------------------------------------------------------

	/**
	 * PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, Class<T> responseType, Object... uriVariables) {
		return put(url, HttpEntity.EMPTY, responseType, uriVariables);
	}

	/**
	 * PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
		return put(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, Object requestBody, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
		return put(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Object... uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return put(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return put(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return put(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的PUT请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return put(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的PUT请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables) {
		return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的PUT请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> put(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Map<String, ?> uriVariables) {
		return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
	}

	// ----------------------------------DELETE-------------------------------------------------------

	/**
	 * DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Class<T> responseType, Object... uriVariables) {
		return delete(url, HttpEntity.EMPTY, responseType, uriVariables);
	}

	/**
	 * DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		return delete(url, HttpEntity.EMPTY, responseType, uriVariables);
	}

	/**
	 * DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Object requestBody, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType,
			Object... uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return delete(url, httpHeaders, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType,
			Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return delete(url, httpHeaders, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType,
			Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Object... uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return delete(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody,
			Class<T> responseType, Object... uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, Map<String, String> headers, Object requestBody,
			Class<T> responseType, Map<String, ?> uriVariables) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		return delete(url, httpHeaders, requestBody, responseType, uriVariables);
	}

	/**
	 * 带请求头的DELETE请求调用方式
	 * 
	 * @param url          请求URL
	 * @param headers      请求头参数
	 * @param requestBody  请求参数体
	 * @param responseType 返回对象类型
	 * @param uriVariables URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody,
			Class<T> responseType, Map<String, ?> uriVariables) {
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
		return delete(url, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的DELETE请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables) {
		return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
	}

	/**
	 * 自定义请求头和请求体的DELETE请求调用方式
	 * 
	 * @param url           请求URL
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> delete(String url, HttpEntity<?> requestEntity, Class<T> responseType,
			Map<String, ?> uriVariables) {
		return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
	}

	// ----------------------------------通用方法-------------------------------------------------------

	/**
	 * 通用调用方式
	 * 
	 * @param url           请求URL
	 * @param method        请求方法类型
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，按顺序依次对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables) {
		return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
	}

	/**
	 * 通用调用方式
	 * 
	 * @param url           请求URL
	 * @param method        请求方法类型
	 * @param requestEntity 请求头和请求体封装对象
	 * @param responseType  返回对象类型
	 * @param uriVariables  URL中的变量，与Map中的key对应
	 * @return ResponseEntity 响应对象封装类
	 */
	public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Map<String, ?> uriVariables) {
		return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
	}

	/**
	 * 获取RestTemplate实例对象，可自由调用其方法
	 * 
	 * @return RestTemplate实例对象
	 */
	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param headers
	 * @param filePath
	 * @return
	 */
	public static File downloadFile(String url, Map<String, String> headers, String filePath) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(headers);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
		ResponseEntity<byte[]> response = exchange(url, HttpMethod.GET, requestEntity, byte[].class);
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(response.getBody());
			fos.flush();
			fos.close();
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String url = "http://localhost:9072/file/download/708f25ce2a284ffbb15697bcb735da60";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("ACCESS_TOKEN", "da1ecc2ca7b549b680a2808ca68c861f");
		String filePath = "D:/test/abcdef";
		downloadFile(url, headers, filePath);
	}
}