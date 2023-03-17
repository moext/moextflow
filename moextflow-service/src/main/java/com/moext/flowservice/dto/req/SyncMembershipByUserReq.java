package com.moext.flowservice.dto.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

/**
 * 按用户全量同步同步用户-用户组列表关系
 * @author PengPeng
 */
public class SyncMembershipByUserReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	@NotBlank(message="userCode不能为空!")
	private String userCode;
	
	@NotNull(message="newGroupIdList不能为null!")
	private List<String> newGroupIdList;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<String> getNewGroupIdList() {
		return newGroupIdList;
	}

	public void setNewGroupIdList(List<String> newGroupIdList) {
		this.newGroupIdList = newGroupIdList;
	}
}
