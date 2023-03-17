package com.moext.flowservice.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 响应基类
 * @author PengPeng
 */
public class BaseResponse<T> implements Serializable {
	
	private static final long serialVersionUID = -7941277334597793217L;
	
	//响应码
	private Integer code;
	
	//响应消息
	private String message;
	
	//响应体
	private T data;
	
	private BaseResponse(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 使用Builder模式让客户端调用时方便形成链式调用
	 * @author pengPeng
	 */
	public static class Builder<T> {
		private Integer code;
		private String message;
		private T data;
		
		public Integer getCode() {
			return code;
		}

		public Builder<T> setCode(Integer code) {
			this.code = code;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public Builder<T> setMessage(String message) {
			this.message = message;
			return this;
		}

		public T getData() {
			return data;
		}

		public Builder<T> setData(T data) {
			this.data = data;
			return this;
		}

		public Builder(int code) {
			this.code = code;
		}
		
		public BaseResponse<T> build(){
			return new BaseResponse<T>(this.code, this.message, this.data);
		}
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
