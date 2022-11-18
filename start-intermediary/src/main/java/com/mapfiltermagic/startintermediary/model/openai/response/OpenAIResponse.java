package com.mapfiltermagic.startintermediary.model.openai.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAIResponse {

  private String id;
  private String object;
  private String model;

  private List<Choice> choices;

  private Usage usage;

}
