package com.mapfiltermagic.startintermediary.service.filehandling;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.mapfiltermagic.startintermediary.model.initializr.ComponentType;
import com.mapfiltermagic.startintermediary.model.initializr.FileType;
import com.mapfiltermagic.startintermediary.model.initializr.Project;
import com.mapfiltermagic.startintermediary.service.openai.CodeCompletionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class FileHandlingService {

    /**
     * Character used to seperate names of packages (i.e. com.example.test).
     */
    private static final String PACKAGE_NAME_SEPERATOR = ".";

    /**
     * Character used to seperate subdirectories in a full directory URI.
     */
    private static final String DIRECTORY_SEPERATOR = "/";

    /**
     * This is used to access a zip file from within a mounted file system. It is prefixed to directory URI of the zip file.
     */
    private static final String JAR_FILE_PREFIX = "jar:";

    // TODO: Remove this
    /**
     * This is used to build out the URI of the generated file. The package name contains '/' characters, and the UriComponentsBuilder does not
     * support that. So this is temporarily placed in the URI format and will be later replaced with the actual package subpath.
     */
    private static final String REPLACE_WITH_PACKAGE_SUBPATH_INDICATOR = "REPLACEWITHPACKAGESUBPATH";

    // TODO: Update this to use format specifiers
    /**
     * This is the URI format of where the generated file will be built to relative to the root directory of a zip file.
     */
    private static final String PROJECT_DIRECTORY_PATH_FORMAT
            = "{artifactId}/src/main/java/" + REPLACE_WITH_PACKAGE_SUBPATH_INDICATOR + "/{componentType}/{fileName}";

    private final CodeCompletionService codeCompletionService;

    // TODO: Rename this to generateCodeCompletionFileData
    // TODO: Fix documentation here
    /**
     * Gets and uses it to build a Java code file.
     *
     * @param prompt what to prompt the GPT-3 model with
     * @param endpointType which REST method the endpoint should be
     * @return the populated Java code file data
     */
    public byte[] generateFileFromCodeCompletion(String prompt, String endpointType) {
        String generatedCode = codeCompletionService.getCodeCompletion(prompt, endpointType);
        try {
            File tempJavaFile = File.createTempFile("temp_java_file", null, null);
            tempJavaFile.deleteOnExit();
            Path updatedPath = Files.write(tempJavaFile.toPath(), generatedCode.getBytes());
            byte[] outputData = Files.readAllBytes(updatedPath);

            return outputData;
        } catch (IOException ex) {
            log.error("Encountered an exception while creating a temporary java file => {}", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Encountered an exception while creating a temporary zip file", ex);
        }
    }

    // TODO: Update method name to addFileDataToProjectArchive
    /**
     * Inserts a given file into an existing archive.
     *
     * @param newFileData data of file to add
     * @param fileName name of the file without an extension
     * @param project the {@link Project} to append the new file data to
     * @return updated project data
     */
    public byte[] addFileToProject(byte[] newFileData, String fileName, Project project) {
        Path tempProjectArchive = null;
        try {
            tempProjectArchive = Files.write(new File("temp.zip").toPath(), project.getProjectData());
        } catch (IOException ex) {
            log.error("Encountered an exception while creating a temporary zip file => {}", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Encountered an exception while creating a temporary zip file", ex);
        }
        URI uri = URI.create(JAR_FILE_PREFIX + tempProjectArchive.toUri());
        // Mount a file system at the zip file path that is prefixed as a jar file in order to edit its contents without unzipping it.
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {
            String directory = buildPathForGeneratedFile(fileName, project);
            Path newFilePath = fileSystem.getPath(directory);
            Files.createDirectories(newFilePath.getParent());
            Files.createFile(newFilePath);
            Files.write(newFilePath, newFileData, StandardOpenOption.CREATE);
            // Clean up time, but save the results before finishing
            fileSystem.close();
            byte[] updatedProjectData = Files.readAllBytes(tempProjectArchive);
            Files.delete(tempProjectArchive);

            return updatedProjectData;
        } catch (IOException ex) {
            log.error("Encountered an exception while operating within the mounted filesystem => {}", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Encountered an exception while operating within the mounted filesystem", ex);
        }
    }

    // TODO: Refactor this to just use String.format(). URI building was totally unecessary.
    private String buildPathForGeneratedFile(String fileName, Project project) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PROJECT_DIRECTORY_PATH_FORMAT);
            URI uri = builder.build(
                    project.getArtifactId(),
                    determinePackageFromGeneratedFile(),
                    getFileNameWithExtension(fileName, project)
                    );
            String path = StringUtils.replace(uri.toString(), REPLACE_WITH_PACKAGE_SUBPATH_INDICATOR,
                    getSubPathFromPackageName(project.getPackageName()));

            log.info("Built the following path for the generated file: {}", path);

            return path;
        } catch (Exception ex) {
            log.error("Exception encountered trying to build a path for the generated file => {}", ex.getMessage());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception encountered trying to build a path for the generated file", ex);
        }
    }

    private String getSubPathFromPackageName(String packageName) {
        String subPath = StringUtils.replaceChars(packageName, PACKAGE_NAME_SEPERATOR, DIRECTORY_SEPERATOR);

        log.debug("Built the subpath {} from the package name: {}", subPath, packageName);

        return StringUtils.replaceChars(packageName, PACKAGE_NAME_SEPERATOR, DIRECTORY_SEPERATOR);
    }

    // TODO: For now this will always return the "controller" package. However, it should eventually be extended to parse the generated file and
    //       return what compontent it is (i.e. services, model, controller, etc.)
    private String determinePackageFromGeneratedFile() {
        return ComponentType.CONTROLLER.getPackageName();
    }

    private String getFileNameWithExtension(String fileName, Project project) {
        String updatedFilename = fileName + FileType.resolveFileExtension(project.getLanguage());

        log.debug("Built the following filename with extension: {}", updatedFilename);

        return updatedFilename;
    }

}
