package com.moext.flowservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.component.IdentityComponent;
import com.moext.flowservice.dto.req.GroupIdReq;
import com.moext.flowservice.dto.req.GroupReq;
import com.moext.flowservice.dto.req.MembershipReq;
import com.moext.flowservice.dto.req.SyncMembershipByGroupReq;
import com.moext.flowservice.dto.req.SyncMembershipByUserReq;
import com.moext.flowservice.dto.req.UserCodeReq;
import com.moext.flowservice.dto.req.UserReq;
import com.moext.flowservice.flow.model.FlowGroup;
import com.moext.flowservice.flow.model.FlowUser;

/**
 * 用户与用户组接口
 * 
 * @author PengPeng
 */
@RequestMapping("/identity")
@RestController
public class IdentityController {

	@Autowired
	private IdentityComponent identityComponent;

	/**
	 * 保存用户，如果用户不存在则新增，如果存在则更新 适用场景：业务系统新增或修改用户时
	 * 
	 * @param request
	 * @param userReq
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> saveUser(HttpServletRequest request, @RequestBody UserReq userReq) {
		String validateMsg = RspUtils.validate(userReq, UserReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.saveUser(FlowUser.instanceOf(userReq)));
	}

	/**
	 * 删除用户，如果用户不存在则忽略。删除用户后，用户与用户组关系也同时删除。 适用场景：业务系统删除用户时
	 * 
	 * @param request
	 * @param userCodeReq
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> deleteUser(HttpServletRequest request, @RequestBody UserCodeReq userCodeReq) {
		String validateMsg = RspUtils.validate(userCodeReq, UserCodeReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.deleteUser(userCodeReq.getUserCode()));
	}

	/**
	 * 保存分组，如果分组不存在则新增，如果存在则更新 适用场景：业务系统新增或修改工作流角色时
	 * 
	 * @param request
	 * @param groupReq
	 * @return
	 */
	@RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> saveGroup(HttpServletRequest request, @RequestBody GroupReq groupReq) {
		String validateMsg = RspUtils.validate(groupReq, GroupReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.saveGroup(FlowGroup.instanceOf(groupReq)));
	}

	/**
	 * 删除分组，如果分组不存在则忽略。删除分组后，用户与用户分组关系也同时删除。 适用场景：业务系统删除工作流角色时
	 * 
	 * @param request
	 * @param groupIdReq
	 * @return
	 */
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> deleteGroup(HttpServletRequest request, @RequestBody GroupIdReq groupIdReq) {
		String validateMsg = RspUtils.validate(groupIdReq, GroupIdReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.deleteGroup(groupIdReq.getGroupId()));
	}

	/**
	 * 用户与用户组关系绑定，当关系已存在时忽略，当用户或用户组不存在时抛出运行时异常 适用场景：业务系统新增或修改单个用户所属角色时
	 * 
	 * @param request
	 * @param membershipReq
	 * @return
	 */
	@RequestMapping(value = "/createMembership", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> createMembership(HttpServletRequest request,
			@RequestBody MembershipReq membershipReq) {
		String validateMsg = RspUtils.validate(membershipReq, MembershipReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils
				.success(identityComponent.createMembership(membershipReq.getUserCode(), membershipReq.getGroupId()));
	}

	/**
	 * 用户与用户组关系解除，当用户或用户组不存在时忽略 适用场景：业务系统删除单个用户所属角色时
	 * 
	 * @param request
	 * @param membershipReq
	 * @return
	 */
	@RequestMapping(value = "/deleteMembership", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> deleteMembership(HttpServletRequest request,
			@RequestBody MembershipReq membershipReq) {
		String validateMsg = RspUtils.validate(membershipReq, MembershipReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils
				.success(identityComponent.deleteMembership(membershipReq.getUserCode(), membershipReq.getGroupId()));
	}

	/**
	 * 按用户全量同步用户-用户组关系 注意：新用户组列表不能为null，可为空，为空时则删除该用户下的所有的用户-用户组关系
	 * 适用场景：业务系统新增、修改或删除用户所属全量角色时
	 * 
	 * @param request
	 * @param syncMembershipByUserReq
	 * @return
	 */
	@RequestMapping(value = "/syncMembershipByUser", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> syncMembershipByUser(HttpServletRequest request,
			@RequestBody SyncMembershipByUserReq syncMembershipByUserReq) {
		String validateMsg = RspUtils.validate(syncMembershipByUserReq, SyncMembershipByUserReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.syncMembershipByUser(syncMembershipByUserReq.getUserCode(),
				syncMembershipByUserReq.getNewGroupIdList()));
	}

	/**
	 * 按用户组全量同步用户组-用户关系 注意：新用户列表不能为null，可为空，为空时则删除该用户组下所有用户-用户组关系
	 * 适用场景：业务系统新增、修改或删除角色所属全量用户时
	 * 
	 * @param request
	 * @param syncMembershipByGroupReq
	 * @return
	 */
	@RequestMapping(value = "/syncMembershipByGruop", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> syncMembershipByGruop(HttpServletRequest request,
			@RequestBody SyncMembershipByGroupReq syncMembershipByGroupReq) {
		String validateMsg = RspUtils.validate(syncMembershipByGroupReq, SyncMembershipByGroupReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(identityComponent.syncMembershipByGroup(syncMembershipByGroupReq.getGroupId(),
				syncMembershipByGroupReq.getNewUserCodeList()));
	}

}
