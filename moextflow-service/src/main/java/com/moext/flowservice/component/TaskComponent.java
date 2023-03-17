package com.moext.flowservice.component;

import java.util.Map;

import org.flowable.task.api.Task;

/**
 * 任务组件
 * @author PengPeng
 */
public interface TaskComponent {

	/**
	 * 完成第一个任务
	 * @param userName
	 * @param procInsId
	 * @param comment
	 * @return
	 */
	public String completeFirstTask(String userName, String procInsId, String comment);
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars);
	
	/**
	 * 提交任务，并保存意见
	 * @param taskId
	 * @param procInsId
	 * @param comment
	 * @param vars
	 */
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars);
	
	/**
	 * 分配任务
	 * @param taskId 任务ID
	 * @param userId 用户名
	 */
	public void setAssignee(String taskId, String userId);
	
	/**
	 * 获取当前审批任务，如果当前任务有多个，返回最先创建的任务
	 * @param procInsId
	 * @return
	 */
	public Task getCurrentTask(String procInsId);
}
