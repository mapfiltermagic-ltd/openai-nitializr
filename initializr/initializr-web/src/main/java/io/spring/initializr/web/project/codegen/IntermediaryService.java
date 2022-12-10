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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import io.spring.initializr.web.project.ProjectRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Calls out to the start-intermediary API.
 *
 * @author Tony Murillo
 */
public class IntermediaryService {

	/**
	 * Makes the outward call to the start-intermediary API to add a generated code file
	 * to an existing project archive.
	 * @param archiveData the project achive data
	 * @param projectRequest the {@link ProjectRequest} containing information about the
	 * project.
	 * @return the updated project archive with the generated code file added to it
	 */
	public byte[] updateProject(byte[] archiveData, ProjectRequest projectRequest) {
		URI url = null;
		try {
			url = new URI("http://localhost:8070/v1/code-generation/zip");
		}
		catch (URISyntaxException ex) {
			ex.printStackTrace();
		}

		IntermediaryRequest intermediaryRequest = IntermediaryRequest.builder().projectData(archiveData)
				.prompt(projectRequest.getPrompt()).httpMethod(projectRequest.getHttpMethod())
				.name(projectRequest.getName()).type(projectRequest.getType())
				.description(projectRequest.getDescription()).groupId(projectRequest.getGroupId())
				.artifactId(projectRequest.getArtifactId()).version(projectRequest.getVersion())
				.bootVersion(projectRequest.getBootVersion()).packaging(projectRequest.getPackaging())
				.applicationName(projectRequest.getApplicationName()).language(projectRequest.getLanguage())
				.packageName(projectRequest.getPackageName()).build();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<IntermediaryRequest> requestEntity = new HttpEntity<IntermediaryRequest>(intermediaryRequest,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<IntermediaryResponse> responseEntity = restTemplate.postForEntity(url, requestEntity,
				IntermediaryResponse.class);
		byte[] result = responseEntity.getBody().getArchiveContents();
		return result;
	}

}
