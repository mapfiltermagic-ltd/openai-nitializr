package com.mapfiltermagic.startintermediary.model.openai.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OpenAIRequest {

    private int maxTokens;
    private int temperature;
    private int topP;
    private int n;
    private int logprobs;

    private boolean stream;

    private String model;
    private String prompt;
    private String stop;

}
