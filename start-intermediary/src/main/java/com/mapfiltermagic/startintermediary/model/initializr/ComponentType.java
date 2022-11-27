package com.mapfiltermagic.startintermediary.model.initializr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComponentType {
    
    MODEL("model"),
    CONTROLLER("controller"),
    SERVICE("service"),
    CONFIG("config");

    private String packageName;

}
