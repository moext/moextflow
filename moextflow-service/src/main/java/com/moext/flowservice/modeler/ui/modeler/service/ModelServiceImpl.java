/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moext.flowservice.modeler.ui.modeler.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.cmmn.converter.CmmnXmlConverter;
import org.flowable.cmmn.editor.json.converter.CmmnJsonConverter;
import org.flowable.cmmn.editor.json.converter.util.CmmnModelJsonConverterUtil;
import org.flowable.cmmn.model.CmmnModel;
import org.flowable.dmn.editor.converter.DmnJsonConverterUtil;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.editor.language.json.converter.util.JsonConverterUtil;
import org.flowable.form.model.SimpleFormModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moext.flowservice.modeler.ui.common.service.exception.BadRequestException;
import com.moext.flowservice.modeler.ui.common.service.exception.InternalServerErrorException;
import com.moext.flowservice.modeler.ui.common.service.exception.NotFoundException;
import com.moext.flowservice.modeler.ui.common.util.XmlUtil;
import com.moext.flowservice.modeler.ui.modeler.domain.AbstractModel;
import com.moext.flowservice.modeler.ui.modeler.domain.AppDefinition;
import com.moext.flowservice.modeler.ui.modeler.domain.AppModelDefinition;
import com.moext.flowservice.modeler.ui.modeler.domain.Model;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelHistory;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelRelation;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelRelationTypes;
import com.moext.flowservice.modeler.ui.modeler.model.ModelKeyRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.ModelRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.ReviveModelResultRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.ReviveModelResultRepresentation.UnresolveModelRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.decisiontable.DecisionTableDefinitionRepresentation;
import com.moext.flowservice.modeler.ui.modeler.repository.ModelHistoryRepository;
import com.moext.flowservice.modeler.ui.modeler.repository.ModelRelationRepository;
import com.moext.flowservice.modeler.ui.modeler.repository.ModelRepository;
import com.moext.flowservice.modeler.ui.modeler.repository.ModelSort;
import com.moext.flowservice.modeler.ui.modeler.serviceapi.ModelService;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelServiceImpl.class);

	public static final String currentUserId = "currentUserId";

	public static final String NAMESPACE = "http://flowable.org/modeler";

	protected static final String PROCESS_NOT_FOUND_MESSAGE_KEY = "PROCESS.ERROR.NOT-FOUND";

	@Autowired
	protected ModelImageService modelImageService;

	@Autowired
	protected ModelRepository modelRepository;

	@Autowired
	protected ModelHistoryRepository modelHistoryRepository;

	@Autowired
	protected ModelRelationRepository modelRelationRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

	protected BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

	protected CmmnJsonConverter cmmnJsonConverter = new CmmnJsonConverter();

	protected CmmnXmlConverter cmmnXMLConverter = new CmmnXmlConverter();

	@Override
	public Model getModel(String modelId) {
		Model model = modelRepository.get(modelId);

		if (model == null) {
			NotFoundException modelNotFound = new NotFoundException("No model found with the given id: " + modelId);
			modelNotFound.setMessageKey(PROCESS_NOT_FOUND_MESSAGE_KEY);
			throw modelNotFound;
		}

		return model;
	}

	@Override
	public ModelRepresentation getModelRepresentation(String modelId) {
		Model model = getModel(modelId);
		return new ModelRepresentation(model);
	}

	@Override
	public List<AbstractModel> getModelsByModelType(Integer modelType) {
		return new ArrayList<>(modelRepository.findByModelType(modelType, ModelSort.NAME_ASC));
	}

	@Override
	public ModelHistory getModelHistory(String modelId, String modelHistoryId) {
		// Check if the user has read-rights on the process-model in order to fetch
		// history
		Model model = getModel(modelId);
		ModelHistory modelHistory = modelHistoryRepository.get(modelHistoryId);

		// Check if history corresponds to the current model and is not deleted
		if (modelHistory == null || modelHistory.getRemovalDate() != null
				|| !modelHistory.getModelId().equals(model.getId())) {
			throw new NotFoundException("Process model history not found: " + modelHistoryId);
		}
		return modelHistory;
	}

	@Override
	public byte[] getBpmnXML(AbstractModel model) {
		BpmnModel bpmnModel = getBpmnModel(model);
		return getBpmnXML(bpmnModel);
	}

	@Override
	public byte[] getBpmnXML(BpmnModel bpmnModel) {
		for (Process process : bpmnModel.getProcesses()) {
			if (StringUtils.isNotEmpty(process.getId())) {
				char firstCharacter = process.getId().charAt(0);
				// no digit is allowed as first character
				if (Character.isDigit(firstCharacter)) {
					process.setId("a" + process.getId());
				}
			}
		}
		byte[] xmlBytes = bpmnXMLConverter.convertToXML(bpmnModel);
		return xmlBytes;
	}

	@Override
	public byte[] getCmmnXML(AbstractModel model) {
		CmmnModel cmmnModel = getCmmnModel(model);
		return getCmmnXML(cmmnModel);
	}

	@Override
	public byte[] getCmmnXML(CmmnModel cmmnModel) {
		byte[] xmlBytes = cmmnXMLConverter.convertToXML(cmmnModel);
		return xmlBytes;
	}

	@Override
	public ModelKeyRepresentation validateModelKey(Model model, Integer modelType, String key) {
		ModelKeyRepresentation modelKeyResponse = new ModelKeyRepresentation();
		modelKeyResponse.setKey(key);

		List<Model> models = modelRepository.findByKeyAndType(key, modelType);
		for (Model modelInfo : models) {
			if (model == null || !modelInfo.getId().equals(model.getId())) {
				modelKeyResponse.setKeyAlreadyExists(true);
				modelKeyResponse.setId(modelInfo.getId());
				modelKeyResponse.setName(modelInfo.getName());
				break;
			}
		}

		return modelKeyResponse;
	}

	@Override
	public String createModelJson(ModelRepresentation model) {
		String json = null;
		if (Integer.valueOf(AbstractModel.MODEL_TYPE_FORM).equals(model.getModelType())) {
			try {
				json = objectMapper.writeValueAsString(new SimpleFormModel());
			} catch (Exception e) {
				LOGGER.error("Error creating form model", e);
				throw new InternalServerErrorException("Error creating form");
			}

		} else if (Integer.valueOf(AbstractModel.MODEL_TYPE_DECISION_TABLE).equals(model.getModelType())) {
			try {
				DecisionTableDefinitionRepresentation decisionTableDefinition = new DecisionTableDefinitionRepresentation();
				decisionTableDefinition.setModelVersion("3");
				String decisionTableDefinitionKey = model.getName().replaceAll(" ", "");
				decisionTableDefinition.setKey(decisionTableDefinitionKey);

				json = objectMapper.writeValueAsString(decisionTableDefinition);
			} catch (Exception e) {
				LOGGER.error("Error creating decision table model", e);
				throw new InternalServerErrorException("Error creating decision table");
			}

		} else if (Integer.valueOf(AbstractModel.MODEL_TYPE_APP).equals(model.getModelType())) {
			try {
				json = objectMapper.writeValueAsString(new AppDefinition());
			} catch (Exception e) {
				LOGGER.error("Error creating app definition", e);
				throw new InternalServerErrorException("Error creating app definition");
			}

		} else if (Integer.valueOf(AbstractModel.MODEL_TYPE_CMMN).equals(model.getModelType())) {
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/cmmn1.1#");
			editorNode.set("stencilset", stencilSetNode);
			ObjectNode propertiesNode = objectMapper.createObjectNode();
			propertiesNode.put("case_id", model.getKey());
			propertiesNode.put("name", model.getName());
			if (StringUtils.isNotEmpty(model.getDescription())) {
				propertiesNode.put("documentation", model.getDescription());
			}
			editorNode.set("properties", propertiesNode);

			ArrayNode childShapeArray = objectMapper.createArrayNode();
			editorNode.set("childShapes", childShapeArray);
			ObjectNode childNode = objectMapper.createObjectNode();
			childShapeArray.add(childNode);
			ObjectNode boundsNode = objectMapper.createObjectNode();
			childNode.set("bounds", boundsNode);
			ObjectNode lowerRightNode = objectMapper.createObjectNode();
			boundsNode.set("lowerRight", lowerRightNode);
			lowerRightNode.put("x", 758);
			lowerRightNode.put("y", 754);
			ObjectNode upperLeftNode = objectMapper.createObjectNode();
			boundsNode.set("upperLeft", upperLeftNode);
			upperLeftNode.put("x", 40);
			upperLeftNode.put("y", 40);
			childNode.set("childShapes", objectMapper.createArrayNode());
			childNode.set("dockers", objectMapper.createArrayNode());
			childNode.set("outgoing", objectMapper.createArrayNode());
			childNode.put("resourceId", "casePlanModel");
			ObjectNode stencilNode = objectMapper.createObjectNode();
			childNode.set("stencil", stencilNode);
			stencilNode.put("id", "CasePlanModel");
			json = editorNode.toString();

		} else if (Integer.valueOf(AbstractModel.MODEL_TYPE_DECISION_SERVICE).equals(model.getModelType())) {
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/dmn1.2#");
			editorNode.set("stencilset", stencilSetNode);

			ObjectNode canvasBoundsNode = objectMapper.createObjectNode();
			editorNode.set("bounds", canvasBoundsNode);
			ObjectNode lowerRightCanvasNode = objectMapper.createObjectNode();
			canvasBoundsNode.set("lowerRight", lowerRightCanvasNode);
			lowerRightCanvasNode.put("x", 1200);
			lowerRightCanvasNode.put("y", 1050);
			ObjectNode upperLeftCanvasNode = objectMapper.createObjectNode();
			canvasBoundsNode.set("upperLeft", upperLeftCanvasNode);
			upperLeftCanvasNode.put("x", 0);
			upperLeftCanvasNode.put("y", 0);

			ObjectNode propertiesNode = objectMapper.createObjectNode();
			propertiesNode.put("drd_id", model.getKey());
			propertiesNode.put("name", model.getName());
			if (StringUtils.isNotEmpty(model.getDescription())) {
				propertiesNode.put("documentation", model.getDescription());
			}
			editorNode.set("properties", propertiesNode);

			ArrayNode childShapeArray = objectMapper.createArrayNode();
			editorNode.set("childShapes", childShapeArray);

			ObjectNode childNode = objectMapper.createObjectNode();
			childShapeArray.add(childNode);
			ObjectNode boundsNode = objectMapper.createObjectNode();
			childNode.set("bounds", boundsNode);
			ObjectNode lowerRightNode = objectMapper.createObjectNode();
			boundsNode.set("lowerRight", lowerRightNode);
			lowerRightNode.put("x", 750);
			lowerRightNode.put("y", 554);
			ObjectNode upperLeftNode = objectMapper.createObjectNode();
			boundsNode.set("upperLeft", upperLeftNode);
			upperLeftNode.put("x", 150);
			upperLeftNode.put("y", 74);

			ArrayNode childShapes = objectMapper.createArrayNode();

			childNode.set("childShapes", childShapes);
			childNode.set("dockers", objectMapper.createArrayNode());
			childNode.set("outgoing", objectMapper.createArrayNode());
			childNode.put("resourceId", "expandedDecisionService");

			ObjectNode stencilNode = objectMapper.createObjectNode();
			childNode.set("stencil", stencilNode);
			stencilNode.put("id", "ExpandedDecisionService");

			ObjectNode outgoingDecisionsShape = objectMapper.createObjectNode();
			childShapes.add(outgoingDecisionsShape);

			ObjectNode outgoingDecisionsStencilNode = objectMapper.createObjectNode();
			outgoingDecisionsShape.set("stencil", outgoingDecisionsStencilNode);
			outgoingDecisionsStencilNode.put("id", "OutputDecisionsDecisionServiceSection");

			outgoingDecisionsShape.set("childShapes", objectMapper.createArrayNode());
			outgoingDecisionsShape.set("dockers", objectMapper.createArrayNode());
			outgoingDecisionsShape.set("outgoing", objectMapper.createArrayNode());
			outgoingDecisionsShape.put("resourceId", "outputDecisions");

			ObjectNode outgoingDecisionsBoundsNode = objectMapper.createObjectNode();
			outgoingDecisionsShape.set("bounds", outgoingDecisionsBoundsNode);
			ObjectNode outgoingDecisionsLowerRightNode = objectMapper.createObjectNode();
			outgoingDecisionsBoundsNode.set("lowerRight", outgoingDecisionsLowerRightNode);
			outgoingDecisionsLowerRightNode.put("x", 600);
			outgoingDecisionsLowerRightNode.put("y", 240);
			ObjectNode outgoingDecisionsUpperLeftNode = objectMapper.createObjectNode();
			outgoingDecisionsBoundsNode.set("upperLeft", outgoingDecisionsUpperLeftNode);
			outgoingDecisionsUpperLeftNode.put("x", 0);
			outgoingDecisionsUpperLeftNode.put("y", 0);

			ObjectNode encapsulatedDecisionsShape = objectMapper.createObjectNode();
			childShapes.add(encapsulatedDecisionsShape);

			ObjectNode encapsulatedDecisionsStencilNode = objectMapper.createObjectNode();
			encapsulatedDecisionsShape.set("stencil", encapsulatedDecisionsStencilNode);
			encapsulatedDecisionsStencilNode.put("id", "EncapsulatedDecisionsDecisionServiceSection");

			encapsulatedDecisionsShape.set("childShapes", objectMapper.createArrayNode());
			encapsulatedDecisionsShape.set("dockers", objectMapper.createArrayNode());
			encapsulatedDecisionsShape.set("outgoing", objectMapper.createArrayNode());
			encapsulatedDecisionsShape.put("resourceId", "encapsulatedDecisions");

			ObjectNode encapsulatedDecisionsBoundsNode = objectMapper.createObjectNode();
			encapsulatedDecisionsShape.set("bounds", encapsulatedDecisionsBoundsNode);
			ObjectNode encapsulatedDecisionsLowerRightNode = objectMapper.createObjectNode();
			encapsulatedDecisionsBoundsNode.set("lowerRight", encapsulatedDecisionsLowerRightNode);
			encapsulatedDecisionsLowerRightNode.put("x", 600);
			encapsulatedDecisionsLowerRightNode.put("y", 480);
			ObjectNode encapsulatedDecisionsUpperLeftNode = objectMapper.createObjectNode();
			encapsulatedDecisionsBoundsNode.set("upperLeft", encapsulatedDecisionsUpperLeftNode);
			encapsulatedDecisionsUpperLeftNode.put("x", 0);
			encapsulatedDecisionsUpperLeftNode.put("y", 240);

			json = editorNode.toString();

		} else {
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.set("stencilset", stencilSetNode);
			ObjectNode propertiesNode = objectMapper.createObjectNode();
			propertiesNode.put("process_id", model.getKey());
			propertiesNode.put("name", model.getName());
			if (StringUtils.isNotEmpty(model.getDescription())) {
				propertiesNode.put("documentation", model.getDescription());
			}
			editorNode.set("properties", propertiesNode);

			ArrayNode childShapeArray = objectMapper.createArrayNode();
			editorNode.set("childShapes", childShapeArray);
			ObjectNode childNode = objectMapper.createObjectNode();
			childShapeArray.add(childNode);
			ObjectNode boundsNode = objectMapper.createObjectNode();
			childNode.set("bounds", boundsNode);
			ObjectNode lowerRightNode = objectMapper.createObjectNode();
			boundsNode.set("lowerRight", lowerRightNode);
			lowerRightNode.put("x", 130);
			lowerRightNode.put("y", 193);
			ObjectNode upperLeftNode = objectMapper.createObjectNode();
			boundsNode.set("upperLeft", upperLeftNode);
			upperLeftNode.put("x", 100);
			upperLeftNode.put("y", 163);
			childNode.set("childShapes", objectMapper.createArrayNode());
			childNode.set("dockers", objectMapper.createArrayNode());
			childNode.set("outgoing", objectMapper.createArrayNode());
			childNode.put("resourceId", "startEvent1");
			ObjectNode stencilNode = objectMapper.createObjectNode();
			childNode.set("stencil", stencilNode);
			stencilNode.put("id", "StartNoneEvent");
			json = editorNode.toString();
		}

		return json;
	}

	@Override
	public Model createModel(Model newModel, String createdBy) {
		newModel.setVersion(1);
		newModel.setCreated(Calendar.getInstance().getTime());
		newModel.setCreatedBy(createdBy);
		newModel.setLastUpdated(Calendar.getInstance().getTime());
		newModel.setLastUpdatedBy(createdBy);

		persistModel(newModel);
		return newModel;
	}

	@Override
	public Model createModel(ModelRepresentation model, String editorJson, String createdBy) {
		Model newModel = new Model();
		newModel.setVersion(1);
		newModel.setName(model.getName());
		newModel.setKey(model.getKey());
		newModel.setModelType(model.getModelType());
		newModel.setCreated(Calendar.getInstance().getTime());
		newModel.setCreatedBy(createdBy);
		newModel.setDescription(model.getDescription());
		newModel.setModelEditorJson(editorJson);
		newModel.setLastUpdated(Calendar.getInstance().getTime());
		newModel.setLastUpdatedBy(createdBy);
		newModel.setTenantId(model.getTenantId());

		persistModel(newModel);
		return newModel;
	}

	@Override
	public ModelRepresentation importNewVersion(String modelId, String fileName, InputStream modelStream) {
		Model processModel = getModel(modelId);

		if (fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))) {
			try {
				XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
				InputStreamReader xmlIn = new InputStreamReader(modelStream, StandardCharsets.UTF_8);
				XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
				BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xtr);
				if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
					throw new BadRequestException("No process found in definition " + fileName);
				}

				if (bpmnModel.getLocationMap().size() == 0) {
					throw new BadRequestException("No required BPMN DI information found in definition " + fileName);
				}

				ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

				AbstractModel savedModel = saveModel(modelId, processModel.getName(), processModel.getKey(),
						processModel.getDescription(), modelNode.toString(), true, "Version import via REST service",
						currentUserId);
				return new ModelRepresentation(savedModel);

			} catch (BadRequestException e) {
				throw e;

			} catch (Exception e) {
				throw new BadRequestException("Import failed for " + fileName + ", error message " + e.getMessage());
			}
		} else {
			throw new BadRequestException(
					"Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName);
		}
	}

	@Override
	public Model createNewModelVersion(Model modelObject, String comment, String updatedBy) {
		return (Model) internalCreateNewModelVersion(modelObject, comment, updatedBy, false);
	}

	@Override
	public ModelHistory createNewModelVersionAndReturnModelHistory(Model modelObject, String comment,
			String updatedBy) {
		return (ModelHistory) internalCreateNewModelVersion(modelObject, comment, updatedBy, true);
	}

	protected AbstractModel internalCreateNewModelVersion(Model modelObject, String comment, String updatedBy,
			boolean returnModelHistory) {
		modelObject.setLastUpdated(new Date());
		modelObject.setLastUpdatedBy(updatedBy);
		modelObject.setComment(comment);

		ModelHistory historyModel = createNewModelhistory(modelObject);
		persistModelHistory(historyModel);

		modelObject.setVersion(modelObject.getVersion() + 1);
		persistModel(modelObject);

		return returnModelHistory ? historyModel : modelObject;
	}

	@Override
	public Model saveModel(Model modelObject) {
		return persistModel(modelObject);
	}

	@Override
	public Model saveModel(Model modelObject, String editorJson, byte[] imageBytes, boolean newVersion,
			String newVersionComment, String updatedBy) {

		return internalSave(modelObject.getName(), modelObject.getKey(), modelObject.getDescription(), editorJson,
				newVersion, newVersionComment, imageBytes, updatedBy, modelObject);
	}

	@Override
	public Model saveModel(String modelId, String name, String key, String description, String editorJson,
			boolean newVersion, String newVersionComment, String updatedBy) {

		Model modelObject = modelRepository.get(modelId);
		return internalSave(name, key, description, editorJson, newVersion, newVersionComment, null, updatedBy,
				modelObject);
	}

	protected Model internalSave(String name, String key, String description, String editorJson, boolean newVersion,
			String newVersionComment, byte[] imageBytes, String updatedBy, Model modelObject) {

		if (!newVersion) {

			modelObject.setLastUpdated(new Date());
			modelObject.setLastUpdatedBy(updatedBy);
			modelObject.setName(name);
			modelObject.setKey(key);
			modelObject.setDescription(description);
			modelObject.setModelEditorJson(editorJson);

			if (imageBytes != null) {
				modelObject.setThumbnail(imageBytes);
			}

		} else {

			ModelHistory historyModel = createNewModelhistory(modelObject);
			persistModelHistory(historyModel);

			modelObject.setVersion(modelObject.getVersion() + 1);
			modelObject.setLastUpdated(new Date());
			modelObject.setLastUpdatedBy(updatedBy);
			modelObject.setName(name);
			modelObject.setKey(key);
			modelObject.setDescription(description);
			modelObject.setModelEditorJson(editorJson);
			modelObject.setComment(newVersionComment);

			if (imageBytes != null) {
				modelObject.setThumbnail(imageBytes);
			}
		}

		return persistModel(modelObject);
	}

	@Override
	public void deleteModel(String modelId) {

		Model model = modelRepository.get(modelId);
		if (model == null) {
			throw new IllegalArgumentException("No model found with id: " + modelId);
		}

		// Fetch current model history list
		List<ModelHistory> history = modelHistoryRepository.findByModelId(model.getId());

		// Move model to history and mark removed
		ModelHistory historyModel = createNewModelhistory(model);
		historyModel.setRemovalDate(Calendar.getInstance().getTime());
		persistModelHistory(historyModel);

		deleteModelAndChildren(model);
	}

	protected void deleteModelAndChildren(Model model) {

		// Models have relations with each other, in all kind of wicked and funny ways.
		// Hence, we remove first all relations, comments, etc. while collecting all
		// models.
		// Then, once all foreign key problem makers are removed, we remove the models

		List<Model> allModels = new ArrayList<>();
		internalDeleteModelAndChildren(model, allModels);

		for (Model modelToDelete : allModels) {
			modelRepository.delete(modelToDelete);
		}
	}

	protected void internalDeleteModelAndChildren(Model model, List<Model> allModels) {
		// Delete all related data
		modelRelationRepository.deleteModelRelationsForParentModel(model.getId());

		allModels.add(model);
	}

	@Override
	public ReviveModelResultRepresentation reviveProcessModelHistory(ModelHistory modelHistory, String userId,
			String newVersionComment) {
		Model latestModel = modelRepository.get(modelHistory.getModelId());
		if (latestModel == null) {
			throw new IllegalArgumentException("No process model found with id: " + modelHistory.getModelId());
		}

		// Store the current model in history
		ModelHistory latestModelHistory = createNewModelhistory(latestModel);
		persistModelHistory(latestModelHistory);

		// Populate the actual latest version with the properties in the historic model
		latestModel.setVersion(latestModel.getVersion() + 1);
		latestModel.setLastUpdated(new Date());
		latestModel.setLastUpdatedBy(userId);
		latestModel.setName(modelHistory.getName());
		latestModel.setKey(modelHistory.getKey());
		latestModel.setDescription(modelHistory.getDescription());
		latestModel.setModelEditorJson(modelHistory.getModelEditorJson());
		latestModel.setModelType(modelHistory.getModelType());
		latestModel.setComment(newVersionComment);
		persistModel(latestModel);

		ReviveModelResultRepresentation result = new ReviveModelResultRepresentation();

		// For apps, we need to make sure the referenced processes exist as models.
		// It could be the user has deleted the process model in the meantime. We give
		// back that info to the user.
		if (latestModel.getModelType() == AbstractModel.MODEL_TYPE_APP) {
			if (StringUtils.isNotEmpty(latestModel.getModelEditorJson())) {
				try {
					AppDefinition appDefinition = objectMapper.readValue(latestModel.getModelEditorJson(),
							AppDefinition.class);
					for (AppModelDefinition appModelDefinition : appDefinition.getModels()) {
						if (modelRepository.get(appModelDefinition.getId()) == null) {
							result.getUnresolvedModels()
									.add(new UnresolveModelRepresentation(appModelDefinition.getId(),
											appModelDefinition.getName(), appModelDefinition.getLastUpdatedBy()));
						}
					}
				} catch (Exception e) {
					LOGGER.error("Could not deserialize app model json (id = {})", latestModel.getId(), e);
				}
			}
		}

		return result;
	}

	@Override
	public BpmnModel getBpmnModel(AbstractModel model) {
		BpmnModel bpmnModel = null;
		try {
			ConverterContext converterContext = new ConverterContext(this, objectMapper);
			List<Model> referencedModels = modelRepository.findByParentModelId(model.getId());
			for (Model childModel : referencedModels) {
				if (Model.MODEL_TYPE_FORM == childModel.getModelType()) {
					converterContext.addFormModel(childModel);

				} else if (Model.MODEL_TYPE_DECISION_TABLE == childModel.getModelType()) {
					converterContext.addDecisionTableModel(childModel);

				} else if (Model.MODEL_TYPE_DECISION_SERVICE == childModel.getModelType()) {
					converterContext.addDecisionServiceModel(childModel);

				}
			}

			bpmnModel = getBpmnModel(model, converterContext);

		} catch (Exception e) {
			LOGGER.error("Could not generate BPMN 2.0 model for {}", model.getId(), e);
			throw new InternalServerErrorException("Could not generate BPMN 2.0 model");
		}

		return bpmnModel;
	}

	@Override
	public BpmnModel getBpmnModel(AbstractModel model, ConverterContext appConverterContext) {
		try {
			ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
			return bpmnJsonConverter.convertToBpmnModel(editorJsonNode, appConverterContext);

		} catch (Exception e) {
			LOGGER.error("Could not generate BPMN 2.0 model for {}", model.getId(), e);
			throw new InternalServerErrorException("Could not generate BPMN 2.0 model");
		}
	}

	@Override
	public CmmnModel getCmmnModel(AbstractModel model) {
		CmmnModel cmmnModel = null;
		try {

			ConverterContext converterContext = new ConverterContext(this, objectMapper);
			List<Model> referencedModels = modelRepository.findByParentModelId(model.getId());
			for (Model childModel : referencedModels) {
				if (Model.MODEL_TYPE_FORM == childModel.getModelType()) {
					converterContext.addFormModel(childModel);

				} else if (Model.MODEL_TYPE_DECISION_TABLE == childModel.getModelType()) {
					converterContext.addDecisionTableModel(childModel);

				} else if (Model.MODEL_TYPE_DECISION_SERVICE == childModel.getModelType()) {
					converterContext.addDecisionServiceModel(childModel);

				} else if (Model.MODEL_TYPE_CMMN == childModel.getModelType()) {
					converterContext.addCaseModel(childModel);

				} else if (Model.MODEL_TYPE_BPMN == childModel.getModelType()) {
					converterContext.addProcessModel(childModel);

				}
			}

			cmmnModel = getCmmnModel(model, converterContext);

		} catch (Exception e) {
			LOGGER.error("Could not generate CMMN model for {}", model.getId(), e);
			throw new InternalServerErrorException("Could not generate CMMN model");
		}

		return cmmnModel;
	}

	@Override
	public CmmnModel getCmmnModel(AbstractModel model, ConverterContext converterContext) {

		try {
			ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
			return cmmnJsonConverter.convertToCmmnModel(editorJsonNode, converterContext);

		} catch (Exception e) {
			LOGGER.error("Could not generate CMMN model for {}", model.getId(), e);
			throw new InternalServerErrorException("Could not generate CMMN model");
		}
	}

	protected void addOrUpdateExtensionElement(String name, String value, UserTask userTask) {
		List<ExtensionElement> extensionElements = userTask.getExtensionElements().get(name);

		ExtensionElement extensionElement;

		if (CollectionUtils.isNotEmpty(extensionElements)) {
			extensionElement = extensionElements.get(0);
		} else {
			extensionElement = new ExtensionElement();
		}
		extensionElement.setNamespace(NAMESPACE);
		extensionElement.setNamespacePrefix("modeler");
		extensionElement.setName(name);
		extensionElement.setElementText(value);

		if (CollectionUtils.isEmpty(extensionElements)) {
			userTask.addExtensionElement(extensionElement);
		}
	}

	@Override
	public Long getModelCountForUser(String userId, int modelType) {
		return modelRepository.countByModelTypeAndCreatedBy(modelType, userId);
	}

	protected Model persistModel(Model model) {

		if (StringUtils.isNotEmpty(model.getModelEditorJson())) {

			// Parse json to java
			ObjectNode jsonNode = null;
			try {
				jsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
			} catch (Exception e) {
				LOGGER.error("Could not deserialize json model", e);
				throw new InternalServerErrorException("Could not deserialize json model");
			}

			if ((model.getModelType() == null || model.getModelType().intValue() == Model.MODEL_TYPE_BPMN)) {

				// Thumbnail
				byte[] thumbnail = modelImageService.generateThumbnailImage(model, jsonNode);
				if (thumbnail != null) {
					model.setThumbnail(thumbnail);
				}

				modelRepository.save(model);

				// Relations
				handleBpmnProcessFormModelRelations(model, jsonNode);
				handleBpmnProcessDecisionTaskModelRelations(model, jsonNode);

			} else if (model.getModelType().intValue() == Model.MODEL_TYPE_CMMN) {

				// Thumbnail
				byte[] thumbnail = modelImageService.generateCmmnThumbnailImage(model, jsonNode);
				if (thumbnail != null) {
					model.setThumbnail(thumbnail);
				}

				modelRepository.save(model);

				// Relations
				handleCmmnFormModelRelations(model, jsonNode);
				handleCmmnDecisionModelRelations(model, jsonNode);
				handleCmmnCaseModelRelations(model, jsonNode);
				handleCmmnProcessModelRelations(model, jsonNode);

			} else if (model.getModelType().intValue() == Model.MODEL_TYPE_FORM
					|| model.getModelType().intValue() == Model.MODEL_TYPE_DECISION_TABLE) {

				jsonNode.put("name", model.getName());
				jsonNode.put("key", model.getKey());
				modelRepository.save(model);

			} else if (model.getModelType().intValue() == Model.MODEL_TYPE_APP) {

				modelRepository.save(model);
				handleAppModelProcessRelations(model, jsonNode);
			} else if (model.getModelType().intValue() == Model.MODEL_TYPE_DECISION_SERVICE) {
				// Thumbnail
				byte[] thumbnail = modelImageService.generateDmnThumbnailImage(model, jsonNode);
				if (thumbnail != null) {
					model.setThumbnail(thumbnail);
				}

				modelRepository.save(model);
				handleDecisionServiceModelDecisionTableRelations(model, jsonNode);
			}
		}

		return model;
	}

	protected void persistModelHistory(ModelHistory modelHistory) {
		modelHistoryRepository.save(modelHistory);
	}

	protected void handleBpmnProcessFormModelRelations(AbstractModel bpmnProcessModel, ObjectNode editorJsonNode) {
		List<JsonNode> formReferenceNodes = JsonConverterUtil
				.filterOutJsonNodes(JsonConverterUtil.getBpmnProcessModelFormReferences(editorJsonNode));
		Set<String> formIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(formReferenceNodes, "id");

		handleModelRelations(bpmnProcessModel, formIds, ModelRelationTypes.TYPE_FORM_MODEL_CHILD);
	}

	protected void handleBpmnProcessDecisionTaskModelRelations(AbstractModel bpmnProcessModel,
			ObjectNode editorJsonNode) {
		List<JsonNode> decisionTableNodes = JsonConverterUtil
				.filterOutJsonNodes(JsonConverterUtil.getBpmnProcessModelDecisionTableReferences(editorJsonNode));
		Set<String> decisionTableIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(decisionTableNodes, "id");

		handleModelRelations(bpmnProcessModel, decisionTableIds, ModelRelationTypes.TYPE_DECISION_TABLE_MODEL_CHILD);

		List<JsonNode> decisionServiceNodes = JsonConverterUtil
				.filterOutJsonNodes(JsonConverterUtil.getBpmnProcessModelDecisionServiceReferences(editorJsonNode));
		Set<String> decisionServiceIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(decisionServiceNodes,
				"id");

		handleModelRelations(bpmnProcessModel, decisionServiceIds,
				ModelRelationTypes.TYPE_DECISION_SERVICE_MODEL_CHILD);
	}

	protected void handleCmmnFormModelRelations(AbstractModel caseModel, ObjectNode editorJsonNode) {
		List<JsonNode> formReferenceNodes = CmmnModelJsonConverterUtil
				.filterOutJsonNodes(CmmnModelJsonConverterUtil.getCmmnModelFormReferences(editorJsonNode));
		Set<String> formIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(formReferenceNodes, "id");

		handleModelRelations(caseModel, formIds, ModelRelationTypes.TYPE_FORM_MODEL_CHILD);
	}

	protected void handleCmmnDecisionModelRelations(AbstractModel caseModel, ObjectNode editorJsonNode) {
		List<JsonNode> processReferenceNodes = CmmnModelJsonConverterUtil
				.filterOutJsonNodes(CmmnModelJsonConverterUtil.getCmmnModelDecisionTableReferences(editorJsonNode));
		Set<String> processModelIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(processReferenceNodes, "id");

		handleModelRelations(caseModel, processModelIds, ModelRelationTypes.TYPE_DECISION_TABLE_MODEL_CHILD);
	}

	protected void handleCmmnCaseModelRelations(AbstractModel caseModel, ObjectNode editorJsonNode) {
		List<JsonNode> caseReferenceNodes = CmmnModelJsonConverterUtil
				.filterOutJsonNodes(CmmnModelJsonConverterUtil.getCmmnModelCaseReferences(editorJsonNode));
		Set<String> caseModelIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(caseReferenceNodes, "id");

		handleModelRelations(caseModel, caseModelIds, ModelRelationTypes.TYPE_CASE_MODEL_CHILD);
	}

	protected void handleCmmnProcessModelRelations(AbstractModel caseModel, ObjectNode editorJsonNode) {
		List<JsonNode> processReferenceNodes = CmmnModelJsonConverterUtil
				.filterOutJsonNodes(CmmnModelJsonConverterUtil.getCmmnModelProcessReferences(editorJsonNode));
		Set<String> processModelIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(processReferenceNodes, "id");

		handleModelRelations(caseModel, processModelIds, ModelRelationTypes.TYPE_PROCESS_MODEL_CHILD);
	}

	protected void handleAppModelProcessRelations(AbstractModel appModel, ObjectNode appModelJsonNode) {
		Set<String> processModelIds = JsonConverterUtil.getAppModelReferencedModelIds(appModelJsonNode);
		handleModelRelations(appModel, processModelIds, ModelRelationTypes.TYPE_PROCESS_MODEL);
	}

	protected void handleDecisionServiceModelDecisionTableRelations(AbstractModel decisionServiceModel,
			ObjectNode editorJsonNode) {
		List<JsonNode> decisionTableNodes = DmnJsonConverterUtil
				.filterOutJsonNodes(DmnJsonConverterUtil.getDmnModelDecisionTableReferences(editorJsonNode));
		Set<String> decisionTableIds = JsonConverterUtil.gatherStringPropertyFromJsonNodes(decisionTableNodes, "id");

		handleModelRelations(decisionServiceModel, decisionTableIds,
				ModelRelationTypes.TYPE_DECISION_TABLE_MODEL_CHILD);
	}

	/**
	 * Generic handling of model relations: deleting/adding where needed.
	 */
	protected void handleModelRelations(AbstractModel bpmnProcessModel, Set<String> idsReferencedInJson,
			String relationshipType) {

		// Find existing persisted relations
		List<ModelRelation> persistedModelRelations = modelRelationRepository
				.findByParentModelIdAndType(bpmnProcessModel.getId(), relationshipType);

		// if no ids referenced now, just delete them all
		if (idsReferencedInJson == null || idsReferencedInJson.size() == 0) {
			for (ModelRelation modelRelation : persistedModelRelations) {
				modelRelationRepository.delete(modelRelation);
			}
			return;
		}

		Set<String> alreadyPersistedModelIds = new HashSet<>(persistedModelRelations.size());
		for (ModelRelation persistedModelRelation : persistedModelRelations) {
			if (!idsReferencedInJson.contains(persistedModelRelation.getModelId())) {
				// model used to be referenced, but not anymore. Delete it.
				modelRelationRepository.delete(persistedModelRelation);
			} else {
				alreadyPersistedModelIds.add(persistedModelRelation.getModelId());
			}
		}

		// Loop over all referenced ids and see which one are new
		for (String idReferencedInJson : idsReferencedInJson) {

			// if model is referenced, but it is not yet persisted = create it
			if (!alreadyPersistedModelIds.contains(idReferencedInJson)) {

				// Check if model actually still exists. Don't create the relationship if it
				// doesn't exist. The client UI will have cope with this too.
				if (modelRepository.get(idReferencedInJson) != null) {
					modelRelationRepository
							.save(new ModelRelation(bpmnProcessModel.getId(), idReferencedInJson, relationshipType));
				}
			}
		}
	}

	protected ModelHistory createNewModelhistory(Model model) {
		ModelHistory historyModel = new ModelHistory();
		historyModel.setName(model.getName());
		historyModel.setKey(model.getKey());
		historyModel.setDescription(model.getDescription());
		historyModel.setCreated(model.getCreated());
		historyModel.setLastUpdated(model.getLastUpdated());
		historyModel.setCreatedBy(model.getCreatedBy());
		historyModel.setLastUpdatedBy(model.getLastUpdatedBy());
		historyModel.setModelEditorJson(model.getModelEditorJson());
		historyModel.setModelType(model.getModelType());
		historyModel.setVersion(model.getVersion());
		historyModel.setModelId(model.getId());
		historyModel.setComment(model.getComment());
		historyModel.setTenantId(model.getTenantId());

		return historyModel;
	}

	protected void populateModelBasedOnHistory(Model model, ModelHistory basedOn) {
		model.setName(basedOn.getName());
		model.setKey(basedOn.getKey());
		model.setDescription(basedOn.getDescription());
		model.setCreated(basedOn.getCreated());
		model.setLastUpdated(basedOn.getLastUpdated());
		model.setCreatedBy(basedOn.getCreatedBy());
		model.setLastUpdatedBy(basedOn.getLastUpdatedBy());
		model.setModelEditorJson(basedOn.getModelEditorJson());
		model.setModelType(basedOn.getModelType());
		model.setVersion(basedOn.getVersion());
		model.setComment(basedOn.getComment());
		model.setTenantId(basedOn.getTenantId());
	}

	protected Map<String, String> convertToModelKeyMap(Map<String, Model> modelMap) {
		Map<String, String> modelKeyMap = new HashMap<>();
		if (modelMap != null) {
			for (Model formModel : modelMap.values()) {
				modelKeyMap.put(formModel.getId(), formModel.getKey());
			}
		}

		return modelKeyMap;
	}
}
