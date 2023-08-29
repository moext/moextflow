package com.moext.flowservice.component.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moext.flowservice.component.HistoryComponent;

@Component
public class FlowableHistoryComponent implements HistoryComponent {

	@Autowired
	private HistoryService historyService;

	@Override
	public String getHistoryAssignee(String procInsId, String taskDefKey) {
		List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(procInsId).taskDefinitionKey(taskDefKey).orderByTaskId().desc().list();
		if (CollectionUtils.isNotEmpty(historicTaskInstanceList)) {
			for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
				String assignee = historicTaskInstance.getAssignee();
				if (StringUtils.isNotBlank(assignee)) {
					return assignee;
				}
			}
		}
		return null;
	}
}
