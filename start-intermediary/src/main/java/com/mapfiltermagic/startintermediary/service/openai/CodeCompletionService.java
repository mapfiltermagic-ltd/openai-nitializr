package com.mapfiltermagic.startintermediary.service.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfiltermagic.startintermediary.model.openai.request.OpenAIRequest;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIResponse;
import com.mapfiltermagic.startintermediary.service.openai.rest.OpenAIService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CodeCompletionService {

    private static final int MAX_TOKENS_ARGUMENT = 4096;
    private static final int TEMPERATURE_ARGUMENT = 0;
    private static final String MODEL_ARGUMENT = "code-davinci-002";

    private final OpenAIService openAIService;

    /**
     * Builds the request model to call out to the completions OpenAI endpoint and parses the response model for resultant text.
     *
     * @param prompt what to prompt the model with
     * @return the completion text
     */
    public String getCodeCompletion(String prompt) {
        OpenAIRequest openAIRequest = OpenAIRequest.builder()
                .model(MODEL_ARGUMENT)
                .maxTokens(MAX_TOKENS_ARGUMENT)
                .temperature(TEMPERATURE_ARGUMENT)
                .prompt(prompt)
                .build();    
        OpenAIResponse openAIResponse = openAIService.createCompletion(openAIRequest);

        // Only currently accounting for responses in which 1 choice is returned. Have yet to see multiple choices.
        return openAIResponse.getChoices().get(0).getText();
    }

}
