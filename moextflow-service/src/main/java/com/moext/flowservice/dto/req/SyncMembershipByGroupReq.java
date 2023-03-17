package com.moext.flowservice.dto.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

/**
 * 按组全量同步同步用户组-用户列表关系
 * @author PengPeng
 */
public class SyncMembershipByGroupReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	@NotNull(message="newUserCodeList不能为null!")
	private List<String> newUserCodeList;
	
	@NotBlank(message="groupId不能为空!")
	private String groupId;

	public List<String> getNewUserCodeList() {
		return newUserCodeList;
	}

	public void setNewUserCodeList(List<String> newUserCodeList) {
		this.newUserCodeList = newUserCodeList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
