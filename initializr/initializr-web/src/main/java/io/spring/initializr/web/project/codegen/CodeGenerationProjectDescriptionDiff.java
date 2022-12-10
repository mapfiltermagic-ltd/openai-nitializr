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

package io.spring.initializr.web.project.codegen;

import java.util.Objects;
import java.util.function.BiConsumer;

import io.spring.initializr.generator.project.ProjectDescriptionDiff;

/**
 * Extends the base {@link ProjectDescriptionDiff} to provide convenient diff methods on
 * {@link CodeGenerationProjectDescription}.
 *
 * @author Tony Murillo
 */
class CodeGenerationProjectDescriptionDiff extends ProjectDescriptionDiff {

	private final CodeGenerationProjectDescription original;

	CodeGenerationProjectDescriptionDiff(CodeGenerationProjectDescription original) {
		super(original);
		this.original = original;
	}

	/**
	 * Calls the specified consumer if the {@code customFlag} is different on the original
	 * source project description than the specified project description.
	 * @param current the project description to test against
	 * @param consumer to call if the property has changed
	 */
	void ifCustomFlagChanged(CodeGenerationProjectDescription current, BiConsumer<String, String> consumer) {
		if (!Objects.equals(this.original.getHttpMethod(), current.getHttpMethod())) {
			consumer.accept(this.original.getHttpMethod(), current.getHttpMethod());
		}
		else if (!Objects.equals(this.original.getPrompt(), current.getPrompt())) {
			consumer.accept(this.original.getPrompt(), current.getPrompt());
		}
	}

}
