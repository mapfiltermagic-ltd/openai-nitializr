package com.mapfiltermagic.startintermediary.service.openai.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfiltermagic.startintermediary.model.openai.request.OpenAIRequest;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIError;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIResponse;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@ExtendWith(MockitoExtension.class)
public class OpenAIServiceTest {

    private static final String OPENAI_SECRET_KEY = "sk-5v3LxuqRCq2KzMAeiVgciDTDhtyWZe8HKhdmd7V27PAwawyaLzfgDdrArBrV";

    private static final String OPENAI_REQUEST_CODE_COMPLEITION_PATH = "service/openai/rest/openai_request_code_completion.json";

    private static final String OPENAI_RESPONSE_CODE_COMPLEITION_PATH = "service/openai/rest/openai_response_code_completion.json";

    private static final String OPENAI_ERROR_INVALID_API_KEY_PATH = "service/openai/rest/openai_error_invalid_api_key.json";

    @Captor
    ArgumentCaptor<ClientRequest> clientRequestCaptor;
 
    private static ObjectMapper objectMapper;

    private OpenAIService openAIService;

    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void setUp() throws IOException {
        objectMapper = new ObjectMapper();

        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        openAIService = new OpenAIService(baseUrl, OPENAI_SECRET_KEY);
    }

    @Test
    public void createCompletion_ValidRequest_HeadersShouldBeSetAndShouldReturnExpectedResponse() throws Exception {

        OpenAIResponse expectedOpenAIResponse = getOpenAIResponse(OPENAI_RESPONSE_CODE_COMPLEITION_PATH);
        assertNotNull(expectedOpenAIResponse);

        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedOpenAIResponse)));

        OpenAIRequest openAIRequest = getOpenAIRequest(OPENAI_REQUEST_CODE_COMPLEITION_PATH);
        assertNotNull(openAIRequest);

        OpenAIResponse actualOpenAIResponse = openAIService.createCompletion(openAIRequest);
    
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/v1/completions", recordedRequest.getPath());
        assertTrue(StringUtils.contains(recordedRequest.getHeader("Authorization"), "Bearer " + OPENAI_SECRET_KEY));
        assertEquals(expectedOpenAIResponse, actualOpenAIResponse);
    }

    @Test
    public void createCompletion_InvalidApiKeyResponse_ShouldThrowException() throws Exception {
        OpenAIError expectedOpenAIError = getOpenAIError(OPENAI_ERROR_INVALID_API_KEY_PATH);

        mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.UNAUTHORIZED.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(expectedOpenAIError.toString()));

        OpenAIRequest openAIRequest = getOpenAIRequest(OPENAI_REQUEST_CODE_COMPLEITION_PATH);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            openAIService.createCompletion(openAIRequest);
        });

        assertTrue(StringUtils.contains(exception.getReason(), "invalid_api_key"));
        assertTrue(exception.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private OpenAIRequest getOpenAIRequest(String pathOfDataFile) throws IOException {
        return (OpenAIRequest) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), OpenAIRequest.class);
    }
    
    private OpenAIResponse getOpenAIResponse(String pathOfDataFile) throws IOException {
        return (OpenAIResponse) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), OpenAIResponse.class);
    }

    private OpenAIError getOpenAIError(String pathOfDataFile) throws IOException {
        return (OpenAIError) objectMapper.readValue(this.getClass().getClassLoader().getResource(pathOfDataFile), OpenAIError.class);
    }

}
