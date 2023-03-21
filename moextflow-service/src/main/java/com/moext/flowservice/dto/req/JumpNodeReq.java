package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 节点跳转请求
 * @author PengPeng
 *
 */
public class JumpNodeReq extends BaseRequest {

	private static final long serialVersionUID = -3549422377974819190L;

	@NotBlank(message="procInsId不能为空!")
	private String procInsId;

	@NotBlank(message="toTaskDefKey不能为空!")
	private String toTaskDefKey;

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getToTaskDefKey() {
		return toTaskDefKey;
	}

	public void setToTaskDefKey(String toTaskDefKey) {
		this.toTaskDefKey = toTaskDefKey;
	}

}
