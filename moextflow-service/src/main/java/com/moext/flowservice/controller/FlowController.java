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
import com.moext.flowservice.common.RspUtils;
import com.moext.flowservice.data.gen.model.TaskComment;
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.dto.req.ApproveProcessReq;
import com.moext.flowservice.dto.req.StartProcessReq;
import com.moext.flowservice.flow.model.TaskNodeModel;
import com.moext.flowservice.service.TaskCommentService;
import com.moext.flowservice.service.TodoTaskService;

/**
 * 流程接口
 * 
 * @author PengPeng
 */
@RequestMapping("/flow")
@RestController
public class FlowController {

	@Autowired
	private TodoTaskService todoTaskService;

	@Autowired
	private TaskCommentService taskCommentService;

	/**
	 * 发起流程
	 * 
	 * @param request
	 * @param startProcessReq
	 * @return
	 */
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<String> startProcess(HttpServletRequest request, @RequestBody StartProcessReq startProcessReq) {
		String validateMsg = RspUtils.validate(startProcessReq, StartProcessReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(todoTaskService.startProcess(startProcessReq));
	}

	/**
	 * 审批流程
	 */
	@RequestMapping(value = "/approveProcess", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Boolean> approveProcess(HttpServletRequest request,
			@RequestBody ApproveProcessReq approveProcessReq) {
		String validateMsg = RspUtils.validate(approveProcessReq, ApproveProcessReq.class);
		if (StringUtils.isNotBlank(validateMsg)) {
			return RspUtils.error(validateMsg);
		}

		return RspUtils.success(todoTaskService.approveProcess(approveProcessReq));
	}

	/**
	 * 可退节点列表
	 * 
	 * @param todoTask
	 * @return
	 */
	@GetMapping("/listBackNode/{todoId}")
	public List<TaskNodeModel> listBackNode(HttpServletRequest request, @PathVariable("todoId") String todoId) {
		return todoTaskService.listBackNode(todoId);
	}

	/**
	 * 列出审批意见列表
	 * 
	 * @param todoTask
	 * @return
	 */
	@GetMapping("/listComments/{todoId}")
	public List<TaskComment> listComments(HttpServletRequest request, @PathVariable("todoId") String todoId) {
		TodoTask todoTask = todoTaskService.getByTodoId(todoId);
		return taskCommentService.listComments(todoTask.getProcessInstanceId());
	}

	/**
	 * 列出所有审批节点
	 * 
	 * @param todoTask
	 * @return
	 */
	@GetMapping("/listAllNode/{todoId}")
	public List<TaskNodeModel> listAllNode(HttpServletRequest request, @PathVariable("todoId") String todoId) {
		return todoTaskService.listAllNode(todoId);
	}

	/**
	 * 获取当前流程审批任务
	 * 
	 * @param todoTask
	 * @return
	 */
	@GetMapping("/getCurrentTask/{todoId}")
	public TodoTask getCurrentTask(HttpServletRequest request, @PathVariable("todoId") String todoId) {
		return todoTaskService.getCurrentTask(todoId);
	}
}
