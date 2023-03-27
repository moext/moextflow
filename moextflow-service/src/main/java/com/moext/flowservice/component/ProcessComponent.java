package com.moext.flowservice.component;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.moext.flowservice.dto.req.ActProcessPageReq;
import com.moext.flowservice.flow.model.StartProcessModel;
import com.moext.flowservice.flow.model.TaskManageModel;
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
	
	/**
	 * 分页查询历史流程列表
	 * @param actProcessPageReq
	 * @return
	 */
	public List<TaskManageModel> listHistoryProcess(ActProcessPageReq actProcessPageReq);
	
	/**
	 * 统计符合条件的历史流程数量
	 * @param actProcessPageReq
	 * @return
	 */
	public long countHistoryProcess(ActProcessPageReq actProcessPageReq);
	
	/**
	 * 从当前节点跳转到指定节点
	 * @param procInsId
	 * @param nodeId
	 * @param toNodeId
	 */
	public void jumpToNode(String procInsId, String nodeId, String toNodeId);
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	public void convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException;
	
}
