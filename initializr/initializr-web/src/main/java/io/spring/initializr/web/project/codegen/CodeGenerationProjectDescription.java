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

// TODO: Fix link to Project Description (it violates the checkstyle since it's unused.)
/**
 * A custom Project Description to convey the additional flags to contributors.
 *
 * @author Tony Murillo
 */
import io.spring.initializr.generator.project.MutableProjectDescription;

public class CodeGenerationProjectDescription extends MutableProjectDescription {

	private String httpMethod;

	private String prompt;

	CodeGenerationProjectDescription() {
	}

	CodeGenerationProjectDescription(CodeGenerationProjectDescription source) {
		super(source);
		this.httpMethod = source.getHttpMethod();
		this.prompt = source.getPrompt();
	}

	@Override
	public CodeGenerationProjectDescription createCopy() {
		return new CodeGenerationProjectDescription(this);
	}

	public String getHttpMethod() {
		return this.httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPrompt() {
		return this.prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
