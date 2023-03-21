package com.moext.flowservice.service;

import java.util.List;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.data.gen.model.TodoTaskExample;
import com.moext.flowservice.dto.req.ApproveProcessReq;
import com.moext.flowservice.dto.req.StartProcessReq;
import com.moext.flowservice.dto.req.TodoListCountReq;
import com.moext.flowservice.dto.req.TodoListPageReq;
import com.moext.flowservice.flow.model.TaskNodeModel;

/**
 * 待办任务Service
 * @author PengPeng
 */
public interface TodoTaskService {

	/**
	 * 按待办ID构建查询条件，可单元测试
	 * @param todoId
	 * @return
	 */
	public TodoTaskExample toExampleByTodoId(String todoId);
	
	/**
	 * 按流程ID及任务ID构建查询条件，可单元测试
	 * @param procId
	 * @param taskId
	 * @return
	 */
	public TodoTaskExample toExampleByByProcIdAndTaskId(String procId, String taskId);
	
	/**
	 * 从待办列表中获取一条记录，可单元测试
	 * @param list
	 * @return
	 */
	public TodoTask getOne(List<TodoTask> list);
	
	/**
	 * 分页按条件查询待办列表
	 * @param todoListPageReq
	 * @return
	 */
	public PageResponse<TodoTask> listPage(TodoListPageReq todoListPageReq);
	
	/**
	 * 按条件查询待办数量
	 * @param todoListPageReq
	 * @return
	 */
	public Long count(TodoListCountReq todoListCountReq);
	
	/**
	 * 按待办ID查询待办
	 * @param todoId
	 * @return
	 */
	public TodoTask getByTodoId(String todoId);
	
	/**
	 * 按待办ID删除待办
	 * @param todoId
	 * @return
	 */
	public boolean deleteByTodoId(String todoId);
	
	/**
	 * 按流程及任务ID查询待办
	 * @param procId
	 * @param taskId
	 * @return
	 */
	public TodoTask getByProcIdAndTaskId(String procId, String taskId);
	
	/**
	 * 发起流程
	 * @param startProcessReq
	 * @return
	 */
	public String startProcess(StartProcessReq startProcessReq);
	
	/**
	 * 任务分配时生成待办
	 * @param todoTask
	 */
	public void insertTodoWhenTaskAssigneed(TodoTask todoTask);
	
	/**
	 * 任务完成时待办改已办
	 * @param todoTask
	 */
	public void updateWhenTaskComplete(TodoTask todoTask);
	
	/**
	 * 流程结束时已办改办结
	 * @param processInstanceId
	 */
	public void updateWhenProcessComplete(String processInstanceId);
	
	/**
	 * 审批流程
	 * @param approveProcessReq
	 */
	public boolean approveProcess(ApproveProcessReq approveProcessReq);
	
	/**
	 * 查询可退节点列表
	 * @param todoId
	 * @return
	 */
	public List<TaskNodeModel> listBackNode(String todoId);
	
	/**
	 * 查询所有待审批节点
	 * @param todoId
	 * @return
	 */
	public List<TaskNodeModel> listAllNode(String todoId);
	
	/**
	 * 查询当前待审批任务
	 * @param todoId
	 * @return
	 */
	public TodoTask getCurrentTask(String todoId);
	

}
