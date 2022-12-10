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

import lombok.Builder;
import lombok.Data;

/**
 * Holds the information to be passed over the intermediary backend application.
 *
 * @author Tony Murillo
 */
@Data
@Builder
public class IntermediaryRequest {

	private byte[] projectData;

	private String prompt;

	private String httpMethod;

	private String name;

	private String type; // project type e.g. maven, gradle groovy, or gradle kotlin

	private String description;

	private String groupId;

	private String artifactId; // what determins root directory name

	private String version; // java version

	private String bootVersion; // spring boot version

	private String packaging; // jar or war

	private String applicationName;

	private String language; // java, kotlin, or groovy

	private String packageName; // e.g. com.example.demo

}
