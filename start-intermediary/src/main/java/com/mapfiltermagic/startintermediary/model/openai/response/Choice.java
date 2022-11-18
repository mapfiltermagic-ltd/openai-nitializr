package com.mapfiltermagic.startintermediary.model.openai.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choice {

    private String text;

    @JsonAlias("finish_reason")
    private String finishReason;

    private int index;
    private int logprobs;

}
