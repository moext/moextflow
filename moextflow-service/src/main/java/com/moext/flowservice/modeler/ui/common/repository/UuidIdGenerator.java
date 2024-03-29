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
package com.moext.flowservice.modeler.ui.common.repository;

import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * {@link IdGenerator} implementation based on the current time and the ethernet
 * address of the machine it is running on.
 * 
 * @author Tijs Rademakers
 * @author Joram Barrez
 */
@Component
public class UuidIdGenerator {

	// different ProcessEngines on the same classloader share one generator.
	protected static volatile TimeBasedGenerator timeBasedGenerator;

	public UuidIdGenerator() {
		ensureGeneratorInitialized();
	}

	protected void ensureGeneratorInitialized() {
		if (timeBasedGenerator == null) {
			synchronized (UuidIdGenerator.class) {
				if (timeBasedGenerator == null) {
					timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
				}
			}
		}
	}

	public String generateId() {
		return timeBasedGenerator.generate().toString();
	}

}
