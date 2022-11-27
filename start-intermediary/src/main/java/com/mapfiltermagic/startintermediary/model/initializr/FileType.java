package com.mapfiltermagic.startintermediary.model.initializr;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    JAVA("java", ".java"),
    KOTLIN("kotlin", ".kt"),
    GROOVY("groovy", ".groovy"),
    ZIP("zip", ".zip");

    private String name;

    private String fileExtension;

    public static String resolveFileExtension(String label) {
        for (FileType fileType : values()) {
            if (StringUtils.equals(fileType.name, label)) {
                return fileType.getFileExtension();
            }
        }

        return null;
    }

}