package com.mapfiltermagic.startintermediary.service.filehandling;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfiltermagic.startintermediary.model.initializr.Project;
import com.mapfiltermagic.startintermediary.service.openai.CodeCompletionService;

// TODO: I need to address how these tests will run in a container given paths assume access to directories in the project folder?
@ExtendWith(MockitoExtension.class)
public class FileHandlingServiceTest {

    private static final String ADD_CONTROLLER_FILE_NAME = "AddController";

    private static final String ADD_CONTROLLER_JAVA_FILE_PATH = "service/filehandling/addfiletoproject/" + ADD_CONTROLLER_FILE_NAME + ".java";

    private static final String ADD_CONTROLLER_CODE_COMPLETION_EXPECTED_JAVA_FILE_PATH
            = "service/filehandling/generatefilefromcodecompletion/" + ADD_CONTROLLER_FILE_NAME + ".java";

    private static final String INPUT_ZIP_PROJECT_FILE_PATH = "service/filehandling/addfiletoproject/project_without_generated_file.zip";

    private static final String PROJECT_FILE_PATH = "service/filehandling/addfiletoproject/project.json";

    private static final String ADD_TWO_NUMBERS_PROMPT = "takes in two numbers and returns their sum";

    private static final String ADD_TWO_NUMBERS_COMPLETED_JAVA_CODE_RESPONSE
            = "package com.example.demo;\n\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotati"
            + "on.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class AddController {"
            + "\n\n    @GetMapping(\"/add\")\n    public int add(@RequestParam(value = \"num1\") int num1, @RequestParam(value = \"num2\") int num2)"
            + " {\n        return num1 + num2;\n    }\n}";

    private static ObjectMapper objectMapper;

    @Mock
    private CodeCompletionService codeCompletionService;

    private FileHandlingService fileHandlingService;
    
    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void init() {
        fileHandlingService = new FileHandlingService(codeCompletionService);
    }

    @Test
    public void addFileToProject_InputDataIsAnAddControllerJavaFile_ShouldCreate() throws IOException {
        File addControllerFile = getFileFromResource(ADD_CONTROLLER_JAVA_FILE_PATH);
        byte[] addControllerFileData = Files.readAllBytes(addControllerFile.toPath());

        File inputZipFile = getFileFromResource(INPUT_ZIP_PROJECT_FILE_PATH);
        byte[] inputProjectArchive = getByteArrayFromFile(inputZipFile);
        Project project = getProject(PROJECT_FILE_PATH);

        project.setProjectData(inputProjectArchive);

        byte[] actualProjectArchiveData = fileHandlingService.addFileToProject(addControllerFileData, ADD_CONTROLLER_FILE_NAME, project);

        String directory = "src/test/resources/service/filehandling/temp-output.zip";

        Path actualZipFile = Files.write(new File(directory).toPath(), actualProjectArchiveData);
        URI uri = URI.create("jar:" + actualZipFile.toUri());

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        FileSystem fileSystem = FileSystems.newFileSystem(uri, env);

        String expectedDirectory = "world/src/main/java/com/hello/world/controller/AddController.java";

        assertTrue(Files.isReadable(fileSystem.getPath(expectedDirectory)));
        assertTrue(Files.mismatch(fileSystem.getPath(expectedDirectory), addControllerFile.toPath()) == -1);

        fileSystem.close();
        Files.delete(actualZipFile);
    }

    @Test
    public void generateFileFromCodeCompletion_AddTwoNumbersEndpointPrompt_ShouldReturnAddControllerJavaFileData() throws IOException {
        File addControllerFile = getFileFromResource(ADD_CONTROLLER_CODE_COMPLETION_EXPECTED_JAVA_FILE_PATH);

        doReturn(ADD_TWO_NUMBERS_COMPLETED_JAVA_CODE_RESPONSE).when(codeCompletionService).getCodeCompletion(anyString(), anyString());

        byte[] actualOutputData = fileHandlingService.generateFileFromCodeCompletion(ADD_TWO_NUMBERS_PROMPT, "GET");

        File tempExpectedJavaFile = File.createTempFile("temp_expected_java_file", null, null);
        tempExpectedJavaFile.deleteOnExit();

        Path actualJavaFilePath = Files.write(tempExpectedJavaFile.toPath(), actualOutputData);

        assertTrue(Files.isReadable(actualJavaFilePath));
        assertTrue(Files.mismatch(addControllerFile.toPath(), actualJavaFilePath) == -1);

        Files.delete(actualJavaFilePath);
    }

    private Project getProject(String pathOfDataFile) throws IOException {
        return (Project) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), Project.class);
     }

    private File getFileFromResource(String pathToResource) {
       return new File(this.getClass().getClassLoader().getResource(pathToResource).getFile());
    }

    private byte[] getByteArrayFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
