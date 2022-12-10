package com.mapfiltermagic.startintermediary.model.openai.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Consider autoboxed types instead of using JsonIgnore annotations
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_EMPTY)
public class OpenAIRequest {

    @JsonProperty("max_tokens")
    private int maxTokens; // maximum number of tokens to generate in the completion

    // @JsonProperty("top_p")
    @JsonIgnore
    private int topP; // alternative to sampling with temperature, called nucleus sampling

    private double temperature; // what sampling temperature to use. Higher values means the model will take more risks

    @JsonIgnore
    private int n; // how many completions to generate for each prompt
    
    @JsonIgnore
    private int logprobs; // include the log probabilities on the logprobs most likely tokens, as well the chosen tokens

    @JsonIgnore
    private boolean stream; // whether to stream back partial progress

    private String model; // id of the model to use
    
    private String prompt; // the prompt(s) to generate completions for, encoded as a string, array of strings, etc.

    @JsonIgnore
    private String stop; // up to 4 sequences where the API will stop generating further tokens

}
