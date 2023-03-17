package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 用户ID请求
 * @author PengPeng
 */
public class UserCodeReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	@NotBlank(message="userCode不能为空!")
	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
