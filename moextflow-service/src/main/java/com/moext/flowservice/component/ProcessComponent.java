package com.moext.flowservice.component;

import java.util.List;

import com.moext.flowservice.flow.model.StartProcessModel;
import com.moext.flowservice.flow.model.TaskNodeModel;

/**
 * 流程组件
 * @author PengPeng
 */
public interface ProcessComponent {

	/**
	 * 发起流程并返回流程实例ID
	 * @param startProcessModel 流程发起模型
	 * @return
	 */
	public String startProcess(StartProcessModel startProcessModel);
	
	/**
	 * 查询可回退节点定义列表
	 * @param taskId
	 * @return
	 */
	public List<TaskNodeModel> listBackNode(String taskId);
	
	/**
	 * 查询所有审批节点列表
	 * @param procInsId
	 * @return
	 */
	public List<TaskNodeModel> listAllNode(String procInsId);
}
