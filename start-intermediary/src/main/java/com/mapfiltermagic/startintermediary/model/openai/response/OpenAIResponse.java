package com.mapfiltermagic.startintermediary.model.openai.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponse {
    
    private int created;

    private String id;

    private String object;

    private String model;

    private List<Choice> choices;

    private Usage usage;

}
