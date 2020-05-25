/*
 *
 *  *
 *  *  *
 *  *  *  * Copyright 2019-2020 the original author or authors.
 *  *  *  *
 *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  * You may obtain a copy of the License at
 *  *  *  *
 *  *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *
 *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  * See the License for the specific language governing permissions and
 *  *  *  * limitations under the License.
 *  *  *
 *  *
 *
 *
 */

package org.springdoc.data.rest;

import java.util.Optional;

import javax.annotation.PostConstruct;

import io.swagger.v3.core.util.Json;
import org.springdoc.hateoas.HateoasHalProvider;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;

public class DataRestHalProvider extends HateoasHalProvider {

	private Optional<RepositoryRestConfiguration> repositoryRestConfigurationOptional;

	public DataRestHalProvider(Optional<RepositoryRestConfiguration> repositoryRestConfigurationOptional) {
		this.repositoryRestConfigurationOptional = repositoryRestConfigurationOptional;
	}

	@PostConstruct
	@Override
	protected void init() {
		if (!isHalEnabled())
			return;
		if (!Jackson2HalModule.isAlreadyRegisteredIn(Json.mapper()))
			Json.mapper().registerModule(new Jackson2HalModule());
	}

	@Override
	public boolean isHalEnabled() {
		return repositoryRestConfigurationOptional
				.map(RepositoryRestConfiguration::useHalAsDefaultJsonMediaType)
				.orElse(true);
	}
}
