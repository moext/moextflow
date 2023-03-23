package com.moext.flowservice.service;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型服务
 * @author PengPeng
 */
public interface FlowModelService {

	/**
	 * 按条件分页查询
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<FlowModel> listPage(FlowModelPageRequest pageRequest);

}
