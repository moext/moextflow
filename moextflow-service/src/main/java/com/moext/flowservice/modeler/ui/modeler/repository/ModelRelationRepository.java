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

import java.util.List;

import com.moext.flowservice.modeler.ui.modeler.domain.ModelInformation;
import com.moext.flowservice.modeler.ui.modeler.domain.ModelRelation;

/**
 * @author Joram Barrez
 */
public interface ModelRelationRepository {

	void deleteModelRelationsForParentModel(String parentModelId);

	void save(ModelRelation modelRelation);

	void delete(ModelRelation modelRelation);

	List<ModelRelation> findByParentModelIdAndType(String parentModelId, String type);

	List<ModelInformation> findModelInformationByParentModelId(String parentModelId);

	List<ModelInformation> findModelInformationByChildModelId(String modelId);

}
