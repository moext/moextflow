package com.moext.flowservice.component.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.moext.flowservice.component.IdentityComponent;
import com.moext.flowservice.flow.model.FlowGroup;
import com.moext.flowservice.flow.model.FlowUser;

/**
 * 用户组件的flowable实现
 * 
 * @author PengPeng
 */
@Component
public class FlowableIdentityComponent implements IdentityComponent {

	@Override
	public String getUserDisplayNameByCode(String userCode, String defaultDisplayName) {
		FlowUser user = getUserByCode(userCode);
		if (user != null) {
			return user.getDisplayName();
		} else {
			return defaultDisplayName;
		}
	}

	@Autowired
	private IdentityService identityService;

	@Override
	public FlowUser getUserByCode(String userCode) {
		User user = identityService.createUserQuery().userId(userCode).singleResult();
		if (user == null) {
			throw new IllegalArgumentException("用户ID为[" + userCode + "]的用户不存在");
		}
		return FlowUser.instancesOf(user);
	}

	@Override
	public List<FlowUser> listUserByGroupId(String groupId) {
		List<User> userList = identityService.createUserQuery().memberOfGroup(groupId).list();
		return FlowUser.instancesOf(userList);
	}

	@Override
	public FlowGroup getGroupById(String groupId) {
		org.flowable.idm.api.Group gruop = identityService.createGroupQuery().groupId(groupId).singleResult();
		return FlowGroup.instancesOf(gruop);
	}

	@Override
	public boolean saveGroup(FlowGroup group) {
		org.flowable.idm.api.Group item = identityService.createGroupQuery().groupId(group.getId()).singleResult();
		if (item == null) {
			item = identityService.newGroup(group.getId());
		}
		item.setId(group.getId());
		// 判断字段是否有变动才执行更新操作，提高执行效率
		boolean updateFlag = false;
		if (!StringUtils.equals(item.getType(), group.getType())) {
			item.setType(group.getType());
			updateFlag = true;
		}
		if (!StringUtils.equals(item.getName(), group.getName())) {
			item.setName(group.getName());
			updateFlag = true;
		}
		if (updateFlag == true) {
			identityService.saveGroup(item);
		}
		return true;
	}

	@Override
	public boolean deleteGroup(String groupId) {
		identityService.deleteGroup(groupId);
		return true;
	}

	@Override
	public boolean saveUser(FlowUser user) {
		User item = identityService.createUserQuery().userId(user.getUserCode()).singleResult();
		if (item == null) {
			item = identityService.newUser(user.getUserCode());
		}
		item.setId(user.getUserCode());
		// 判断字段是否有变动才执行更新操作，提高执行效率
		boolean updateFlag = false;
		if (!StringUtils.equals(item.getDisplayName(), user.getDisplayName())) {
			item.setDisplayName(user.getDisplayName());
			updateFlag = true;
		}
		if (!StringUtils.equals(item.getEmail(), user.getEmail())) {
			item.setEmail(user.getEmail());
			updateFlag = true;
		}
		if (!StringUtils.equals(item.getFirstName(), user.getDisplayName())) {
			item.setFirstName(user.getDisplayName());
			updateFlag = true;
		}
		if (!StringUtils.equals(item.getLastName(), user.getDisplayName())) {
			item.setLastName(user.getDisplayName());
			updateFlag = true;
		}
		if (updateFlag == true) {
			identityService.saveUser(item);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(String userCode) {
		identityService.deleteUser(userCode);
		return true;
	}

	@Override
	public boolean createMembership(String userCode, String groupId) {
		User user = identityService.createUserQuery().userId(userCode).memberOfGroup(groupId).singleResult();
		if (user == null) {
			identityService.createMembership(userCode, groupId);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public boolean deleteMembership(String userCode, String groupId) {
		identityService.deleteMembership(userCode, groupId);
		return true;
	}

	@Override
	@Transactional
	public boolean syncMembershipByUser(String userCode, List<String> newGroupIdList) {
		// 引入map避免n*n循环
		// 新用户组标识Map
		Map<String, String> newGroupIdMap = new HashMap<String, String>();
		// 旧用户组标识Map
		Map<String, String> oldGroupIdMap = new HashMap<String, String>();
		for (String newGroupId : newGroupIdList) {
			newGroupIdMap.put(newGroupId, newGroupId);
		}

		// 查出该用户原用户组
		List<Group> oldGroupIdList = identityService.createGroupQuery().groupMember(userCode).list();
		for (Group oldGroup : oldGroupIdList) {
			oldGroupIdMap.put(oldGroup.getId(), oldGroup.getId());
			if (!newGroupIdMap.containsKey(oldGroup.getId())) {// 该旧用户组在新的用户组列表里不存在，则删除。（存在则保持不变）
				identityService.deleteMembership(userCode, oldGroup.getId());
			}
		}

		for (String groupId : newGroupIdList) {
			if (!oldGroupIdMap.containsKey(groupId)) {// 该新用户组在旧用户组中不存在，则新增
				identityService.createMembership(userCode, groupId);
			}
		}

		return true;
	}

	@Override
	@Transactional
	public boolean syncMembershipByGroup(String groupId, List<String> newUserCodeList) {
		// 引入map避免n*n循环
		Map<String, String> newUserCodeMap = new HashMap<String, String>();
		Map<String, String> oldUserCodeMap = new HashMap<String, String>();
		for (String newUserCode : newUserCodeList) {
			newUserCodeMap.put(newUserCode, newUserCode);
		}

		// 查出该用户组下所有用户
		List<User> oldUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (User oldUser : oldUserList) {
			oldUserCodeMap.put(oldUser.getId(), oldUser.getId());
			if (!newUserCodeMap.containsKey(oldUser.getId())) {// 该旧用户在新用户列表里不存在，则删除。（存在则保持不变）
				identityService.deleteMembership(oldUser.getId(), groupId);
			}
		}

		for (String newUserCode : newUserCodeList) {
			if (!oldUserCodeMap.containsKey(newUserCode)) {// 新新用户在旧用户列表里不存在，则新增
				identityService.createMembership(newUserCode, groupId);
			}
		}
		return true;
	}
}
