package com.mapfiltermagic.startintermediary.model.openai.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Error {

    private String message;

    private String type;

    private String param;

    private String code;

}
