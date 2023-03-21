package com.moext.flowservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.dto.req.ActProcessPageReq;
import com.moext.flowservice.dto.req.ChangeTaskAssigneeReq;
import com.moext.flowservice.dto.req.JumpNodeReq;
import com.moext.flowservice.flow.model.TaskManageModel;
import com.moext.flowservice.flow.model.TaskNodeModel;
import com.moext.flowservice.service.FlowOpsService;

/**
 * 流程干预接口
 * @author PengPeng
 */
@RequestMapping("/ops")
@RestController
public class FlowOpsController {

	@Autowired
	private FlowOpsService flowOpsService;
	
	/**
	 * 流程干预分页查询列表
	 * @param request
	 * @param actProcessPageReq
	 * @return
	 */
	@RequestMapping(value="/listOpsFlowPage", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<PageResponse<TaskManageModel>> listOpsFlowPage(HttpServletRequest request, @RequestBody ActProcessPageReq actProcessPageReq) {
		String validateMsg = RspUtils.validate(actProcessPageReq, ActProcessPageReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		PageResponse<TaskManageModel> response = flowOpsService.listOpsFlowPage(actProcessPageReq);
		return RspUtils.success(response);
	}
	
	/**
	 * 可跳转节点列表查询
	 * @param request
	 * @param procInsId
	 * @return
	 */
	@GetMapping("/listJumpNode/{procInsId}")
	public List<TaskNodeModel> listActivityNode(HttpServletRequest request, @PathVariable("procInsId") String procInsId) {
		return flowOpsService.listJumpNode(procInsId);
	}
	
	/**
	 * 跳转到指定节点
	 * @param request
	 * @param jumpNodeReq
	 * @return
	 */
	@RequestMapping(value="/jumpNode", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> jumpNode(HttpServletRequest request, @RequestBody JumpNodeReq jumpNodeReq) {
		String validateMsg = RspUtils.validate(jumpNodeReq, JumpNodeReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		return RspUtils.success(flowOpsService.jumpNode(jumpNodeReq));
	}
	
	/**
	 * 指定任务处理人
	 * @param request
	 * @param changeTaskAssigneeReq
	 * @return
	 */
	@RequestMapping(value="/changeTaskAssignee", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> changeTaskAssignee(HttpServletRequest request, @RequestBody ChangeTaskAssigneeReq changeTaskAssigneeReq) {
		String validateMsg = RspUtils.validate(changeTaskAssigneeReq, ChangeTaskAssigneeReq.class);
		if(StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}
		
		return RspUtils.success(flowOpsService.changeTaskAssignee(changeTaskAssigneeReq));
	}
}
