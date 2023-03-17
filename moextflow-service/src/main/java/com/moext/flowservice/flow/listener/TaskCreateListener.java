package com.moext.flowservice.flow.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.moext.flowservice.component.AssigneeComponent;
import com.moext.flowservice.component.HistoryComponent;
import com.moext.flowservice.component.IdentityComponent;
import com.moext.flowservice.component.TaskComponent;
import com.moext.flowservice.flow.constants.FlowVariableConstants;
import com.moext.flowservice.flow.constants.TaskCommentConstants;
import com.moext.flowservice.flow.model.DynamicAssignee;
import com.moext.flowservice.flow.model.FlowGroup;
import com.moext.flowservice.flow.model.FlowUser;
import com.moext.flowservice.util.SpringUtils;

/**
 * 任务创建时监听器
 * @author PengPeng
 */
public class TaskCreateListener implements FlowableEventListener {

	private static Logger logger = LoggerFactory.getLogger(TaskCreateListener.class);
	
	public RuntimeService getRuntimeService() {
		return SpringUtils.getBean(RuntimeService.class);
	}
	
	public TaskComponent getTaskComponent() {
		return SpringUtils.getBean(TaskComponent.class);
	}
	
	public IdentityComponent getIdentityComponent() {
		return SpringUtils.getBean(IdentityComponent.class);
	}
	
	public HistoryComponent getHistoryComponent() {
		return SpringUtils.getBean(HistoryComponent.class);
	}
	
	public AssigneeComponent getAssigneeComponent() {
		return SpringUtils.getBean(AssigneeComponent.class);
	}

	@Override
	public void onEvent(FlowableEvent event) {
		FlowableEntityEvent entityEvent = (FlowableEntityEvent)event;
		Object entity = entityEvent.getEntity();
		if(entity instanceof TaskEntity) {//用户任务类型的实体
			TaskEntity taskEntity = (TaskEntity)entityEvent.getEntity();
			String procInsId = taskEntity.getProcessInstanceId();
			logger.info("TaskCreateListener procId={} taskId={} onEvent...", procInsId, taskEntity.getId());
		    
			String orgAssignee = taskEntity.getAssignee();
			Set<IdentityLink> candidates = taskEntity.getCandidates();
			String historyTaskAssignee = getHistoryComponent().getHistoryAssignee(procInsId, taskEntity.getTaskDefinitionKey());
			if(StringUtils.isNotBlank(historyTaskAssignee)) {//该节点历史处理过，让原处理人继续处理
				getTaskComponent().setAssignee(taskEntity.getId(), historyTaskAssignee);
			}else if(StringUtils.isNotBlank(orgAssignee)) {
				if(StringUtils.startsWith(orgAssignee, "{")) {//大括号开始的，扩展assignee取法
					DynamicAssignee dynamicAssignee = JSONObject.parseObject(orgAssignee, DynamicAssignee.class);
					//核心参数
					String applyUserId = (String)taskEntity.getVariable(FlowVariableConstants.VARIABLE_APPLY_USERID);
					String company = (String)taskEntity.getVariable(FlowVariableConstants.VARIABLE_COMPANYKEY);
					String department = (String)taskEntity.getVariable(FlowVariableConstants.VARIABLE_DEPARTMENTKEY);
					dynamicAssignee.setApplyUserId(applyUserId);
					dynamicAssignee.setCompany(company);
					dynamicAssignee.setDepartment(department);
					//扩展参数
					dynamicAssignee.setVariables(taskEntity.getVariables());
					
					//修改任务处理人为最终动态处理人
					String finalAssignee = getAssigneeComponent().getFinalAssignee(dynamicAssignee);
					getTaskComponent().setAssignee(taskEntity.getId(), finalAssignee);
					
					//该节点配置为可以跳过且最终动态处理人为空
					if(dynamicAssignee.isCanSkip()==true && StringUtils.isBlank(finalAssignee)) {
						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_PASS);
						getTaskComponent().complete(taskEntity.getId(), taskEntity.getProcessInstanceId(), TaskCommentConstants.SYSTEM_AUTO_PASS, vars);
					}
				}
			}else if(CollectionUtils.size(candidates) == 1) {//如果只有一个分组条件
				IdentityLink identityLink = candidates.iterator().next();
				if(StringUtils.isNotBlank(identityLink.getGroupId())) {
					FlowGroup group = getIdentityComponent().getGroupById(identityLink.getGroupId());
					if(group != null) {
						List<FlowUser> userList = getIdentityComponent().listUserByGroupId(group.getId());
						if(CollectionUtils.size(userList) == 1) {//判断该组是否只有一个人，如是，则此人将该组任务自动签收
							getTaskComponent().setAssignee(taskEntity.getId(), userList.get(0).getUserCode());
						}else {
							//本流程同节点历史处理人
							String historyAssignee = getHistoryComponent().getHistoryAssignee(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey());
							if(historyAssignee != null) {
								getTaskComponent().setAssignee(taskEntity.getId(), historyAssignee);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public boolean isFireOnTransactionLifecycleEvent() {
		return false;
	}

	@Override
	public String getOnTransaction() {
		return null;
	}
}
