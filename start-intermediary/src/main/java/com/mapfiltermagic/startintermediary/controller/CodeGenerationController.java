package com.mapfiltermagic.startintermediary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapfiltermagic.startintermediary.model.initializr.IntermediaryRequest;
import com.mapfiltermagic.startintermediary.service.filehandling.FileHandlingService;
import com.mapfiltermagic.startintermediary.service.openai.CodeCompletionService;

import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/code-generation")
public class CodeGenerationController {
    
    private final FileHandlingService fileHandlingService;

    private final CodeCompletionService codeCompletionService;

    /**
     * Generates code given the prompt in a request and adds the code as a file to the existing project.
     *
     * @param projectRequest
     * @return
     */
    @PostMapping("/zip")
    public ResponseEntity<byte[]> generateCode(@RequestBody IntermediaryRequest projectRequest) {
        byte[] generatedFileData = fileHandlingService.generateFileFromCodeCompletion(projectRequest.getPrompt(), projectRequest.getEndpointType());
        String fileName = codeCompletionService.determineFileNameFromCode(new String(generatedFileData, StandardCharsets.UTF_8));
        byte[] updatedProjectArchive = fileHandlingService.addFileToProject(generatedFileData, fileName, projectRequest);

        return ResponseEntity.ok()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .body(updatedProjectArchive);
	}

}
