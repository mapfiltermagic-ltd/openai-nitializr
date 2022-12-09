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

package io.spring.initializr.web.controller.codegen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectGenerationResult;
import io.spring.initializr.web.project.codegen.CodeGenerationProjectRequest;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A custom {@link ProjectGenerationController} that binds request attributes to
 * {@link CodeGenerationProjectRequest} used to generate code files in addition to
 * generating projects.
 *
 * @author Tony Murillo
 */
@Controller
public class CodeGenerationProjectController extends ProjectGenerationController<CodeGenerationProjectRequest> {

	private static final Log LOG = LogFactory.getLog(CodeGenerationProjectController.class);

	// TODO: Uncomment once created.
	// private final IntermediaryService intermediaryService;

	public CodeGenerationProjectController(InitializrMetadataProvider metadataProvider,
			ProjectGenerationInvoker<CodeGenerationProjectRequest> projectGenerationInvoker) {
		// this.intermediaryService = intermediaryService;
		super(metadataProvider, projectGenerationInvoker);
	}

	@Override
	public CodeGenerationProjectRequest projectRequest(Map<String, String> headers) {
		CodeGenerationProjectRequest request = new CodeGenerationProjectRequest();
		request.getParameters().putAll(headers);
		request.initialize(getMetadata());
		return request;
	}

	@RequestMapping("/code-generation/starter.zip")
	@Override
	public ResponseEntity<byte[]> springZip(CodeGenerationProjectRequest request) throws IOException {
		ProjectGenerationResult result = this.projectGenerationInvoker.invokeProjectStructureGeneration(request);
		// TODO: Make service call to generate file and add it to the project.
		Path archive = createArchive(result, "zip", ZipArchiveOutputStream::new, ZipArchiveEntry::new,
				ZipArchiveEntry::setUnixMode);
		return upload(archive, result.getRootDirectory(), generateFileName(request, "zip"), "application/zip");
	}

}
