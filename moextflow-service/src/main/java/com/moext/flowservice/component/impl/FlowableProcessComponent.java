package com.moext.flowservice.component.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.moext.flowservice.component.ProcessComponent;
import com.moext.flowservice.dto.req.ActProcessPageReq;
import com.moext.flowservice.flow.constants.FlowVariableConstants;
import com.moext.flowservice.flow.constants.ProcessDefConstants;
import com.moext.flowservice.flow.model.StartProcessModel;
import com.moext.flowservice.flow.model.TaskManageModel;
import com.moext.flowservice.flow.model.TaskNodeModel;

/**
 * 流程组件的Flowable实现
 * @author PengPeng
 */
@Component
public class FlowableProcessComponent implements ProcessComponent{

	@Autowired
	private RuntimeService runtimeService;
		
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	@Transactional
	public String startProcess(StartProcessModel startProcessModel) {
		//发起人
		User startUser = identityService.createUserQuery().userId(startProcessModel.getApplyUserId()).singleResult();
		String startUserName = StringUtils.EMPTY;
		if(startUser != null) {
			startUserName = startUser.getDisplayName();
		}
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(startProcessModel.getApplyUserId());
		
		Map<String, Object> vars = startProcessModel.getVariables();
		// 设置流程变量
		if (vars == null){
			vars = new HashMap<String, Object>();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(startProcessModel.getTitle())){
			vars.put(FlowVariableConstants.VARIABLE_TITLE, startProcessModel.getTitle());
		}
		
		//部门及公司标识
		vars.put(FlowVariableConstants.VARIABLE_DEPARTMENTKEY, startProcessModel.getDepartmentKey());
		vars.put(FlowVariableConstants.VARIABLE_COMPANYKEY, startProcessModel.getCompanyKey());
		
		//发起人及发起时间
		vars.put(FlowVariableConstants.VARIABLE_APPLY_USERID, startProcessModel.getApplyUserId());
		vars.put(FlowVariableConstants.VARIABLE_APPLY_USERNAME, startUserName);
		
		Date now = new Date();
		vars.put(FlowVariableConstants.VARIABLE_APPLY_DATE, now);
		//审批结果
		vars.put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_PASS);
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(startProcessModel.getProcDefKey(), startProcessModel.getFormInstanceId(), vars);
		return procIns.getProcessInstanceId();
	}
	
	private TaskNodeModel toTaskNodeModel(HistoricActivityInstance instance) {
		TaskNodeModel taskNodeModel = new TaskNodeModel();
		taskNodeModel.setAsignee(instance.getAssignee());
		taskNodeModel.setTaskDefName(instance.getActivityName());
		taskNodeModel.setTaskDefKey(instance.getActivityId());
		taskNodeModel.setStartTime(instance.getStartTime());
		taskNodeModel.setEndTime(instance.getEndTime());
		taskNodeModel.setDurationInMillis(instance.getDurationInMillis());
		return taskNodeModel;
	}
	
	@Override
	public List<TaskNodeModel> listBackNode(String taskId){
		List<TaskNodeModel> nodeList = new ArrayList<TaskNodeModel>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task != null) {
			//查询已完成的用户任务
			List<HistoricActivityInstance> historicActivityInstancesList = historyService.createHistoricActivityInstanceQuery().activityType(ProcessDefConstants.USER_TASK)
	                .processInstanceId(task.getProcessInstanceId()).finished()
	                .orderByHistoricActivityInstanceEndTime().asc().list();
			Map<String, Boolean> checkMap = new HashMap<String, Boolean>();
			for(HistoricActivityInstance instance : historicActivityInstancesList) {
				if(instance.getActivityId()!=null && StringUtils.equalsIgnoreCase(instance.getActivityId(), task.getTaskDefinitionKey())){//防止循环查询
					break;
				}
				if(!checkMap.containsKey(instance.getActivityId())) {//过滤重复节点
					if(instance.getAssignee()!=null) {//处理人为自身的任务可选择
						String activityId = instance.getActivityId();
						//同时过滤 多人会签任务
						if(!StringUtils.startsWithIgnoreCase(activityId, ProcessDefConstants.TASK_DEF_KEY_PREFIX_MULTI)) {//会签发起人任务不可选择
							checkMap.put(instance.getActivityId(), true);
							nodeList.add(toTaskNodeModel(instance));
						}
					}
				}
			}
		}
		return nodeList;
	}

	@Override
	public List<TaskNodeModel> listAllNode(String procInsId) {
		List<TaskNodeModel> nodeList = new ArrayList<TaskNodeModel>();
		//查询已完成的用户任务
		List<HistoricActivityInstance> historicActivityInstancesList = historyService.createHistoricActivityInstanceQuery().activityType(ProcessDefConstants.USER_TASK)
            .processInstanceId(procInsId).orderByHistoricActivityInstanceStartTime().asc().list();
		Map<String, Boolean> checkMap = new HashMap<String, Boolean>();
		for(HistoricActivityInstance instance : historicActivityInstancesList) {
			if(!checkMap.containsKey(instance.getActivityId())) {//过滤重复节点
				if(instance.getAssignee()!=null) {//处理人为自身的任务可选择
					checkMap.put(instance.getActivityId(), true);
					nodeList.add(toTaskNodeModel(instance));
				}
			}
		}
		return nodeList;
	}
	
	@Override
	public List<TaskManageModel> listHistoryProcess(ActProcessPageReq actProcessPageReq){
		TaskQuery taskQuery = genTaskQuery(actProcessPageReq);
		List<Task> taskList = taskQuery.includeProcessVariables().orderByTaskCreateTime().desc().listPage(actProcessPageReq.getFirstIndex(), actProcessPageReq.getPageSize());	
		List<TaskManageModel> taskManageModels = new ArrayList<TaskManageModel>();
		if(!CollectionUtils.isEmpty(taskList)) {
			for(Task task : taskList) {
				taskManageModels.add(toTaskManageModel(task));
			}
		}
		return taskManageModels;
	}	
	
	@Override
	public long countHistoryProcess(ActProcessPageReq actProcessPageReq){
		return genTaskQuery(actProcessPageReq).count();
	}	
	
	private TaskQuery genTaskQuery(ActProcessPageReq actProcessPageReq) {
		TaskQuery taskQuery = taskService.createTaskQuery();				
		//任务id
		if(StringUtils.isNotBlank(actProcessPageReq.getTaskId())){
			taskQuery.taskId(actProcessPageReq.getTaskId());
		}
		
		if(StringUtils.isNotBlank(actProcessPageReq.getProcInsId())){
		    taskQuery.processInstanceId(actProcessPageReq.getProcInsId());
		}
		if(StringUtils.isNotBlank(actProcessPageReq.getCurrentTaskUser())){
			taskQuery.taskAssignee(actProcessPageReq.getCurrentTaskUser());
		}
		if(StringUtils.isNotBlank(actProcessPageReq.getApplyUserId())){
			//发起人
			taskQuery.processVariableValueEquals(FlowVariableConstants.VARIABLE_APPLY_USERID, actProcessPageReq.getApplyUserId());
		}
		if(StringUtils.isNotBlank(actProcessPageReq.getProcessTitle())){
			//流程标题
			taskQuery.processVariableValueLike(FlowVariableConstants.VARIABLE_TITLE, "%"+actProcessPageReq.getProcessTitle()+"%");
		}
		//流程类型
		if (StringUtils.isNotBlank(actProcessPageReq.getProcDefKey())) {
			taskQuery.processDefinitionKeyLike("%"+actProcessPageReq.getProcDefKey()+"%");
		}
		//任务创建时间
		if(actProcessPageReq.getBeginDate()!=null){
			taskQuery.taskCreatedAfter(actProcessPageReq.getBeginDate());
		}
		if(actProcessPageReq.getEndDate()!=null){
			taskQuery.taskCreatedBefore(actProcessPageReq.getEndDate());
		}
		return taskQuery;
	}
	
	private TaskManageModel toTaskManageModel(Task task) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		TaskManageModel model = new TaskManageModel();
		Map<String, Object> processVariables = task.getProcessVariables();
		if(processVariables!=null && processVariables.get(FlowVariableConstants.VARIABLE_TITLE)!=null){
			model.setProcessDefinitionName((String)processVariables.get(FlowVariableConstants.VARIABLE_TITLE));//流程标题
		}else{
			model.setProcessDefinitionName("");
		}
		//设置发起人
		if (processVariables != null) {
			model.setInitiator((String) processVariables.get(FlowVariableConstants.VARIABLE_APPLY_USERID));//发起人
		}
		//设置流程类型
		model.setProcDefKey(processDefinition.getKey());
		model.setProcessDefinitionId(processDefinition.getId());
		model.setTaskCreateTime(task.getCreateTime());//任务创建时间
		model.setTaskId(task.getId());//任务ID
		model.setOwner(task.getOwner());//任务owner
		model.setTaskName(task.getName());//任务名
		model.setProcessInstanceId(task.getProcessInstanceId());//流程实例ID
		model.setPriority(task.getPriority());
		model.setDueDate(task.getDueDate());//任务结束 时间
		model.setCurrentOperator(task.getAssignee());
		model.setTaskDefKey(task.getTaskDefinitionKey());
		model.setTaskDefName(task.getName());
		return model;
	}
	
	@Override
	public void jumpToNode(String procInsId, String nodeId, String toNodeId) {
		 runtimeService.createChangeActivityStateBuilder()
         .processInstanceId(procInsId)
         .moveActivityIdTo(nodeId, toNodeId)
         .changeState();
	}
}
