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
import com.moext.flowservice.modeler.ui.modeler.domain.ModelHistory;

@Component
public class ModelHistoryRepositoryImpl implements ModelHistoryRepository {

	private static final String NAMESPACE = "com.moext.flowservice.modeler.ui.modeler.domain.ModelHistory.";

	// @Qualifier("flowableModeler")
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	protected UuidIdGenerator idGenerator;

	@Override
	public ModelHistory get(String id) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "selectModelHistory", id);
	}

	@Override
	public List<ModelHistory> findByModelTypAndCreatedBy(String createdBy, Integer modelType) {
		Map<String, Object> params = new HashMap<>();
		params.put("modelType", modelType);
		params.put("createdBy", createdBy);
		return sqlSessionTemplate.selectList(NAMESPACE + "selectModelHistoryByTypeAndCreatedBy", params);
	}

	@Override
	public List<ModelHistory> findByModelId(String modelId) {
		return sqlSessionTemplate.selectList(NAMESPACE + "selectModelHistoryByModelId", modelId);
	}

	@Override
	public void save(ModelHistory modelHistory) {
		if (modelHistory.getId() == null) {
			modelHistory.setId(idGenerator.generateId());
			sqlSessionTemplate.insert(NAMESPACE + "insertModelHistory", modelHistory);
		} else {
			sqlSessionTemplate.update(NAMESPACE + "updateModelHistory", modelHistory);
		}
	}

	@Override
	public void delete(ModelHistory modelHistory) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteModelHistory", modelHistory);
	}

}
