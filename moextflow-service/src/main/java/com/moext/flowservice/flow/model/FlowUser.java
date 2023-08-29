package com.moext.flowservice.flow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.User;

import com.moext.flowservice.dto.req.UserReq;

/**
 * 工作流用户
 * 
 * @author PengPeng
 */
public class FlowUser implements Serializable {

	private static final long serialVersionUID = 2562087378597053182L;

	// 用户名
	private String userCode;

	// 姓名
	private String displayName;

	// 邮箱
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

	public static FlowUser instanceOf(UserReq userReq) {
		if (userReq == null) {
			throw new IllegalArgumentException("参数userReq不能为空");
		}
		FlowUser item = new FlowUser();
		item.setUserCode(userReq.getUserCode());
		item.setDisplayName(userReq.getDisplayName());
		item.setEmail(userReq.getEmail());
		return item;
	}

	public static FlowUser instancesOf(User user) {
		if (user == null) {
			return null;
		}
		FlowUser item = new FlowUser();
		item.setUserCode(user.getId());
		item.setDisplayName(user.getDisplayName());
		item.setEmail(user.getEmail());
		return item;
	}

	public static List<FlowUser> instancesOf(List<User> userList) {
		if (userList == null) {
			return null;
		}
		List<FlowUser> itemList = new ArrayList<FlowUser>();
		for (org.flowable.idm.api.User user : userList) {
			if (user != null) {
				itemList.add(instancesOf(user));
			}
		}
		return itemList;
	}
}
