package com.mapfiltermagic.startintermediary.service.openai;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mapfiltermagic.startintermediary.model.openai.request.OpenAIRequest;
import com.mapfiltermagic.startintermediary.model.openai.response.OpenAIResponse;
import com.mapfiltermagic.startintermediary.service.openai.rest.OpenAIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CodeCompletionService {

    /**
     * This format string is used to be build a request that the OpenAI API will understand.
     */
    private static final String PROMPT_FORMAT = "/* Import required packages and create a Java Spring Boot %s endpoint that %s */";

    /**
     * An OpenAI model argument that is somewhat equivalent to a character limit.
     */
    private static final int MAX_TOKENS_ARGUMENT = 2000;

    private static final double TEMPERATURE_ARGUMENT = 0.0;

    /**
     * The expectation is that OpenAI will return generated code prepended with two spaces at the very top. This is used to remove those characters.
     */
    private static final String NEWLINE_CHARACTERS = "\n\n";

    /**
     * Should OpenAI return generated code with two spaces at the very top, this is the first line of code that comes after the associated newline
     * characters. This is used to remove those newline characters.
     */
    private static final String EXPECTED_FIRST_WORD_IN_CODE_AFTER_NEWLINES = "package";

    private static final String CODE_THAT_COMES_IMMEDIATELY_BEFORE_CLASS_NAME = "public class";

    /**
     * The OpenAI model we are requesting from. This is the codex model best suited for code generation.
     */
    private static final String MODEL_ARGUMENT = "code-davinci-002";

    private final OpenAIService openAIService;

    /**
     * Builds the request model to call out to the completions OpenAI endpoint and parses the response model for resultant text.
     *
     * @apiNote Currently only supports the generation of a single controller or endpoint at a time.
     *
     * @param prompt what to prompt the completion model with
     * @param httpMethod which REST method the endpoint should be
     * @return
     */
    public String getCodeCompletion(String prompt, String httpMethod) {
        String updatedPrompt = stagePromptForCompletionRequest(prompt, httpMethod);
        OpenAIRequest openAIRequest = OpenAIRequest.builder()
                .model(MODEL_ARGUMENT)
                .maxTokens(MAX_TOKENS_ARGUMENT)
                .temperature(TEMPERATURE_ARGUMENT)
                .prompt(updatedPrompt)
                .build();
        OpenAIResponse openAIResponse = openAIService.createCompletion(openAIRequest);
        if (StringUtils.isBlank(openAIResponse.getChoices().get(0).getText())) {
            log.error("No code was generated");

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No code was generated");
        }

        String generatedCode = openAIResponse.getChoices().get(0).getText();

        return removeNewlinesFromStart(generatedCode);
    }

    /**
     * Gets the name of a file based on input code.
     *
     * @param generatedCode the code from which to determine the filename
     * @return the filename without an extension
     */
    public String determineFileNameFromCode(String generatedCode) {
        log.info("Determining file name from {}", generatedCode);

        String restOfCode = StringUtils.substringAfter(generatedCode, CODE_THAT_COMES_IMMEDIATELY_BEFORE_CLASS_NAME);
        log.info("Rest of code: {}", restOfCode);
        if (!StringUtils.equals(restOfCode, StringUtils.EMPTY)) {
            String[] restOfCodeAsWordList = StringUtils.split(restOfCode, StringUtils.SPACE);
            if (ArrayUtils.isNotEmpty(restOfCodeAsWordList)) {
                String className = restOfCodeAsWordList[0];

                return className;
            } else {
                log.error("Could not find a class name");

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find a class name");
            }
        } else {
            log.error("Could not find any class definition in code from which to determine a filename");

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not find any class definition in code from which to determine a filename");
        }
    }

    private String removeNewlinesFromStart(String generatedCode) {
        String newLineCharacters = StringUtils.substringBefore(generatedCode, EXPECTED_FIRST_WORD_IN_CODE_AFTER_NEWLINES);
        if (StringUtils.equals(newLineCharacters, NEWLINE_CHARACTERS)) {
            String updatedCode = RegExUtils.removeFirst(generatedCode, NEWLINE_CHARACTERS);

            log.info("Sanitized newline characters from input");

            return updatedCode;
        } else {
            log.info("Input had no newline characters to remove");

            return generatedCode;
        }
    }

    private String stagePromptForCompletionRequest(String prompt, String requestType) {
        String updatedPrompt = String.format(PROMPT_FORMAT, requestType, prompt);

        log.debug("Staged prompt for request as: {}", updatedPrompt);

        return String.format(PROMPT_FORMAT, requestType, prompt);
    }

}
