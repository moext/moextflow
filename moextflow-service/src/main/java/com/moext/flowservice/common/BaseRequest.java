package com.moext.flowservice.common;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 请求基类
 * 
 * @author PengPeng
 */
public class BaseRequest implements Serializable {

	private static final long serialVersionUID = -5486218059780222108L;

	@NotBlank(message = "流水号不能为空")
	private String flowNo;

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
