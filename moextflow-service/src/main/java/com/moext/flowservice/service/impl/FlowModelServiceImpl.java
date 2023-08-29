package com.moext.flowservice.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.codec.binary.StringUtils;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moext.flowservice.common.PageResponse;
import com.moext.flowservice.component.ProcessModelComponent;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;
import com.moext.flowservice.service.FlowModelService;

import jakarta.transaction.Transactional;

@Service
public class FlowModelServiceImpl implements FlowModelService {

	@Autowired
	private ProcessModelComponent processModelComponent;

	@Override
	public PageResponse<FlowModel> listPage(FlowModelPageRequest pageRequest) {
		List<FlowModel> data = processModelComponent.list(pageRequest);
		long total = processModelComponent.count(pageRequest);
		return new PageResponse.Builder<FlowModel>().setPageRequest(pageRequest).setContent(data)
				.setTotalElements(total).build();
	}

	@Override
	@Transactional
	public void saveOrUpdate(String name, String key, String description, String category)
			throws UnsupportedEncodingException {
		processModelComponent.saveOrUpdate(name, key, description, category);
	}

	@Override
	public String deploy(String id) {
		return processModelComponent.deploy(id);
	}

	@Override
	public void importModel(Model data, InputStream bpmnStream)
			throws UnsupportedEncodingException, XMLStreamException {
		processModelComponent.importModel(data, bpmnStream);

	}

	@Override
	public void export(String id, HttpServletResponse response) {
		processModelComponent.export(id, response);
	}

	@Override
	public void exportAll(HttpServletResponse response) {
		processModelComponent.exportAll(response);
	}

	@Override
	@Transactional
	public boolean updateCategory(String id, String category) {
		Model model = processModelComponent.getModel(id);
		if (model != null) {
			if (!StringUtils.equals(category, model.getCategory())) {
				processModelComponent.updateCategory(id, category);
			}
			return true;
		} else {
			return false;
		}

	}

	@Override
	@Transactional
	public boolean delete(String id) {
		Model model = processModelComponent.getModel(id);
		if (model != null) {
			processModelComponent.delete(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean importListByModelFile(Part file) throws Exception {
		return processModelComponent.importListByModelFile(file);
	}

	@Override
	@Transactional
	public boolean deployUpdated() {
		return processModelComponent.deployUpdated();
	}
}
