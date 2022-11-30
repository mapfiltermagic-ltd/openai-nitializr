package com.mapfiltermagic.startintermediary.model.initializr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntermediaryRequest {

	private byte[] projectData;

	private String prompt;

	private String endpointType;

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
