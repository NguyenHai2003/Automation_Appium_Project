package com.company.framework.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum AppType {

    /**
     * Native App - Ứng dụng native thuần túy (Android/iOS)
     */
    NATIVE("Native"),

    /**
     * Hybrid App - Ứng dụng kết hợp native và WebView
     */
    HYBRID("Hybrid"),

    /**
     * Mobile Web - Ứng dụng web chạy trên mobile browser
     */
    MOBILE_WEB("MobileWeb");

    private final String value;

    public static AppType fromString(String appType) {
        if (appType == null) {
            throw new IllegalArgumentException("AppType cannot be null");
        }
        for (AppType type : AppType.values()) {
            if (type.value.equalsIgnoreCase(appType.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("AppType không hợp lệ: " + appType);
    }
}
