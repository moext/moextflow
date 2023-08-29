package com.moext.flowservice.component;

/**
 * 历史组件
 * 
 * @author PengPeng
 */
public interface HistoryComponent {

	/**
	 * 获取某个流程实例指定taskDefinitionKey的历史处理人，按时间反序
	 * 
	 * @param procInsId
	 * @param taskDefKey
	 * @return
	 */
	public String getHistoryAssignee(String procInsId, String taskDefKey);
}
