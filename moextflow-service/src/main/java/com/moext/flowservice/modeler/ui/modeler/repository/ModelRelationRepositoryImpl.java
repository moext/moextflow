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
package com.moext.flowservice.modeler.ui.modeler.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.moext.flowservice.modeler.ui.common.repository.UuidIdGenerator;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelInformation;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelRelation;

@Component
public class ModelRelationRepositoryImpl implements ModelRelationRepository {

	private static final String NAMESPACE = "com.moext.flowservice.modeler.ui.modeler.domain.ModelRelation.";

	// @Qualifier("flowableModeler")
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	protected UuidIdGenerator idGenerator;

	@Override
	public List<ModelRelation> findByParentModelIdAndType(String parentModelId, String type) {
		Map<String, Object> params = new HashMap<>();
		params.put("parentModelId", parentModelId);
		params.put("type", type);
		return sqlSessionTemplate.selectList("selectModelRelationByParentModelIdAndType", params);
	}

	@Override
	public List<ModelInformation> findModelInformationByParentModelId(String parentModelId) {
		return sqlSessionTemplate.selectList(NAMESPACE + "selectModelInformationByParentModelId", parentModelId);
	}

	@Override
	public List<ModelInformation> findModelInformationByChildModelId(String modelId) {
		return sqlSessionTemplate.selectList(NAMESPACE + "selectModelInformationModelId", modelId);
	}

	@Override
	public void deleteModelRelationsForParentModel(String parentModelId) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteModelRelationByParentModelId", parentModelId);
	}

	@Override
	public void save(ModelRelation modelRelation) {
		if (modelRelation.getId() == null) {
			modelRelation.setId(idGenerator.generateId());
			sqlSessionTemplate.insert(NAMESPACE + "insertModelRelation", modelRelation);
		} else {
			sqlSessionTemplate.update(NAMESPACE + "updateModelRelation", modelRelation);
		}
	}

	@Override
	public void delete(ModelRelation modelRelation) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteModelRelation", modelRelation);
	}

}
