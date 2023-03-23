package com.moext.flowservice.component;

import java.util.List;

import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型组件
 * @author PengPeng
 *
 */
public interface ProcessModelComponent {

	/**
	 * 分页查询
	 * @param pageRequest
	 * @return
	 */
	public List<FlowModel> list(FlowModelPageRequest pageRequest);
	
	/**
	 * 按条件统计数量
	 * @param pageRequest
	 * @return
	 */
	public long count(FlowModelPageRequest pageRequest);
}
