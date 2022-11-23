package com.mapfiltermagic.startintermediary.service.openai.rest;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.mapfiltermagic.startintermediary.model.openai.request.OpenAIRequest;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIResponse;
import com.mapfiltermagic.startintermediary.util.WebClientUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all outward REST calls to the OpenAI API.
 */
@Slf4j
@Service
public class OpenAIService {

    private static final String CREATE_COMPLETION_URI = "/v1/completions";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final String secretKey;

    private final WebClient webClient;

    /**
     *
     * @param baseUrl
     * @param secretKey
     * @param webClient
     */
    @Autowired
    public OpenAIService(@Value("${openai.base-url}") String baseUrl, @Value("${openai.secret-key") String secretKey) {
        this.secretKey = secretKey;
        this.webClient = WebClientUtil.getWebClient(baseUrl);
    }

    /**
     * Makes a POST to the completion endpoint.
     * 
     * @param openAIRequest the {@link OpenAIRequest} that includes set parameters to influence our result
     * @return the populated {@link OpenAIResponse}
     */
    public OpenAIResponse createCompletion(OpenAIRequest openAIRequest) {
        try {
            OpenAIResponse openAIResponse = webClient.post()
                .uri(CREATE_COMPLETION_URI)
                .headers(getCreateCompletionHeaders())
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .block();

            log.debug("Successfully called createCompletion endpoint");

            return openAIResponse;
        } catch (WebClientResponseException ex) {
            log.error("Encountered unexpected exception when calling createCompletion endpoint");

            throw new ResponseStatusException(HttpStatus.valueOf(ex.getRawStatusCode()), ex.getMessage());
        }
    }

    /**
     * Builds out the headers needed in {@link #createCompletion()}
     * 
     * @return the required headers consumer
     */
    private Consumer<HttpHeaders> getCreateCompletionHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + secretKey);

        Consumer<HttpHeaders> headerConsumer = header -> {
            headers.forEach((key, value) -> header.add(key, headers.getFirst(key)));
        };

        return headerConsumer;
    }

}
