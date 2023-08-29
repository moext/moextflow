package com.moext.flowservice.component.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moext.flowservice.component.ProcessModelComponent;
import com.moext.flowservice.dto.req.FlowModelPageRequest;
import com.moext.flowservice.flow.model.FlowModel;

/**
 * 流程模型的Flowable实现 Flowable
 * 6.4.1版后不用act_re_model表来保存流程模型文件,改为用act_de_model表来保存，但对应的RepositoryService操作的仍然是act_re_model表
 * 
 * @author PengPeng
 *
 */
//@Deprecated 
@Component
public class FlowableProcessModelComponent implements ProcessModelComponent {

	private static Logger logger = LoggerFactory.getLogger(FlowableProcessModelComponent.class);

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public List<FlowModel> list(FlowModelPageRequest pageRequest) {
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		if (StringUtils.isNotEmpty(pageRequest.getCategory())) {
			modelQuery.modelCategory(pageRequest.getCategory());
		}
		List<Model> list = modelQuery.listPage(pageRequest.getFirstIndex(), pageRequest.getPageSize());
		List<FlowModel> flowModelList = new ArrayList<FlowModel>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Model model : list) {
				FlowModel flowModel = new FlowModel();
				BeanUtils.copyProperties(model, flowModel);
				flowModelList.add(flowModel);
			}
		}
		return flowModelList;
	}

	@Override
	public long count(FlowModelPageRequest pageRequest) {
		ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
		if (StringUtils.isNotEmpty(pageRequest.getCategory())) {
			modelQuery.modelCategory(pageRequest.getCategory());
		}
		return modelQuery.count();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void saveOrUpdate(String name, String key, String description, String category)
			throws UnsupportedEncodingException {
		Model modelData = this.getModelByKey(key);
		if (modelData == null) {
			modelData = repositoryService.newModel();
			modelData.setVersion(1);
		} else {
			modelData.setVersion(modelData.getVersion() + 1);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode properties = objectMapper.createObjectNode();
		properties.put("process_author", "MoextFlow");
		editorNode.put("properties", properties);
		ObjectNode stencilset = objectMapper.createObjectNode();
		stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilset);

		modelData.setKey(key);
		modelData.setName(name);
		modelData.setCategory(category);

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("UTF-8"));

	}

	@Override
	public String deploy(String id) {
		String message = "";
		try {
			Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			String processName = modelData.getName();
			if (!StringUtils.endsWith(processName, ".bpmn20.xml")) {
				processName += ".bpmn20.xml";
			}
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
					.addInputStream(processName, in).deploy();

			// 设置流程分类
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
					.deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : list) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
				message = "部署成功，流程ID=" + processDefinition.getId();
			}
			if (list.size() == 0) {
				throw new IllegalArgumentException("部署失败，没有流程。");
			}
		} catch (Exception e) {
			throw new FlowableException("设计模型图不正确，检查模型正确性，模型ID=" + id, e);
		}
		return message;
	}

	@Override
	public void importModel(Model data, InputStream bpmnStream)
			throws UnsupportedEncodingException, XMLStreamException {
		// inputStream转成BpmnModel
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		// new 个 model 重新赋值
		Model modelData = repositoryService.newModel();
		modelData.setKey(data.getKey());
		modelData.setName(data.getName());
		modelData.setCategory(data.getCategory());// .getDeploymentId());
		modelData.setDeploymentId(data.getDeploymentId());
		modelData.setVersion(Integer
				.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(data.getKey()).count() + 1)));
		modelData.setMetaInfo(data.getMetaInfo());
		// 模型数据保存
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("UTF-8"));
	}

	@Override
	public void export(String id, HttpServletResponse response) {
		try {
			List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
			Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			// 存model 的map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bpmnBytes", bpmnBytes);
			map.put("modelData", modelData);
			modelList.add(map);
			// 序列化对象
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			byte[] mapBytes = null;
			try {
				baos = new ByteArrayOutputStream();
				// 创建输出流
				oos = new ObjectOutputStream(baos);
				// 输出流写操作
				oos.writeObject(modelList);
				// 关闭流操作
				oos.flush();
				oos.close();
				// 返回Byte数组
				mapBytes = baos.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ByteArrayInputStream in = new ByteArrayInputStream(mapBytes);
			String filename = bpmnModel.getMainProcess().getId() + ".model";
			// 先setHeader再copy,否则超过10kb的文件不会下载而是直接显示在页面上
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			IOUtils.copy(in, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			throw new FlowableException("导出model文件失败，模型ID=" + id, e);
		}

	}

	@Override
	public void exportAll(HttpServletResponse response) {
		try {
			// 需导出的modelList
			List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
			// 查询所有model
			List<Model> list = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc()
					.list();
			// 遍历处理model数据
			for (Model modelData : list) {
				BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
				JsonNode editorNode = new ObjectMapper()
						.readTree(repositoryService.getModelEditorSource(modelData.getId()));
				BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
				// 存model 的map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bpmnBytes", bpmnBytes);
				map.put("modelData", modelData);
				modelList.add(map);
			}
			// 序列化对象
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			byte[] mapBytes = null;
			try {
				baos = new ByteArrayOutputStream();
				// 创建输出流
				oos = new ObjectOutputStream(baos);
				// 输出流写操作
				oos.writeObject(modelList);
				// 关闭流操作
				oos.flush();
				oos.close();
				// 返回Byte数组
				mapBytes = baos.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ByteArrayInputStream in = new ByteArrayInputStream(mapBytes);
			String filename = "total.model";
			// 先setHeader再copy,否则超过10kb的文件不会下载而是直接显示在页面上
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			IOUtils.copy(in, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FlowableException("导出model文件失败", e);
		}

	}

	@Override
	public void updateCategory(String id, String category) {
		Model modelData = repositoryService.getModel(id);
		modelData.setCategory(category);
		repositoryService.saveModel(modelData);
	}

	@Override
	public void delete(String id) {
		repositoryService.deleteModel(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importListByModelFile(Part file) throws Exception {
		if (file.getSize() == 0) {
			throw new IllegalArgumentException("文件不能为空！");
		} else {
			String originalFilename = file.getSubmittedFileName();
			String extName = FilenameUtils.getExtension(originalFilename);
			extName = extName.toLowerCase();
			if (!extName.matches("^(model)$")) {
				throw new IllegalArgumentException("不支持" + extName + "类型的文件！");
			} else {
				// 接收文件modelList
				List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
				// 反序列化数据
				try {
					// 创建输出流对象
					ObjectInputStream ois = new ObjectInputStream(file.getInputStream());
					// 读取流信息
					modelList = (List<Map<String, Object>>) ois.readObject();
					ois.close();
				} catch (Exception e) {
					logger.error("", e);
				}
				if (modelList != null) {
					for (Map<String, Object> map : modelList) {
						// HashMap<String , Object> map = new HashMap<String , Object>();
						InputStream bpmnStream = null;
						// 读出model
						Model modelData = (Model) map.get("modelData");
						// 读出bpmn文件流
						byte[] bpmnBytes = (byte[]) map.get("bpmnBytes");
						bpmnStream = new ByteArrayInputStream(bpmnBytes);
						this.importModel(modelData, bpmnStream);
					}
				}
				return true;
			}
		}
	}

	@Override
	public boolean deployUpdated() {
		// 查询所有已部署流程
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion()
				.orderByProcessDefinitionKey().asc();
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
		// 查询所有模型
		List<Model> modelList = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc()
				.list();
		if (modelList != null) {
			for (Model model : modelList) {
				Date modelLastUpdateTime = model.getLastUpdateTime();// 最后更新时间
				String modelKey = model.getKey();// 模型标识
				if (processDefinitionList != null) {
					for (ProcessDefinition processDefinition : processDefinitionList) {
						String deploymentId = processDefinition.getDeploymentId();
						Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId)
								.singleResult();
						Date deployDate = deployment.getDeploymentTime();// 部署时间
						String deployKey = processDefinition.getKey();
						// 找出 部署时间<模型最后更新时间 的流程进行部署。
						if (StringUtils.isNoneBlank(modelKey) && StringUtils.isNoneBlank(deployKey)
								&& modelKey.equals(deployKey) && modelLastUpdateTime != null && deployDate != null
								&& deployDate.before(modelLastUpdateTime)) {
							this.deploy(model.getId());
							break;// 匹配后直接终止本轮循环
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public Model getModel(String modelId) {
		return repositoryService.getModel(modelId);
	}

	@Override
	public Model getModelByKey(String key) {
		return repositoryService.createModelQuery().modelKey(key).singleResult();
	}
}
