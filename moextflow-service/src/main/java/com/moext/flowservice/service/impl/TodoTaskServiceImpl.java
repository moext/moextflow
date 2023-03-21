package com.moext.flowservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.component.IdentityComponent;
import com.moext.flowservice.component.ProcessComponent;
import com.moext.flowservice.component.TaskComponent;
import com.moext.flowservice.data.gen.dao.TaskCommentMapper;
import com.moext.flowservice.data.gen.dao.TodoTaskMapper;
import com.moext.flowservice.data.gen.model.TaskComment;
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.data.gen.model.TodoTaskExample;
import com.moext.flowservice.dto.req.ApproveProcessReq;
import com.moext.flowservice.dto.req.StartProcessReq;
import com.moext.flowservice.dto.req.TodoListCountReq;
import com.moext.flowservice.dto.req.TodoListPageReq;
import com.moext.flowservice.flow.constants.ApproveOperTypeConstants;
import com.moext.flowservice.flow.constants.FlowVariableConstants;
import com.moext.flowservice.flow.constants.TodoStatusConstants;
import com.moext.flowservice.flow.model.StartProcessModel;
import com.moext.flowservice.flow.model.TaskNodeModel;
import com.moext.flowservice.service.TodoTaskService;
import com.moext.flowservice.util.SnowFlakeUtils;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {

	private static Logger logger = LoggerFactory.getLogger(TodoTaskServiceImpl.class);
	
	@Autowired
	private TodoTaskMapper todoTaskMapper;
	
	@Autowired
	private TaskCommentMapper taskCommentMapper;
	
	@Autowired
	private ProcessComponent processComponent;
	
	@Autowired
	private TaskComponent taskComponent;
	
	@Autowired
	private IdentityComponent identityComponent;
	
	@Override
	public PageResponse<TodoTask> listPage(TodoListPageReq todoListPageReq){
		TodoTaskExample example = todoListPageReq.genExample();
		long total = todoTaskMapper.countByExample(example);
		List<TodoTask> list = todoTaskMapper.selectByExampleWithRowbounds(example, todoListPageReq.getRowBounds());
		return new PageResponse.Builder<TodoTask>().setPageRequest(todoListPageReq).setContent(list).setTotalElements(total).build();
	}
	
	@Override
	public Long count(TodoListCountReq todoListCountReq) {
		TodoTaskExample example = todoListCountReq.genExample();
		return todoTaskMapper.countByExample(example);
	}
	
	@Override
	public TodoTaskExample toExampleByTodoId(String todoId) {
		TodoTaskExample example = new TodoTaskExample();
		example.createCriteria().andTodoIdEqualTo(todoId).andIsDelEqualTo(false);
		return example;
	}
	
	@Override
	public TodoTaskExample toExampleByByProcIdAndTaskId(String procId, String taskId) {
		TodoTaskExample example = new TodoTaskExample();
		example.createCriteria().andProcessInstanceIdEqualTo(procId).andTaskIdEqualTo(taskId).andIsDelEqualTo(false);
		return example;
	} 
	
	@Override
	public TodoTask getOne(List<TodoTask> list) {
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}else {
			throw new IllegalArgumentException("待办任务不存在");
		}
	}
	
	@Override
	public TodoTask getByTodoId(String todoId) {
		List<TodoTask> list = todoTaskMapper.selectByExample(toExampleByTodoId(todoId));
		return getOne(list);
	}
	
	@Override
	public boolean deleteByTodoId(String todoId) {
		TodoTask todoTask = getByTodoId(todoId);
		todoTask.setIsDel(true);
		return todoTaskMapper.updateByPrimaryKey(todoTask) > 0;
	}
	
	@Override
	public TodoTask getByProcIdAndTaskId(String procId, String taskId) {
		List<TodoTask> list = todoTaskMapper.selectByExample(toExampleByByProcIdAndTaskId(procId, taskId));
		return getOne(list);
	}
	
	@Override
	@Transactional
	public String startProcess(StartProcessReq startProcessReq) {
		//启动流程任务
		StartProcessModel startProcessModel = new StartProcessModel.Builder().setApplyUserId(startProcessReq.getApplyUserId()).setDepartmentKey(startProcessReq.getDepartmentKey()).setCompanyKey(startProcessReq.getCompanyKey())
				.setProcDefKey(startProcessReq.getProcDefKey()).setFormInstanceId(startProcessReq.getFormInstanceId()).setTitle(startProcessReq.getTitle()).setVariables(startProcessReq.getVariables()).build();
		String processInstanceId = processComponent.startProcess(startProcessModel);

		//完成第一个任务，此处约定第一个任务为发起人申请任务
		String taskId = taskComponent.completeFirstTask(startProcessReq.getApplyUserId(), processInstanceId, startProcessReq.getComment());

		//写自定义意见表
		TodoTask todoTask = getByProcIdAndTaskId(processInstanceId, taskId);
		if(todoTask == null) {
			throw new IllegalArgumentException("未找到待办任务");
		}
		
		Date now = new Date();
		TaskComment taskComment = new TaskComment();
		taskComment.setId(SnowFlakeUtils.nextId());
		taskComment.setComment(startProcessReq.getComment()==null ? StringUtils.EMPTY:startProcessReq.getComment());
		taskComment.setCreateTime(now);
		taskComment.setFormInstanceId(startProcessReq.getFormInstanceId());
		taskComment.setIsDel(false);
		taskComment.setProcessInstanceId(processInstanceId);
		taskComment.setStepNodeName(todoTask.getStepNodeName());
		taskComment.setTaskCompleteTime(todoTask.getTaskCompleteTime());
		taskComment.setTaskCreateTime(todoTask.getTaskCreateTime());
		taskComment.setTaskId(todoTask.getTaskId());
		taskComment.setTaskName(todoTask.getTaskName());
		taskComment.setTaskReceiveTime(todoTask.getTaskReceiveTime());
		taskComment.setTodoId(todoTask.getTodoId());
		taskComment.setUpdateTime(now);
		taskComment.setUserCode(startProcessReq.getApplyUserId());
		taskComment.setUserName(identityComponent.getUserDisplayNameByCode(startProcessReq.getApplyUserId(), StringUtils.EMPTY));
		taskComment.setOperType(ApproveOperTypeConstants.OPER_TYPE_APPLY);
		taskCommentMapper.insert(taskComment);
		
		return processInstanceId;
	}

	@Override
	@Transactional
	public void insertTodoWhenTaskAssigneed(TodoTask todoTask) {
		logger.info("insertTodoWhenTaskAssigneed procId={}, taskId={}", todoTask.getProcessInstanceId(), todoTask.getTaskId());
		if(todoTask.getProcessInstanceId() == null) {
			return;
		}
		if(todoTask.getTaskId() == null) {
			return;
		}
		if(todoTask.getProcDefKey() == null) {
			return;
		}
		if(todoTask.getReceiveUserCode() == null) {
			return;
		}
		
		Date now = new Date();
		//如果同一任务更换处理人（流程干预）时，将原处理人有效待办删除
		TodoTaskExample exampleTaskOld = new TodoTaskExample();
		exampleTaskOld.createCriteria().andProcessInstanceIdEqualTo(todoTask.getProcessInstanceId())
		.andTaskIdEqualTo(todoTask.getTaskId())
		.andStatusEqualTo(TodoStatusConstants.TODO_STATUS_TODO)
		.andProcDefKeyEqualTo(todoTask.getProcDefKey());
		List<TodoTask> oldTodoTaskList = todoTaskMapper.selectByExample(exampleTaskOld);
		if(!CollectionUtils.isEmpty(oldTodoTaskList)) {//任务有原处理人
			logger.info("oldTodoTaskList size = {}", oldTodoTaskList.size());
			for(TodoTask oldTodoTask : oldTodoTaskList) {
				oldTodoTask.setIsDel(true);
				oldTodoTask.setUpdateTime(now);
				todoTaskMapper.updateByPrimaryKey(oldTodoTask);
			}
		}
		
		//插入待办记录
		todoTask.setStatus(TodoStatusConstants.TODO_STATUS_TODO);//待办状态：待办
		todoTask.setTaskCompleteTime(null);
		todoTask.setTaskReceiveTime(now);
		todoTask.setTodoId(UUID.randomUUID().toString().toLowerCase());
		todoTask.setIsDel(false);
		todoTask.setCreateTime(now);
		todoTask.setUpdateTime(now);
		todoTask.setId(SnowFlakeUtils.nextId());
		this.todoTaskMapper.insert(todoTask);
		
		//删除当前流程下用户的已办任务(面向流程而非面向任务)
		TodoTaskExample exampleTaskDoing = new TodoTaskExample();
		exampleTaskDoing.createCriteria().andProcessInstanceIdEqualTo(todoTask.getProcessInstanceId())
		.andStatusEqualTo(TodoStatusConstants.TODO_STATUS_DOING)
		.andProcDefKeyEqualTo(todoTask.getProcDefKey())
		.andReceiveUserCodeEqualTo(todoTask.getReceiveUserCode()).andIsDelEqualTo(false);
		List<TodoTask> doingTaskList = todoTaskMapper.selectByExample(exampleTaskDoing);
		if(!CollectionUtils.isEmpty(doingTaskList)) {
			logger.info("doingTaskList size = {}", doingTaskList.size());
			for(TodoTask item : doingTaskList) {
				item.setIsDel(true);
				item.setUpdateTime(now);
				todoTaskMapper.updateByPrimaryKey(item);
			}
		}else {
			logger.info("doingTaskList is empty");
		}
		
	}

	@Override
	@Transactional
	public void updateWhenTaskComplete(TodoTask todoTask) {
		logger.info("updateWhenTaskComplete procId={}, taskId={}", todoTask.getProcessInstanceId(), todoTask.getTaskId());
		if(todoTask.getProcessInstanceId() == null) {
			return;
		}
		if(todoTask.getTaskId() == null) {
			return;
		}
		if(todoTask.getProcDefKey() == null) {
			return;
		}
		if(todoTask.getReceiveUserCode() == null) {
			return;
		}
		
		Date now = new Date();
		TodoTaskExample example = new TodoTaskExample();
		example.createCriteria().andProcessInstanceIdEqualTo(todoTask.getProcessInstanceId())
		.andTaskIdEqualTo(todoTask.getTaskId())
		.andStatusEqualTo(TodoStatusConstants.TODO_STATUS_TODO)
		.andReceiveUserCodeEqualTo(todoTask.getReceiveUserCode())
		.andProcDefKeyEqualTo(todoTask.getProcDefKey());
		List<TodoTask> oldTodoTaskList = todoTaskMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(oldTodoTaskList)) {
			for(TodoTask oldTodoTask : oldTodoTaskList) {
				oldTodoTask.setStatus(TodoStatusConstants.TODO_STATUS_DOING);//待办改已办
				oldTodoTask.setTaskCompleteTime(now);
				oldTodoTask.setUpdateTime(now);
				todoTaskMapper.updateByPrimaryKey(oldTodoTask);
			}
		}
		
	}

	@Override
	@Transactional
	public void updateWhenProcessComplete(String processInstanceId) {
		if(processInstanceId == null) {
			return;
		}
		
		Date now = new Date();
		//所有当前流程记录列表（包括删除的）
		TodoTaskExample exampleProcessAll = new TodoTaskExample();
		exampleProcessAll.createCriteria().andProcessInstanceIdEqualTo(processInstanceId);
		List<TodoTask> oldTodoTaskList = todoTaskMapper.selectByExample(exampleProcessAll);
		
		//全部状态改为办结
		if(!CollectionUtils.isEmpty(oldTodoTaskList)) {
			for(TodoTask oldTodoTask : oldTodoTaskList) {
				oldTodoTask.setStatus(TodoStatusConstants.TODO_STATUS_DONE);//改办结
				oldTodoTask.setTaskCompleteTime(now);
				oldTodoTask.setUpdateTime(now);
				todoTaskMapper.updateByPrimaryKey(oldTodoTask);
			}
		}
		
		//每人仅限生成1条，多余记录删除
		TodoTaskExample exampleProcessDone = new TodoTaskExample();
		exampleProcessDone.createCriteria().andProcessInstanceIdEqualTo(processInstanceId)
		.andIsDelEqualTo(false)
		.andStatusEqualTo(TodoStatusConstants.TODO_STATUS_DONE);
		Map<String, TodoTask> receiveUserCode2Task = new HashMap<String, TodoTask>();
		List<TodoTask> doneTaskList = todoTaskMapper.selectByExample(exampleProcessDone);
		if(!CollectionUtils.isEmpty(doneTaskList)) {
			for(TodoTask doneTask : doneTaskList) {
				if(!receiveUserCode2Task.containsKey(doneTask.getReceiveUserCode())) {
					receiveUserCode2Task.put(doneTask.getReceiveUserCode(), doneTask);
				}else {//将同一个流程相同接收人的记录删除
					receiveUserCode2Task.put(doneTask.getReceiveUserCode(), doneTask);
					doneTask.setIsDel(true);
					doneTask.setUpdateTime(now);
					todoTaskMapper.updateByPrimaryKey(doneTask);
				}
			}
		}
	}

	@Override
	@Transactional
	public boolean approveProcess(ApproveProcessReq approveProcessReq) {
		TodoTask todoTask = getByTodoId(approveProcessReq.getTodoId());
		
		//审批通过或不通过变量处理，重新申请或销毁申请变量处理
		if(ApproveOperTypeConstants.OPER_TYPE_OK.equals(approveProcessReq.getOperType())) {
			approveProcessReq.getVariables().put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_PASS);
		}else if(ApproveOperTypeConstants.OPER_TYPE_REJECT.equals(approveProcessReq.getOperType())) {
			approveProcessReq.getVariables().put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_REJECT);
		}else if(ApproveOperTypeConstants.OPER_TYPE_REAPPLY.equals(approveProcessReq.getOperType())) {
			approveProcessReq.getVariables().put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_PASS);
		}else if(ApproveOperTypeConstants.OPER_TYPE_CANCEL.equals(approveProcessReq.getOperType())) {
			approveProcessReq.getVariables().put(FlowVariableConstants.VARIABLE_RESULT, FlowVariableConstants.VARIABLE_RESULT_VALUE_REJECT);
		}
		
		
		if(todoTask != null) {
			taskComponent.complete(todoTask.getTaskId(), todoTask.getProcessInstanceId(), approveProcessReq.getComment(), approveProcessReq.getVariables());
			
			//写自定义意见表
			Date now = new Date();
			TaskComment taskComment = new TaskComment();
			taskComment.setId(SnowFlakeUtils.nextId());
			taskComment.setComment(approveProcessReq.getComment());
			taskComment.setCreateTime(now);
			taskComment.setFormInstanceId(todoTask.getFormInstanceId());
			taskComment.setIsDel(false);
			taskComment.setProcessInstanceId(todoTask.getProcessInstanceId());
			taskComment.setStepNodeName(todoTask.getStepNodeName());
			taskComment.setTaskCompleteTime(now);
			taskComment.setTaskCreateTime(todoTask.getTaskCreateTime());
			taskComment.setTaskId(todoTask.getTaskId());
			taskComment.setTaskName(todoTask.getTaskName());
			taskComment.setTaskReceiveTime(todoTask.getTaskReceiveTime());
			taskComment.setTodoId(todoTask.getTodoId());
			taskComment.setUpdateTime(now);
			taskComment.setUserCode(todoTask.getReceiveUserCode());
			taskComment.setUserName(todoTask.getReceiveUserName());
			taskComment.setOperType(approveProcessReq.getOperType());
			taskCommentMapper.insert(taskComment);
			return true;
		}else {
			throw new IllegalArgumentException("找不到todoId=" + approveProcessReq.getTodoId() + "的待办记录");
		}
	}

	@Override
	public List<TaskNodeModel> listBackNode(String todoId) {
		TodoTask todoTask = this.getByTodoId(todoId);
		return processComponent.listBackNode(todoTask.getTaskId());
	}

	@Override
	public List<TaskNodeModel> listAllNode(String todoId) {
		TodoTask todoTask = this.getByTodoId(todoId);
		return processComponent.listAllNode(todoTask.getProcessInstanceId());
	}

	@Override
	public TodoTask getCurrentTask(String todoId) {
		TodoTask todoTask = this.getByTodoId(todoId);
		Task task = taskComponent.getCurrentTask(todoTask.getProcessInstanceId());
		return this.getByProcIdAndTaskId(task.getProcessInstanceId(), task.getId());
	}
}
