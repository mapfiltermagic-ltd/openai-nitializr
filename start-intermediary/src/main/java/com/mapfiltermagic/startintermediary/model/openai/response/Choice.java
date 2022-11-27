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
public class Choice {

    private String text;

    @JsonAlias("finish_reason")
    private String finishReason;

    private int index;
    
    private int logprobs;

}
