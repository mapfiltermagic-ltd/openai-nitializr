package com.mapfiltermagic.startintermediary.model.openai.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Usage {

    @JsonAlias("prompt_tokens")
    private int promptTokens;

    @JsonAlias("completion_tokens")
    private int completionTokens;

    @JsonAlias("total_tokens")
    private int totalTokens;

}
