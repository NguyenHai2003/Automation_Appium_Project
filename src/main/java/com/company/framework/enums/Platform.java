package com.company.framework.enums;

import lombok.*;

@Getter
@AllArgsConstructor

public enum Platform {
    ANDROID("Android"),
    IOS("iOS");

    private final String value;

    public static Platform fromString(String platform) {
        if (platform == null) {
            throw new IllegalArgumentException("Platform cannot be null");
        }
        for (Platform p : Platform.values()) {
            if (p.value.equalsIgnoreCase(platform.trim())) {
                return p;
            }
        }
        throw new IllegalArgumentException("Platform không hợp lệ: " + platform);
    }
}

