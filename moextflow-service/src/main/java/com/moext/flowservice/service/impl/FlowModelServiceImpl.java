package com.moext.flowservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.component.ProcessModelComponent;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;
import com.moext.flowservice.service.FlowModelService;

@Service
public class FlowModelServiceImpl implements FlowModelService {
	
	@Autowired
	private ProcessModelComponent processModelComponent;
	
	@Override
	public PageResponse<FlowModel> listPage(FlowModelPageRequest pageRequest){
		List<FlowModel> data = processModelComponent.list(pageRequest);
		long total = processModelComponent.count(pageRequest);
		return new PageResponse.Builder<FlowModel>().setPageRequest(pageRequest).setContent(data).setTotalElements(total).build();
	}

}
