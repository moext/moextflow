package com.moext.flowservice.service.impl;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.component.ProcessComponent;
import com.moext.flowservice.component.impl.FlowableTaskComponent;
import com.moext.flowservice.dto.req.ActProcessPageReq;
import com.moext.flowservice.dto.req.ChangeTaskAssigneeReq;
import com.moext.flowservice.dto.req.JumpNodeReq;
import com.moext.flowservice.flow.model.TaskManageModel;
import com.moext.flowservice.flow.model.TaskNodeModel;
import com.moext.flowservice.service.FlowOpsService;

@Service
public class FlowOpsServiceImpl implements FlowOpsService{

	@Autowired
	private ProcessComponent processComponent;
	
	@Autowired
	private FlowableTaskComponent taskComponent;
	
	@Override
	public PageResponse<TaskManageModel> listOpsFlowPage(ActProcessPageReq actProcessPageReq) {
		List<TaskManageModel> data = processComponent.listHistoryProcess(actProcessPageReq);
		long total = processComponent.countHistoryProcess(actProcessPageReq);
		return new PageResponse.Builder<TaskManageModel>().setPageRequest(actProcessPageReq).setContent(data).setTotalElements(total).build();
	}

	@Override
	public List<TaskNodeModel> listJumpNode(String procInsId) {
		return processComponent.listAllNode(procInsId);
	}

	@Override
	@Transactional
	public Boolean jumpNode(JumpNodeReq jumpNodeReq) {
		Task task = taskComponent.getCurrentTask(jumpNodeReq.getProcInsId());
		if(task != null) {
			if(!StringUtils.equals(task.getTaskDefinitionKey(), jumpNodeReq.getToTaskDefKey())) {//当前任务和待跳转任务不是同一个才执行跳转
				processComponent.jumpToNode(jumpNodeReq.getProcInsId(), task.getTaskDefinitionKey(), jumpNodeReq.getToTaskDefKey());
			}
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Boolean changeTaskAssignee(ChangeTaskAssigneeReq changeTaskAssigneeReq) {
		taskComponent.setAssignee(changeTaskAssigneeReq.getTaskId(), changeTaskAssigneeReq.getAssignee());
		return true;
	}

	@Override
	public void toModel(String procDefId) throws Exception {
		processComponent.convertToModel(procDefId);
	}
}
