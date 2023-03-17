package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 用户请求
 * @author PengPeng
 */
public class UserReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	//用户名
	@NotBlank(message="userCode不能为空!")
	private String userCode;
	
	//姓名
	@NotBlank(message="displayName不能为空!")
	private String displayName;
	
	//邮箱
	private String email;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
