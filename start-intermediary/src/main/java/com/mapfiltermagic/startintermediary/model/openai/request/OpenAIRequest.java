package com.mapfiltermagic.startintermediary.model.openai.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OpenAIRequest {

    @JsonAlias("max_tokens")
    private int maxTokens; // maximum number of tokens to generate in the completion

    @JsonAlias("top_p")
    private int topP; // alternative to sampling with temperature, called nucleus sampling

    private int temperature; // what sampling temperature to use. Higher values means the model will take more risks
    private int n; // how many completions to generate for each prompt
    private int logprobs; // include the log probabilities on the logprobs most likely tokens, as well the chosen tokens

    private boolean stream; // whether to stream back partial progress

    private String model; // id of the model to use
    private String prompt; // the prompt(s) to generate completions for, encoded as a string, array of strings, etc.
    private String stop; // up to 4 sequences where the API will stop generating further tokens

}
