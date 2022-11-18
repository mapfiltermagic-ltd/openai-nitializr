package com.mapfiltermagic.startintermediary.model.openai.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usage {

    private int promptTokens;

    @JsonAlias("completion_tokens")
    private int completionTokens;

    @JsonAlias("total_tokens")
    private int totalTokens;

}
