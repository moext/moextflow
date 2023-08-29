package com.moext.flowservice.component;

import com.moext.flowservice.flow.model.DynamicAssignee;

/**
 * 动态处理人组件
 * 
 * @author PengPeng
 */
public interface AssigneeComponent {

	public String getFinalAssignee(DynamicAssignee dynamicAssignee);
}
