package com.moext.flowservice.service;

import java.util.List;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.dto.req.ActProcessPageReq;
import com.moext.flowservice.dto.req.ChangeTaskAssigneeReq;
import com.moext.flowservice.dto.req.JumpNodeReq;
import com.moext.flowservice.flow.model.TaskManageModel;
import com.moext.flowservice.flow.model.TaskNodeModel;

/**
 * 流程操作接口
 * 
 * @author PengPeng
 */
public interface FlowOpsService {

	/**
	 * 干预流程列表
	 * 
	 * @param actProcessPageReq
	 * @return
	 */
	public PageResponse<TaskManageModel> listOpsFlowPage(ActProcessPageReq actProcessPageReq);

	/**
	 * 列出所有可跳转节点
	 * 
	 * @param procInsId
	 * @return
	 */
	public List<TaskNodeModel> listJumpNode(String procInsId);

	/**
	 * 节点跳转
	 * 
	 * @param jumpNodeReq
	 * @return
	 */
	public Boolean jumpNode(JumpNodeReq jumpNodeReq);

	/**
	 * 指定任务处理人
	 * 
	 * @param changeTaskAssigneeReq
	 * @return
	 */
	public Boolean changeTaskAssignee(ChangeTaskAssigneeReq changeTaskAssigneeReq);

	/**
	 * 运行中的流程定义转模型
	 * 
	 * @param procDefId
	 * @throws Exception
	 */
	public void toModel(String procDefId) throws Exception;

}
