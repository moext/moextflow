package com.moext.flowservice.component;

import java.util.List;

import com.moext.flowservice.flow.model.FlowGroup;
import com.moext.flowservice.flow.model.FlowUser;

/**
 * 用户组件
 * 
 * @author PengPeng
 */
public interface IdentityComponent {

	/**
	 * 按用户标识查询用户姓名，如果用户不存在则返回默认用户姓名
	 * 
	 * @param userCode           用户标识
	 * @param defaultDisplayName 默认用户姓名
	 * @return
	 */
	public String getUserDisplayNameByCode(String userCode, String defaultDisplayName);

	/**
	 * 按用户标识查询用户
	 * 
	 * @param userCode
	 * @return
	 */
	public FlowUser getUserByCode(String userCode);

	/**
	 * 按分组标识查用户列表
	 * 
	 * @param groupId
	 * @return
	 */
	public List<FlowUser> listUserByGroupId(String groupId);

	/**
	 * 按分组标识查询分组
	 * 
	 * @param groupId
	 * @return
	 */
	public FlowGroup getGroupById(String groupId);

	/**
	 * 保存分组，如果分组不存在则保存，如果不存在则更新
	 * 
	 * @param group
	 */
	public boolean saveGroup(FlowGroup group);

	/**
	 * 删除分组，如果分组不存在则忽略
	 * 
	 * @param groupId
	 */
	public boolean deleteGroup(String groupId);

	/**
	 * 保存用户，如果用户存在则保存，如果不存在则更新
	 * 
	 * @param user
	 */
	public boolean saveUser(FlowUser user);

	/**
	 * 删除用户，如果用户不存在则忽略
	 * 
	 * @param userCode
	 */
	public boolean deleteUser(String userCode);

	/**
	 * 用户与用户组关系绑定，当关系已存在时忽略，当用户或用户组不存在时抛出运行时异常
	 * 
	 * @param userCode
	 * @param groupId
	 * @return false如果关系已存在
	 */
	public boolean createMembership(String userCode, String groupId);

	/**
	 * 用户与用户组关系解除，当用户或用户组不存在时忽略
	 * 
	 * @param userCode
	 * @param groupId
	 */
	public boolean deleteMembership(String userCode, String groupId);

	/**
	 * 按用户同步全量用户与用户组关系
	 * 
	 * @param userCode       用户名
	 * @param newGroupIdList 该用户名下全量用户组列表
	 * @return
	 */
	public boolean syncMembershipByUser(String userCode, List<String> newGroupIdList);

	/**
	 * 按用户组全量同步用户与用户组关系
	 * 
	 * @param groupId         用户组标识
	 * @param newUserCodeList 该用户组下的全量用户名列表
	 * @return
	 */
	public boolean syncMembershipByGroup(String groupId, List<String> newUserCodeList);

}
