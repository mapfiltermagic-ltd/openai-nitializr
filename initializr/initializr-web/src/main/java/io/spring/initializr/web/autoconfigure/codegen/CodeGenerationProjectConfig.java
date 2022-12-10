/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.web.autoconfigure.codegen;

import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.controller.codegen.CodeGenerationProjectController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.codegen.CodeGenerationProjectRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of a custom {@link ProjectGenerationController} for Code Generation.
 *
 * @author Tony Murillo
 */
@Configuration
public class CodeGenerationProjectConfig {

	@Bean
	public CodeGenerationProjectController projectGenerationController(InitializrMetadataProvider metadataProvider,
			ApplicationContext applicationContext) {
		ProjectGenerationInvoker<CodeGenerationProjectRequest> projectGenerationInvoker = new ProjectGenerationInvoker<>(
				applicationContext, new CodeGenerationProjectRequestToDescriptionConverter());
		return new CodeGenerationProjectController(metadataProvider, projectGenerationInvoker);
	}

	static class CodeGenerationProjectRequestToDescriptionConverter
			implements ProjectRequestToDescriptionConverter<CodeGenerationProjectRequest> {

		@Override
		public ProjectDescription convert(CodeGenerationProjectRequest request, InitializrMetadata metadata) {
			return new MutableProjectDescription();
		}

	}

}
