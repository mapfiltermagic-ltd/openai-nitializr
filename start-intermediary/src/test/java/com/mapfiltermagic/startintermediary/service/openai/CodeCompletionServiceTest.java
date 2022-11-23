package com.mapfiltermagic.startintermediary.service.openai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfiltermagic.startintermediary.model.openai.request.OpenAIRequest;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIResponse;
import com.mapfiltermagic.startintermediary.service.openai.rest.OpenAIService;

@ExtendWith(MockitoExtension.class)
public class CodeCompletionServiceTest {

    private static final String OPENAI_RESPONSE_CODE_COMPLEITION_PATH = "service/openai/openai_response_code_completion.json";
    private static final String OPENAI_REQUEST_CODE_COMPLEITION_PATH = "service/openai/openai_request_code_completion.json";

    private static ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<OpenAIRequest> openAIRequestCaptor;

    @Mock
    private OpenAIService openAIService;

    private CodeCompletionService codeCompletionService;

    @BeforeAll
    public static void setUp() throws IOException {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void init() {
        codeCompletionService = new CodeCompletionService(openAIService);
    }

    @Test
    public void getCodeCompletion_HappyPath() throws Exception {
        OpenAIResponse openAIResponse = getOpenAIResponse(OPENAI_RESPONSE_CODE_COMPLEITION_PATH);
    
        doReturn(openAIResponse).when(openAIService).createCompletion(any(OpenAIRequest.class));

        String prompt = "/* Import required packages and create a Java Spring Boot GET endpoint that takes in two numbers and returns their sum */";

        String choiceText = codeCompletionService.getCodeCompletion(prompt);

        verify(openAIService, times(1)).createCompletion(openAIRequestCaptor.capture());

        OpenAIRequest expectedOpenAIRequest = getOpenAIRequest(OPENAI_REQUEST_CODE_COMPLEITION_PATH);

        assertEquals(expectedOpenAIRequest,openAIRequestCaptor.getValue());

        assertTrue(StringUtils.equalsAny(choiceText, openAIResponse.getChoices().get(0).getText()));
    }

    @Test
    public void getCodeCompletion_CreateCompletionThrowsException_ShouldThrowSameException() throws Exception {    
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "401 - OpenAIError(error=Error(message=Incorrect API key provided: sk-3QV5i***************************************************RKBn. "
                + "You can find your API key at https://beta.openai.com., type=invalid_request_error, param=null, code=invalid_api_key))"); 
        doThrow(ex).when(openAIService).createCompletion(any(OpenAIRequest.class));

        String prompt = "/* Import required packages and create a Java Spring Boot GET endpoint that takes in two numbers and returns their sum */";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            codeCompletionService.getCodeCompletion(prompt);
        });

        verify(openAIService, times(1)).createCompletion(any(OpenAIRequest.class));

        assertTrue(StringUtils.contains(exception.getReason(), "invalid_api_key"));
        assertTrue(exception.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private OpenAIResponse getOpenAIResponse(String pathOfDataFile) throws IOException {
        return (OpenAIResponse) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), OpenAIResponse.class);
    }

    private OpenAIRequest getOpenAIRequest(String pathOfDataFile) throws IOException {
        return (OpenAIRequest) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), OpenAIRequest.class);
    }

}
