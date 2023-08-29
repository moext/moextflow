package com.moext.flowservice.flow.listener;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moext.flowservice.component.HistoryComponent;
import com.moext.flowservice.component.IdentityComponent;
import com.moext.flowservice.component.TaskComponent;
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.flow.constants.FlowVariableConstants;
import com.moext.flowservice.flow.model.FlowGroup;
import com.moext.flowservice.flow.model.FlowUser;
import com.moext.flowservice.service.TodoTaskService;
import com.moext.flowservice.util.SpringUtils;

/**
 * 任务分配时监听器
 * 
 * @author PengPeng
 */
public class TaskAssignedListener implements FlowableEventListener {

	private static Logger logger = LoggerFactory.getLogger(TaskAssignedListener.class);

	public RuntimeService getRuntimeService() {
		return SpringUtils.getBean(RuntimeService.class);
	}

	private TodoTaskService getTodoTaskService() {
		return SpringUtils.getBean(TodoTaskService.class);
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

	@Override
	public void onEvent(FlowableEvent event) {
		FlowableEntityEvent entityEvent = (FlowableEntityEvent) event;
		Object entity = entityEvent.getEntity();
		if (entity instanceof TaskEntity) {// 用户任务类型的实体
			TaskEntity taskEntity = (TaskEntity) entityEvent.getEntity();
			String procInsId = taskEntity.getProcessInstanceId();
			logger.info("TaskAssignedListener procId={} taskId={} onEvent...", procInsId, taskEntity.getId());

			// 刚启动第一个流程并完成第一个任务时，流程跟完成第一个任务在同一事务，此时查询结果为空
			ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(procInsId).singleResult();
			if (processInstance == null) {
				return;
			}

			Set<IdentityLink> candidates = taskEntity.getCandidates();
			if (StringUtils.isNotBlank(taskEntity.getAssignee())) {
				if (!taskEntity.getAssignee().startsWith("{")) {// 任务处理人为单人
					TodoTask todoTask = new TodoTask();
					todoTask.setProcessInstanceId(procInsId);
					todoTask.setTaskId(taskEntity.getId());
					todoTask.setProcDefKey(processInstance.getProcessDefinitionKey());
					todoTask.setReceiveUserCode(taskEntity.getAssignee());
					// 接收人姓名
					todoTask.setReceiveUserName(getIdentityComponent()
							.getUserDisplayNameByCode(taskEntity.getAssignee(), StringUtils.EMPTY));
					// 发起人用户名和姓名，从流程变量中获取
					String applyUserId = (String) taskEntity.getVariable(FlowVariableConstants.VARIABLE_APPLY_USERID);
					String applyUserName = (String) taskEntity
							.getVariable(FlowVariableConstants.VARIABLE_APPLY_USERNAME);
					// 流程标题，从流程变量中获取
					String processTitle = (String) taskEntity.getVariable(FlowVariableConstants.VARIABLE_TITLE);
					todoTask.setApplyUserCode(applyUserId);
					todoTask.setApplyUserName(applyUserName);
					todoTask.setTaskName(processTitle);
					todoTask.setStepNodeName(taskEntity.getName());
					todoTask.setTaskCreateTime(taskEntity.getCreateTime());
					// 业务标识
					todoTask.setFormInstanceId(processInstance.getBusinessKey());
					getTodoTaskService().insertTodoWhenTaskAssigneed(todoTask);
				}
			} else if (CollectionUtils.size(candidates) == 1) {// 如果只有一个分组条件
				IdentityLink identityLink = candidates.iterator().next();
				if (StringUtils.isNotBlank(identityLink.getGroupId())) {
					FlowGroup group = getIdentityComponent().getGroupById(identityLink.getGroupId());
					if (group != null) {
						List<FlowUser> memberList = getIdentityComponent().listUserByGroupId(group.getId());
						if (CollectionUtils.size(memberList) == 1) {// 判断该组是否只有一个人，如是，则此人将该组任务自动签收
							getTaskComponent().setAssignee(taskEntity.getId(), memberList.get(0).getUserCode());
						} else {
							// 本流程同节点历史处理人
							String historyAssignee = getHistoryComponent().getHistoryAssignee(
									taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey());
							if (historyAssignee != null) {
								getTaskComponent().setAssignee(taskEntity.getId(), historyAssignee);
							} else if (CollectionUtils.size(memberList) > 1) {
								logger.warn("角色{}存在{}个用户，取用户{}作为处理人", identityLink.getGroupId(),
										CollectionUtils.size(memberList), memberList.get(0).getUserCode());
								getTaskComponent().setAssignee(taskEntity.getId(), memberList.get(0).getUserCode());
							}
						}
					}
				}
			} else {
				logger.warn("no assignee record found taskId:{}", taskEntity.getId());
			}
		}
	}

	@Override
	public boolean isFailOnException() {
		return true;
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
