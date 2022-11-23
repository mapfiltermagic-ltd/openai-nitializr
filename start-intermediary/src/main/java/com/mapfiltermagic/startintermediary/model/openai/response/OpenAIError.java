package com.mapfiltermagic.startintermediary.model.openai.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OpenAIError {

    private Error error;

}
