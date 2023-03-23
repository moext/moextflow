package com.moext.flowservice.component.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moext.flowservice.component.ProcessModelComponent;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型的Flowable实现
 * @author PengPeng
 *
 */
@Component
public class FlowableProcessModelComponent implements ProcessModelComponent {

	@Autowired
	private RepositoryService repositoryService;
	
	public List<FlowModel> list(FlowModelPageRequest pageRequest) {
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		if (StringUtils.isNotEmpty(pageRequest.getCategory())){
			modelQuery.modelCategory(pageRequest.getCategory());
		}
		List<Model> list =  modelQuery.listPage(pageRequest.getFirstIndex(), pageRequest.getPageSize());
		List<FlowModel> flowModelList = new ArrayList<FlowModel>();
		if(CollectionUtils.isNotEmpty(list)) {
			for(Model model : list) {
				FlowModel flowModel = new FlowModel();
				BeanUtils.copyProperties(model, flowModel);
				flowModelList.add(flowModel);
			}
		}
		return flowModelList;
	}
	
	public long count(FlowModelPageRequest pageRequest) {
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		if (StringUtils.isNotEmpty(pageRequest.getCategory())){
			modelQuery.modelCategory(pageRequest.getCategory());
		}
		return modelQuery.count();
	}
	
}
