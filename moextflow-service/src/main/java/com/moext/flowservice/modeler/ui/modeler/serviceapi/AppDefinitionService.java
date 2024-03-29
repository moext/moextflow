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
package com.moext.flowservice.modeler.ui.modeler.serviceapi;

import java.util.List;

import org.flowable.idm.api.User;

import com.moext.flowservice.modeler.ui.modeler.model.AppDefinitionRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.AppDefinitionSaveRepresentation;
import com.moext.flowservice.modeler.ui.modeler.model.AppDefinitionUpdateResultRepresentation;

public interface AppDefinitionService {

	AppDefinitionRepresentation getAppDefinition(String appDefinitionId);

	AppDefinitionRepresentation getAppDefinitionHistory(String modelId, String modelHistoryId);

	AppDefinitionUpdateResultRepresentation updateAppDefinition(String modelId,
			AppDefinitionSaveRepresentation updatedModel);

	List<AppDefinitionServiceRepresentation> getAppDefinitions();

	List<AppDefinitionServiceRepresentation> getDeployableAppDefinitions(User user);

}