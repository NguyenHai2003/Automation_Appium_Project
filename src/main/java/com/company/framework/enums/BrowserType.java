package com.company.framework.enums;

import lombok.*;

@Getter
@AllArgsConstructor

public enum BrowserType {
    /**
     * Chrome browser cho Android
     */
    CHROME("Chrome"),

    /**
     * Safari browser cho iOS
     */
    SAFARI("Safari");

    private final String value;

    public static BrowserType fromString(String browserType) {
        if (browserType == null) {
            throw new IllegalArgumentException("BrowserType cannot be null");
        }
        for (BrowserType type : BrowserType.values()) {
            if (type.value.equalsIgnoreCase(browserType.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("BrowserType không hợp lệ: " + browserType);
    }
}

